package messaging.sip;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServletMessage;
import javax.servlet.sip.SipSession;

/**
 * ISipDialog is implemented by classes that 
 * are to receive sip messages via the distributing
 * <code>MessagingSipServlet</code>
 */
public interface ISipDialog extends Serializable{

	/**
	 * Invariant session key used to extract the dialog.
	 */
	public static final String SESSION_KEY = "SipDialog";
	public abstract SipSession getSipSession();
	public abstract void doSipServletMessage(SipServletMessage message)
			throws ServletException, IOException;

}