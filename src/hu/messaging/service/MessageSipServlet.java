package hu.messaging.service;

import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipFactory;
import java.io.IOException;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;

import hu.messaging.util.MessageUtil;

public class MessageSipServlet extends SipServlet {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The SIP Factory. Can be used to create URI and requests.
	 */
	private SipFactory sipFactory;

	/**
	 * @inheritDoc
	 */
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		ServletContext context = config.getServletContext();
		sipFactory = (SipFactory) context
				.getAttribute("javax.servlet.sip.SipFactory");

	}

	/**
	 * @inheritDoc
	 */
	protected void doBye(SipServletRequest sipServletRequest)
			throws ServletException, IOException {
		//TODO: Implement this method
	}

	/**
	 * @inheritDoc
	 */
	protected void doAck(SipServletRequest sipServletRequest)
			throws ServletException, IOException {
		//TODO: Implement this method
	}

	/**
	 * @inheritDoc
	 */
	protected void doMessage(SipServletRequest req)
			throws ServletException, IOException {
		System.out.println("message: " + req.getContent() );
		req.createResponse(200).send();
		
		SipServletRequest messageRequest = sipFactory.createRequest(req.getApplicationSession(), "MESSAGE", req.getTo(), req.getFrom());
		System.out.println("to: " + req.getTo());
		System.out.println("from: " + req.getFrom());
		System.out.println("User-Agent: " + req.getHeader("User-Agent"));
		System.out.println("Accept-Contact: " + req.getHeader("Accept-Contact"));
                messageRequest.pushRoute(sipFactory.createSipURI(null, MessageUtil.getLocalIPAddress() + ":5082"));
//                messageRequest.addHeader("Accept-Contact", req.getHeader("Accept-Contact"));
//                messageRequest.addHeader("User-Agent", req.getHeader("User-Agent"));

                // Set the message content.
        	messageRequest.setContent("Hello " + req.getContent(), "text/plain");
        
        	messageRequest.addHeader("p-asserted-identity", "sip:helloworld@ericsson.com");

        	// Send the request
        	messageRequest.send();
	}

	/**
	 * @inheritDoc
	 */
	protected void doInvite(SipServletRequest sipServletRequest)
			throws ServletException, IOException {
		//TODO: Implement this method
	}
}
