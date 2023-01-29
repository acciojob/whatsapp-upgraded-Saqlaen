package com.driver;

public class User {
	
	private String name;
    private String mobile;
    private String belongsToGroup;
  

	@Override
	public String toString() {
		return "User [name=" + name + ", mobile=" + mobile + ", belongsToGroup=" + belongsToGroup + "]";
	}
	
	public String getBelongsToGroup() {
		return belongsToGroup;
	}
	public void setBelongsToGroup(String belongsToGroup) {
		this.belongsToGroup = belongsToGroup;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public User() {
		super();
	}
	public User(String name, String mobile) {
		super();
		this.name = name;
		this.mobile = mobile;
	}
    
}
