package com.voxpilot.mediacontrol;


/**
 * IMediaListener should be implemented by classes
 * which are to receive callbacks from an implementation
 * of <code>IMediaController</code>
 */
public interface IMediaListener {
    
	// VoiceXML application session events
	public void vxmlSessionReady(Object answerSDP , String answerContentType);
    
	public void vxmlSessionFailed(int sipStatusCode);
    
	public void vxmlSessionEnded(/* VoiceXMLReturnData data */);
	
	// Basic IVR methods
	// TODO: Events in response to calls to methods such as playAnnouncement(), promptAndCollect(), etc
	
	// Conferencing methods
	// TODO: Events in response to calls to methods such as createConference(), joinLeg(), etc
}

