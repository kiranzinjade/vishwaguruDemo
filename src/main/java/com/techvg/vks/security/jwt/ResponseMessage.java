package com.techvg.vks.security.jwt;

public class ResponseMessage {
	private String message;
    private int memberId;
    
	public ResponseMessage(String message) {
		this.message = message;
	}
   
	public ResponseMessage(String message, int memberId) {
		this.message = message;
		this.memberId = memberId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	
}
