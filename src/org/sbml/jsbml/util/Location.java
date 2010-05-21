package org.sbml.jsbml.util;

public class Location {

	private int line;
	private int column;
	
	public Location(){}
	
	public Location(int line, int column) {
		this.line = line;
		this.column = column;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return "Location [column=" + column + ", line=" + line + "]";
	}
	
	
}
