package pers.ash.shiro.model.systemmanage;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import pers.ash.shiro.model.BaseModel;
import pers.ash.shiro.util.DateUtils;

public class User extends BaseModel {

	@NotBlank(message = "{error.user.username.blank}")
	@Length(min = 3, max = 20, message = "{error.user.username.length}")
	private String username;
	@NotBlank(message = "{error.user.password.blank}")
	@Length(min = 6, max = 18, message = "{error.user.password.length}")
	private String password;
	private String salt = "";
	private String createDate;
	private Integer loginCount;
	private String description;
	@NotBlank(message = "{error.email.blank}")
	@Email(message = "{error.email.validity}")
	private String email;
	@Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "{error.phone.validity}")
	private String phone;
	private String gender;
	private Integer age;

	public User() {
		super();
		this.loginCount = 0;
		this.createDate = DateUtils.now();
	}

	public User(String username, String password) {
		this();
		this.username = username;
		this.password = password;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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
				+ ", salt=" + salt + ", createDate=" + createDate
				+ ", loginCount=" + loginCount + ", description=" + description
				+ ", email=" + email + ", phone=" + phone + ", gender="
				+ gender + ", age=" + age + ", id=" + id + ", state=" + state
				+ "]";
	}

}
