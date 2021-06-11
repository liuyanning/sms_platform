package com.hero.wireless.web.action.admin.config;


import java.util.List;

public class MenuInfo {

	private String title;
	private String image;
	private String icon;
	private String href;
	private String target;
	private String code;
	private List<MenuInfo> child;

	public MenuInfo() {
	}

	public MenuInfo(String title, String image, String icon, String href, String target, String code) {
		this.title = title;
		this.image = image;
		this.icon = icon;
		this.href = href;
		this.target = target;
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<MenuInfo> getChild() {
		return child;
	}

	public void setChild(List<MenuInfo> child) {
		this.child = child;
	}
}
