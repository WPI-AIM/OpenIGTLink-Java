package com.neuronrobotics.aim.robot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LinkConfiguration {
	private String name;// = getTagValue("name",eElement);
	private double index;// = Double.parseDouble(getTagValue("index",eElement));
	private double length;// = Double.parseDouble(getTagValue("length",eElement));
	private double scale;// = Double.parseDouble(getTagValue("scale",eElement));
	private double defaultOffset;// = Double.parseDouble(getTagValue("defaultOffset",eElement));
	private double upperLimit;// = Double.parseDouble(getTagValue("upperLimit",eElement));
	private double lowerLimit;// = Double.parseDouble(getTagValue("lowerLimit",eElement));
	public LinkConfiguration(Element eElement){
    	setName(getTagValue("name",eElement));
    	setIndex(Double.parseDouble(getTagValue("index",eElement)));
    	setLength(Double.parseDouble(getTagValue("length",eElement)));
    	setScale(Double.parseDouble(getTagValue("scale",eElement)));
    	setDefaultOffset(Double.parseDouble(getTagValue("defaultOffset",eElement)));
    	setUpperLimit(Double.parseDouble(getTagValue("upperLimit",eElement)));
    	setLowerLimit(Double.parseDouble(getTagValue("lowerLimit",eElement)));
	}
	public String toString(){
		String s="Name: "+name+", index: "+index;
		return s;
	}
	private static String getTagValue(String sTag, Element eElement){
	    NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0); 
	    //System.out.println("\t\t"+sTag+" = "+nValue.getNodeValue());
	    return nValue.getNodeValue();    
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setIndex(double index) {
		this.index = index;
	}
	public double getIndex() {
		return index;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getLength() {
		return length;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale() {
		return scale;
	}
	public void setDefaultOffset(double defaultOffset) {
		this.defaultOffset = defaultOffset;
	}
	public double getDefaultOffset() {
		return defaultOffset;
	}
	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}
	public double getUpperLimit() {
		return upperLimit;
	}
	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public double getLowerLimit() {
		return lowerLimit;
	}
}
