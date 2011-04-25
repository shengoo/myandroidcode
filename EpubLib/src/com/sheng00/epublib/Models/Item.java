package com.sheng00.epublib.Models;

import java.io.File;

public class Item {

	private String id;
	private String href;
	private String media_type;
	private String idRef;
	private File file;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String mediaType) {
		media_type = mediaType;
	}
	public String getIdRef() {
		return idRef;
	}
	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}
