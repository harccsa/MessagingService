package hu.messaging.service;

import javax.servlet.sip.SipApplicationSession;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipSession;

import java.io.IOException;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;

import org.apache.log4j.Logger;

import hu.messaging.util.MessageUtil;

public class MessageSipServlet extends SipServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(MessageSipServlet.class);
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
		sipFactory = (SipFactory) context.getAttribute("javax.servlet.sip.SipFactory");

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
	protected void doMessage(SipServletRequest req)	throws ServletException, IOException {
		
		System.out.println("doMessage calling...");
		log.info("Incoming message: " + req.getContent() );
		log.info("to: " + req.getTo());
		log.info("from: " + req.getFrom());
		log.info("User-Agent: " + req.getHeader("User-Agent"));
		log.info("Accept-Contact: " + req.getHeader("Accept-Contact"));
		req.createResponse(200).send();

/*		if (true) {
			final SipSession session = req.getSession();
*/			
			SipServletRequest messageRequest = sipFactory.createRequest(req, false);
			messageRequest.setRequestURI(sipFactory.createSipURI("alice", "ericsson.com"));
			
			messageRequest.pushRoute(sipFactory.createSipURI(null, MessageUtil.getLocalIPAddress() + ":5082"));
//	                messageRequest.addHeader("Accept-Contact", req.getHeader("Accept-Contact"));
//	                messageRequest.addHeader("User-Agent", req.getHeader("User-Agent"));

	        // Set the message content.
	        messageRequest.setContent("Hello " + req.getContent(), "text/plain");
	        
	        messageRequest.addHeader("p-asserted-identity", "sip:helloworld@ericsson.com");

	       	// Send the request
	       	messageRequest.send();
	}

	/**
	 * @inheritDoc
	 */
	protected void doInvite(SipServletRequest req) throws ServletException, IOException {
		log.info("doInvite calling...");
		log.info("Invite from: " + req.getFrom() );
		
		if (req.isInitial()) {
			req.createResponse(200).send();
			log.debug("INVITE is initial! Sending 200 OK:" + req.getFrom());
		}
		else {
			req.createResponse(403).send();
			log.debug("INVITE is not initial! Sending 403:" + req.getFrom());
		}
		
	}
}
