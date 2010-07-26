package messaging.sip.tpcc;

import java.io.Serializable;

/**
 * Interface to be implemented by classes that wish
 * to receive notifications from 3PCC Contolled Call events.
 */
public interface ITPCCEventListener extends Serializable{
	
	/**
	 * The 3PCC procedure has started.
	 * 
	 * At this point INVITE requests should have been sent to both parties.
	 * 
	 * @param controlledCall
	 */
	public void callStarted(ITPCC controlledCall);
    
	/**
	 * The call could not be connected.
	 * @param controlledCall
	 */
	public void callConnectionFailure(ITPCC controlledCall);
    
	/**
	 * Both parties of this call have now been connected successfully,
	 * and no more session initiation activity will be performed.
	 * @param controlledCall
	 */
	public void callConnected(ITPCC controlledCall);
    
	/**
	 * The controlled call has ended.
	 * 
	 * Both parties will have been disconnected.
	 * @param controlledCall
	 */
	public void callEnded(ITPCC controlledCall);
		
}
