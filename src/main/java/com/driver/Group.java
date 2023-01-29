package com.driver;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
    private String name;
    private int numberOfParticipants;
    private List<User> groupMembers = new ArrayList<>();
    private List<Message> grpMessages = new ArrayList<>();
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}
	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}
	public Group() {
		super();
	}
	@Override
	public String toString() {
		return "Group [name=" + name + ", numberOfParticipants=" + numberOfParticipants + ", groupMembers="
				+ groupMembers + "]";
	}
	public Group(String name, int numberOfParticipants, List<User> groupMembers) {
		super();
		this.name = name;
		this.numberOfParticipants = numberOfParticipants;
		this.groupMembers = groupMembers;
	}
	public List<User> getGroupMembers() {
		return groupMembers;
	}
	public void setGroupMembers(List<User> groupMembers) {
		this.groupMembers = groupMembers;
	}
	public List<Message> getGrpMessages() {
		return grpMessages;
	}
	public void setGrpMessages(List<Message> grpMessages) {
		this.grpMessages = grpMessages;
	}

	


    
	
    

}
