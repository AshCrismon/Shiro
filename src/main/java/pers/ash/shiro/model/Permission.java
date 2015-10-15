package pers.ash.shiro.model;

public class Permission extends BaseModel{
	
	private String name;
	private String permissionUri;
	private String description;
	
	public Permission(){
		super();
	}
	public Permission(String name, String permissionUri, String description) {
		super();
		this.name = name;
		this.permissionUri = permissionUri;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Permission [name=" + name + ", permissionUri=" + permissionUri
				+ ", description=" + description + ", state=" + state + "]";
	}
	
}
