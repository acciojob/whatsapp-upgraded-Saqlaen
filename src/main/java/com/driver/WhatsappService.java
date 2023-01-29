package com.driver;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {
	
	public int groupCount = 0;
	public int messageCount = 0; // overall message count
	
	// phone number and user
	HashMap<String, User> usersHm = new HashMap<>();
	HashMap<String, Group> groupsHm = new HashMap<>();
	HashMap<Integer, Message > messageHashMap = new HashMap<>();

	public String createUser(String name, String mobile) throws Exception {
		
		if( !usersHm.containsKey( mobile ) ) {
			
			User user = new User();
			user.setMobile(mobile);
			user.setName(name);
			
			usersHm.put( mobile, user );
			System.out.println( user.toString() );
		}
		else {
			throw new Exception("User already exists");
		}
		
		
		return "SUCCESS";
	}

	public Group createGroup(List<User> users) {
		
		Group group = new Group();
		if( users.size() == 2 ) {
			User lastUser = users.get( users.size() - 1 );
			String groupName = lastUser.getName();
			
			group.setName(groupName);
			group.setNumberOfParticipants( users.size() );
			
			List<User> listUsr = group.getGroupMembers();
			for( User user : users ) {
				listUsr.add( user );
				user.setBelongsToGroup(groupName);
				
			}
			group.setGroupMembers( listUsr );
			
			if( groupsHm.containsKey(groupName) == false ) {
				groupsHm.put( groupName, group );				
			}
			
		}
		else if( users.size() > 2 ) {
			groupCount++;
			
			String groupName = "Group "+groupCount;
			
			group.setName(groupName);
			group.setNumberOfParticipants( users.size() );
			
			List<User> listUsr = group.getGroupMembers();
			for( User user : users ) {
				listUsr.add( user );
				user.setBelongsToGroup(groupName);
			}
			group.setGroupMembers( listUsr );
			
			if( groupsHm.containsKey(groupName) == false ) {
				groupsHm.put( groupName, group );				
			}	
		}
		System.out.println( group.toString() );
		return group;
	}

	
	public int createMessage(String content) {
		messageCount++;
		
		Message msg = new Message();
		msg.setId( messageCount );
		msg.setContent(content);
		msg.setTimestamp(new Date() );
		
		if( messageHashMap.containsKey( msg.getId() ) == false ) {
			messageHashMap.put( messageCount, msg );
		}
		
		return msg.getId();
	}
	
	
	
	
	
	public int sendMessage(Message message, User sender, Group group) throws Exception {
		
		if( groupsHm.containsKey(group.getName()) == false ) {
			throw new Exception("Group does not exist");
		}
		
		Message msg = messageHashMap.get( message.getId() );
		
		boolean found = false;
		Group currGroup = groupsHm.get( group.getName() );
		
		List<User> usersList = currGroup.getGroupMembers();
		for( User user : usersList ) {
			if( user.getMobile() == sender.getMobile() ) {
				found = true;
				msg.setMsgSender(sender);
				msg.setGroup(currGroup);
				break;
			}
		}
		
		if( found == false ) {
			throw new Exception("You are not allowed to send message");
		}
		
		List<Message> grpMessage = currGroup.getGrpMessages();
		grpMessage.add( message );
		currGroup.setGrpMessages(grpMessage);
		
		return grpMessage.size() ; 
	}

	public String changeAdmin(User approver, User user, Group group) throws Exception {
		
		if( groupsHm.containsKey(group.getName()) == false ) {
			throw new Exception("Group does not exist");
		}
		
		boolean found = false;
		Group currGroup = groupsHm.get( group.getName() );
		
		List<User> usersList = currGroup.getGroupMembers();
		// first user in list is always the admin 
		// check if approver is the admin of the group
		User adminUser = usersList.get(0);
		if( adminUser.getMobile() != approver.getMobile() ) {
			throw new Exception("Approver does not have rights");
		}
		
		// check if user is in that group
		int userIndx = 0;
		for( int i=0; i<usersList.size(); i++ ) {
			User usr = usersList.get(i);
			if( usr.getMobile() == user.getMobile() ) {
				found = true;
				userIndx = i;
				break;
			}
		}
		
		if( found == false ) {
			throw new Exception("User is not a participant");
		}
		
		// first remove the user from the group and add him to 0th index 
		usersList.remove(userIndx);
		
		usersList.add( 0, user );
		usersList.add( userIndx, adminUser);
		
		return "SUCCESS";
	}

	public int removeUser(User user) throws Exception {
		User currUser = null;
		
		if( usersHm.containsKey( user.getMobile() ) ) {
			currUser = usersHm.get( user.getMobile() );
		}
		
		if( currUser.getBelongsToGroup() == null ) {
			throw new Exception("User not found");
		}
		
		String groupName = currUser.getBelongsToGroup();
		Group usersGroup = groupsHm.get( groupName );
		
		// check if the user is the admin of that group
		List<User> listOfPeopleInGroup = usersGroup.getGroupMembers();
		User checkAdmin = listOfPeopleInGroup.get(0);
		
		if( user.getMobile() == checkAdmin.getMobile() ) {
			throw new Exception("Cannot remove admin");
		}
		
		// if the user is not the admin remove him from the group 
		// remove all his messages 
		
		int userIndx = 0;
		for( int i=0; i<listOfPeopleInGroup.size(); i++ ) {
			User usr = listOfPeopleInGroup.get(i);
			if( usr.getMobile() == user.getMobile() ) {
				userIndx = i;
				break;
			}
		}
		
		// remove the user from the group
		User toBeRemoved = listOfPeopleInGroup.get( userIndx );
		listOfPeopleInGroup.remove(userIndx);
		
		List<Message> messageList = usersGroup.getGrpMessages();

		for( int i=0; i<messageList.size(); i++ ) {
			Message msgToRemove = messageList.get(i);
			User sentMsg = msgToRemove.getMsgSender();
			if( sentMsg.getMobile().equals( user.getMobile() ) ) {
				
				// remove the message from the group 
				// remove the message from the hash 
				
				messageList.remove( i );
				if( messageHashMap.containsKey( msgToRemove.getId() ) ) {
					messageHashMap.remove( msgToRemove.getId() );
				}
			}
		}
		
		
		// set all attributes of user, group , message
		usersGroup.setGroupMembers(listOfPeopleInGroup);
		usersGroup.setGrpMessages(messageList);
		int total = listOfPeopleInGroup.size() + messageList.size() + messageHashMap.size();
//		messageCount--;
		return total;
	}

	public String findMessage(Date start, Date end, int k) {
		return null;
	}
	
	
	
	
	

	
	

	
	
}
