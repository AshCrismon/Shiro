package cn.ash.shiro.model;

public class User {
	
	private String id;
	private String username;
	private String password;
	private String createDate;
	private Integer loginCount;
	private String description;
	private String email;
	private String phone;
	private String gender;
	private Integer age;
	
	public User(){}
	
	public User(String id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", createDate=" + createDate + ", loginCount=" + loginCount
				+ ", description=" + description + ", email=" + email
				+ ", phone=" + phone + ", gender=" + gender + ", age=" + age
				+ "]";
	}
}
