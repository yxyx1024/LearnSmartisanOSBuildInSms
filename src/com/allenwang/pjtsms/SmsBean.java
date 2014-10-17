package com.allenwang.pjtsms;

import android.content.Context;

/**
 * 包装短信列表中的短信的类
 * @author AllenWang
 *
 */

public class SmsBean {
	private String thread_id;	// 线程id
    private String msg_count;	// 消息个数
    private String snippet; 	// 消息片段
    private String address;		// 电话号码
    private String read;		// 已读
    private Long date;			// 日期
    
    private String name;		//从address转换成的name
    private String time;		//经处理的Sring类型的日期
    
    //构造方法一,获得与对话相关的数据(对话的id,对话数量和短信内容)时使用该构造方法
    public SmsBean(String thread_id, String msg_count, String snippet) {
    	this.thread_id = thread_id;
    	this.msg_count = msg_count;
    	this.snippet = snippet;
    }
    
    //构造方法二,已获得与对话相关数据初始化了短信对象,再获得短信的其他信息(收件人,发送时间,是否已读)时使用该方法
    public SmsBean(Context context, SmsBean unaddedConversation, String address, Long time, String read) {
    	SmsProcessor processor = new SmsProcessor(context);
    	this.thread_id = unaddedConversation.thread_id;
    	this.msg_count = unaddedConversation.msg_count;
    	this.snippet = unaddedConversation.snippet;
    	this.address = address;
    	this.name = processor.getNameFromAddress(address);
System.out.println("name:" + name);    	
    	this.time = processor.formatTimeStampString(context, time, false);
    	this.read = read;
    }

	public String getThread_id() {
		return thread_id;
	}

	public void setThread_id(String thread_id) {
		this.thread_id = thread_id;
	}

	public String getMsg_count() {
		return msg_count;
	}

	public void setMsg_count(String msg_count) {
		this.msg_count = msg_count;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
    
}
