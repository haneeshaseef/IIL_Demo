package com.example.demo.pojo;

import java.io.Serializable;

public class EligibityProcessRequest implements Serializable {
	
	private String requestedUser;

	public String getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(String requestedUser) {
		this.requestedUser = requestedUser;
	}

}
