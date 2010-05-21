package org.sbml.jsbml.util;

public class Option {

	private String name;
	private String status;
	
	public Option() {}
	
	public Option(String name, String status) {
		this.name = name;
		this.status = status;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Option [name=" + name + ", status=" + status + "]";
	}
	
	
	
}
