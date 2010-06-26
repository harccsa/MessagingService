package com.voxpilot.mediacontrol;

import javax.servlet.sip.SipApplicationSession;
import javax.servlet.sip.SipFactory;

/**
 * MediaControllerFactory is used to create IMediaController implementations
 * 
 * There may be a variety of different media controller implementations,
 * however they are not currently implemented here.
 */
public class MediaControllerFactory {

    /**
     * Creates an implementation of IMediaController based on the controllerType attribute.
     * 
     * @param controllerType
     * @param factory
     * @param sipAppSession
     * @return
     */
    public static IMediaController create(int controllerType,
            SipFactory factory, SipApplicationSession sipAppSession) {
        
        switch (controllerType) {
        case IMediaController.SIP_VXML_MEDIA_CONTROLLER:
            return new SipVxmlMediaController(factory, sipAppSession);
        default:
            return new SipVxmlMediaController(factory, sipAppSession);
        }
    }
}

