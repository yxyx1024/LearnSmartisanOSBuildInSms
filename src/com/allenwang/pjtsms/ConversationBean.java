package com.allenwang.pjtsms;

/**
 * 与单个联系人对话页面中的短信的包装类
 * 包含联系人姓名,发送日期,短信内容,布局id4个成员变量
 * 根据短信类型的不同传入不同的layoutID
 * @author AllenWang
 *
 */

public class ConversationBean {

	private String name = null;
	private String date = null;
	private String body = null;
	private int layoutId = 0;

	public ConversationBean(String name, String date, String body, int layoutId) {
		this.name = name;
		this.date = date;
System.out.println("date:" + date);
		this.body = body;
		this.layoutId = layoutId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}

}
