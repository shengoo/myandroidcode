package com.s00.Models;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private Metadata metadata;
	private List<Item> items;
	private List<NavPoint> navPoints;
	private List<NavPoint> flatNavPoints = new ArrayList<NavPoint>();

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<NavPoint> getNavPoints() {
		return navPoints;
	}

	public void setNavPoints(List<NavPoint> navPoints) {
		this.navPoints = navPoints;
		for (NavPoint navPoint : navPoints) {
			flatNavPoints.add(navPoint);
			if(navPoint.getChildren().size() > 0)
				addChildren(navPoint);
		}
	}
	
	private void addChildren(NavPoint np){
		for (NavPoint navPoint : np.getChildren()) {
			flatNavPoints.add(navPoint);
			if (navPoint.getChildren().size() > 0) {
				addChildren(navPoint);
			}
		}
	}

	public List<NavPoint> getFlatNavPoints() {
		return flatNavPoints;
	}
}
