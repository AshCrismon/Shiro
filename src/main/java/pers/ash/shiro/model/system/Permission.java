package pers.ash.shiro.model.system;

import org.hibernate.validator.constraints.NotBlank;

import pers.ash.shiro.model.BaseModel;

public class Permission extends BaseModel{
	
	@NotBlank(message = "{error.permission.name.blank}")
	private String name;
	@NotBlank(message = "{error.permission.permissionUri.blank}")
	private String permissionUri;
	private String requestAction;
	private final String DEFAULT_REQUEST_ACTION = "get";
	private String description;
	
	public Permission(){
		super();
	}
	public Permission(String name, String permissionUri, String description) {
		super();
		this.name = name;
		this.permissionUri = permissionUri;
		this.requestAction = DEFAULT_REQUEST_ACTION;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermissionUri() {
		return permissionUri;
	}
	public void setPermissionUri(String permissionUri) {
		this.permissionUri = permissionUri;
	}
	
	public String getRequestAction() {
		return requestAction;
	}
	public void setRequestAction(String requestAction) {
		this.requestAction = requestAction;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Permission [name=" + name + ", permissionUri=" + permissionUri
				+ ", requestAction=" + requestAction + ", description="
				+ description + ", state=" + state + "]";
	}
}
