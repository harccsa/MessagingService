package messaging.sip.tpcc;

import java.io.IOException;

import javax.servlet.sip.SipSession;

import messaging.sip.ISipDialog;

/**
 * 
 * ITPCC (Third Party Call Control)
 * 
 * This interface should be implemented by classes
 * that perform controlled call connections.
 */
public interface ITPCC extends ISipDialog{
	
	/**
	 * Number of seconds to wait before deciding that a call in unanswered.
	 */
	public static final int CONNECTION_TIMEOUT_SECONDS=25;
    
	/**
	 * Starts the TPCC call sequence.
	 */
	public void startCall();
    
	/**
	 * Call back to receive connection timeout notices from the 
	 * AssistantTimerListener
	 */
	public void connectionTimeout();
    
	/**
	 * @return The Sip Session for party A
	 */
	public SipSession getSipSessionA();
    
	/**
	 * @return The Sip Session for party B
	 */
	public SipSession getSipSessionB();
	/**
	 * Sets the listener which will receive notifications of the 
	 * TPCC connection events.
	 * @see ITPCCEventListener
	 * @param listener
	 */
	public void setTPCCEventListener(ITPCCEventListener listener);
    
	/**
	 * Ends the call.
	 * This method will send 'BYE' requests to both parties.
	 */
	public void endCall();
    
	/**
	 * Disconnects the A party, by sending a 'BYE' request.
	 * @throws IOException
	 */
	public void disconnectA() throws IOException;
    
	/**
	 * Disconnects the B party, by sending a 'BYE' request.
	 * @throws IOException
	 */
	public void disconnectB() throws IOException;

}
