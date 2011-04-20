package com.s00.Models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NavPoint {

	private String id;
	private int playOrder;
	private String label;
	private String href;
	private File content;
	private List<NavPoint> children = new ArrayList<NavPoint>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPlayOrder() {
		return playOrder;
	}
	public void setPlayOrder(int playOrder) {
		this.playOrder = playOrder;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public List<NavPoint> getChildren() {
		return children;
	}
	public void setChildren(List<NavPoint> children) {
		this.children = children;
	}
	public File getContent() {
		return content;
	}
	public void setContent(File file) {
		this.content = file;
	}
	
}
