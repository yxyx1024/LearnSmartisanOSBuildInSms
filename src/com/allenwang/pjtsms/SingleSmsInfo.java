package com.allenwang.pjtsms;

public class SingleSmsInfo {

	private String mAddress = null;
	private String mBody = null;
	private long mDate = 0;
	private int m_id = 0;
	private int mThread_id = 0;
	private int mType = 0;
	private int mPerson = 0;
	private int mProtocol = 0;
	private int mRead = 0;

	private String mName = null;
	private String mTime = null;

	
	
	public SingleSmsInfo(String address, String body, long date, int _id,
			int thread_id, int type, int person, int protocol, int read, String name, String time) {
		this.mAddress = address;
		this.mBody = body;
		this.mDate = date;
		this.m_id = _id;
		this.mThread_id = thread_id;
		this.mType = type;
		this.mPerson = person;
		this.mProtocol = protocol;
		this.mRead = read;
		
		this.mName = name;
		this.mTime = time;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		this.mAddress = address;
	}

	public String getBody() {
		return mBody;
	}

	public void setBody(String body) {
		this.mBody = body;
	}

	public long getDate() {
		return mDate;
	}

	public void setDate(long date) {
		this.mDate = date;
	}

	public int get_id() {
		return m_id;
	}

	public void set_id(int _id) {
		this.m_id = _id;
	}

	public int getThread_id() {
		return mThread_id;
	}

	public void setThread_id(int thread_id) {
		this.mThread_id = thread_id;
	}

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		this.mType = type;
	}

	public int getPerson() {
		return mPerson;
	}

	public void setPerson(int person) {
		this.mPerson = person;
	}

	public int getProtocol() {
		return mProtocol;
	}

	public void setProtocol(int protocol) {
		this.mProtocol = protocol;
	}

	public int getRead() {
		return mRead;
	}

	public void setRead(int read) {
		this.mRead = read;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String time) {
		this.mTime = time;
	}

}
