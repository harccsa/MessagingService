package com.voxpilot.mediacontrol;

import java.io.Serializable;

/**
 * VxmlParam represents a parameter
 * which can be passed to the the VMS as part of
 * the initial sip request URI.
 */
public class VxmlParam implements Serializable{

	private static final long serialVersionUID = -78037748421045590L;
	public static final String VOICEXML="voicexml";
	public static final String AAI="aai";
	public static final String CCXML="ccxml";
	
	private String name;
	private String value;
	
	public VxmlParam(String name , String value) {
	this.name=name;
	this.value=value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
}

