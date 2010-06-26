package com.voxpilot.mediacontrol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.sip.SipApplicationSession;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServletMessage;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;

/**
 * SipVxmlMediaController is an implementation of <code>IMediaController</code>
 * which uses Sip and VXML to control the flow of the call.
 */
public class SipVxmlMediaController implements IMediaController {

    // These are the transition states of this controller.
    public static final int NONE = 0;

    public static final int IDLE = 1;

    public static final int CONNECTING = 2;

    public static final int ERROR = 3;

    public static final int READY = 4;

    public static final int IN_PROGRESS = 5;

    public static final int DISCONNECTING = 6;

    public static final int ENDED = 7;

    private int sessionState = -1;

    // The Listener that will receive call backs.
    private IMediaListener listener;

    // The Sip attributes.
    private SipFactory sipFactory;

    private Object vmsOffer;

    private String vmsContentType;

    private int failureCode;

    private SipServletRequest inviteRequest;

    private SipServletResponse response;

    private SipApplicationSession sipAppSession;

    /**
     * Constructor.
     * 
     * The given sip application session will be used to create requests to the
     * MRF.
     * 
     * @param factory
     * @param sipAppSession
     */
    public SipVxmlMediaController(SipFactory factory,
            SipApplicationSession sipAppSession) {
        this.sipFactory = factory;
        failureCode = 0;
        setState(NONE);
        this.sipAppSession = sipAppSession;
    }

    public SipSession createVxmlSession(String sipURI, String remoteURI,
            String localURI, Object offerSDP, String contentType,
            VxmlParam[] params) throws MediaControlException {

        System.out.println("SipVXMLMediaController::createVXMLSession sipURI="
                + sipURI);
        System.out
                .println("SipVXMLMediaController::createVXMLSession remoteURI="
                        + remoteURI);
        System.out
                .println("SipVXMLMediaController::createVXMLSession localURI="
                        + localURI);
        System.out.println("SipVXMLMediaController::createVXMLSession kliens offer="
               + offerSDP);

        if (sessionState != NONE) {
            throw new MediaControlException(
                    "Cannot create session , controller state is not NONE. State="
                            + sessionState);
        }

        try {
            inviteRequest = sipFactory.createRequest(sipAppSession, "INVITE",
                    remoteURI, localURI);

            String vxmlParams = "";

            for (int i = 0; i < params.length; i++) {
                vxmlParams += ";" + params[i].getName() + "="
                        + URLEncoder.encode(params[i].getValue(), "UTF-8");
            }

            inviteRequest.setRequestURI(sipFactory.createURI(sipURI
                    + vxmlParams));

            inviteRequest.setContent(offerSDP, contentType);

        } catch (Exception e) {
            throw new MediaControlException(e);
        }

        setState(IDLE);

        return inviteRequest.getSession(true);

    }

    public void connectVxmlSession() throws MediaControlException {
        try {

            if (sessionState != IDLE) {
                throw new MediaControlException(
                        "Cannot start session , controller state is not IDLE. State="
                                + sessionState);
            }

            inviteRequest.send();

        } catch (UnsupportedEncodingException e) {
            throw new MediaControlException(e);
        } catch (IOException e) {
            throw new MediaControlException(e);
        }

        setState(CONNECTING);

    }

    public void setMediaListener(IMediaListener listener) {
        this.listener = listener;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.voxpilot.mediacontrol.IMediaController#endVxmlSession()
     */
    public void endVxmlSession() throws MediaControlException {

        if (sessionState == IN_PROGRESS) {
            try {
                inviteRequest.getSession().createRequest("BYE").send();
            } catch (IOException e) {
                throw new MediaControlException(
                        "Failed to send BYE to Media Server.\n" + e);
            }
            setState(DISCONNECTING);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.voxpilot.mediacontrol.IMediaController#doSipServletMessage(javax.servlet.sip.SipServletMessage)
     */
    public void doSipServletMessage(SipServletMessage message)
            throws ServletException, IOException {
        if (message instanceof SipServletRequest) {
            doSipServletRequest((SipServletRequest) message);
        } else {
            doSipServletResponse((SipServletResponse) message);
        }
    }

    /**
     * This method contains the logic for handling requests from the MRF.
     * 
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    private void doSipServletRequest(SipServletRequest request)
            throws ServletException, IOException {

        if (request.getMethod().equals("BYE")) {
            request.createResponse(200).send();
            setState(ENDED);
        }
    }

    /**
     * This method contains the logic for handling responses from the MRF.
     * 
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doSipServletResponse(SipServletResponse response)
            throws ServletException, IOException {

        System.out.println("SIP MEDIA CONTROLLER RECEIVED RESPONSE "
                + response.getStatus());

        if (response.getStatus() == 200) {
            if (response.getMethod().equals("INVITE")) {

            	    System.out.println("VMS offer content length: " + response.getContentLength());
                vmsOffer = response.getContent();
                vmsContentType = response.getContentType();
                this.response = response;
               
                setState(READY);

            } else if (response.getMethod().equals("BYE")) {
                setState(ENDED);
            }
        } else if (response.getStatus() >= 400) {

            failureCode = response.getStatus();

            setState(ERROR);
        }
    }

    /**
     * Provides access to the controller state information.
     * 
     * @return
     */
    public int getSessionState() {
        return sessionState;
    }

    /**
     * Internal State transition method.
     * 
     * @param newState
     */
    private void setState(int newState) {

        sessionState = newState;
        if (listener != null) {

            switch (sessionState) {
            case READY: {
                listener.vxmlSessionReady(vmsOffer, vmsContentType);
                break;
            }
            case ENDED: {
                listener.vxmlSessionEnded();
                break;
            }
            case ERROR: {
                listener.vxmlSessionFailed(failureCode);
                break;
            }
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.voxpilot.mediacontrol.IMediaController#getSipSession()
     */
    public SipSession getSipSession() {
        return inviteRequest.getSession();
    }

    public void startVxmlSession() throws MediaControlException {
        try {
            response.createAck().send();
            setState(IN_PROGRESS);
        } catch (Exception e) {
            throw new MediaControlException(e);
        }
    }

}

