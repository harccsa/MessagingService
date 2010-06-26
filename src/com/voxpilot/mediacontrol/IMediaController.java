package com.voxpilot.mediacontrol;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServletMessage;
import javax.servlet.sip.SipSession;

/**
 * IMediaController interface to be implemented
 * by the various media controller types.
 */
public interface IMediaController {

    // list of media controller types.
    public static final int SIP_VXML_MEDIA_CONTROLLER=0;
    public static final int SIP_MEDIA_CONTROLLER=1;
    
	public static final String SESSION_CONTENT_TYPE="application/sdp";
	
	// General methods
	public void doSipServletMessage(SipServletMessage message) throws ServletException , IOException;
	public void setMediaListener(IMediaListener listener);
	public SipSession getSipSession();
	
	// VoiceXML application session methods
	public void connectVxmlSession()throws MediaControlException;
	public SipSession createVxmlSession(String sipURI, String remoteURI, String localURI, Object offerSDP  , String contentType, VxmlParam [] params) throws MediaControlException;
    public void startVxmlSession()throws MediaControlException;
    public void endVxmlSession()throws MediaControlException;
	
	// Basic IVR methods
	// TODO: For example, playAnnouncement(), promptAndCollect(), etc
	
	// Conferencing methods
	// TODO: For example, createConference(), joinLeg(), etc
			
}