package com.driver;

import java.util.Date;

public class Message {
	
    private int id;
    private String content;
    private Date timestamp;
    private Group group;
    private User msgSender;
    
    
    
    
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public User getMsgSender() {
		return msgSender;
	}
	public void setMsgSender(User msgSender) {
		this.msgSender = msgSender;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Message(int id, String content, Date timestamp) {
		super();
		this.id = id;
		this.content = content;
		this.timestamp = timestamp;
	}
	public Message() {
		super();
	}
	public Message(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

}
