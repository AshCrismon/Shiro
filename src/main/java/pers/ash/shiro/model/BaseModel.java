package pers.ash.shiro.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import pers.ash.shiro.util.UUIDUtils;

@Entity
@XmlRootElement
public class BaseModel {
	
	protected String id;
	protected ModelState state;
	
	public BaseModel(){
		this.id = UUIDUtils.createUUID();
		this.state = ModelState.NORMAL;
	}
	@XmlAttribute
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@XmlAttribute
	public ModelState getState() {
		return state;
	}
	public void setState(ModelState state) {
		this.state = state;
	}
	
}
