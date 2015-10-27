package pers.ash.shiro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import pers.ash.shiro.util.UUIDUtils;

@Entity
@XmlRootElement
public class BaseModel implements Serializable{
	
	private static final long serialVersionUID = 7643309597111822893L;
	
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
