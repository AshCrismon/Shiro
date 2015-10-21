package pers.ash.shiro.model.systemmanage;

import org.hibernate.validator.constraints.NotBlank;

import pers.ash.shiro.model.BaseModel;

public class Role extends BaseModel{
	
	@NotBlank(message = "{error.role.name.blank}")
	private String name;
	private String description;
	
	public Role(){
		super();
	}

	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", description=" + description + ", id="
				+ id + ", state=" + state + "]";
	}
	
}
