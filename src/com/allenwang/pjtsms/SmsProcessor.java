package com.allenwang.pjtsms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;
import android.text.format.Time;

/**
 * 进行获取短信,对时间进行处理,通过号码匹配联系人的工具类
 * 
 * @author AllenWang
 * 
 */

public class SmsProcessor {

	private Context mContext = null;

	public SmsProcessor() {
	}

	public SmsProcessor(Context context) {
		this.mContext = context;
	}

	public static final String URI_SMS_ALL = "content://sms/";
	public static final String URI_SMS_CONVERSATIONS = "content://sms/conversations";
	public static final String CONTACTS_LOOKUP = "content://com.android.contacts/phone_lookup/";

	public static final String[] SMS_COLUMNS = new String[] {
			"address", // 0 String 号码
			"body", // 1 String 短信内容
			"date", // 2 long 事件
			"_id", // 3 int 短信id
			"thread_id", // 4 int 对话id
			"type", // 5 int 短信类型
			"person", // 6 int 据说通过该字段数据可以将对方号码与联系人进行匹配,但我取得的一直是0
			"protocol", // 7 协议
			"read" // 8 是否已读
	};

	public static final String[] CONVERSATION_COLUMNS = new String[] {
			"thread_id", // 0 对话id
			"msg_count", // 1 对话数量
			"snippet" // 2 短信片段
	};

	public ArrayList<SmsBean> getConversations(int number) {
		ArrayList<SmsBean> converstaionList = new ArrayList<SmsBean>();
		ContentResolver cr = mContext.getContentResolver();
		Uri uri_sms_conversation = Uri.parse(URI_SMS_CONVERSATIONS);
		Cursor c = null;
		try {
			if (number > 0) {
				c = cr.query(uri_sms_conversation, CONVERSATION_COLUMNS, null,
						null, "thread_id desc limit " + number);
			} else {
				c = cr.query(uri_sms_conversation, CONVERSATION_COLUMNS, null,
						null, "date desc");
			}

			if (c == null || c.getCount() == 0)
				return converstaionList;

			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);
				SmsBean conversation = new SmsBean(c.getString(c
						.getColumnIndex("thread_id")), c.getString(c
						.getColumnIndex("msg_count")), c.getString(c
						.getColumnIndex("snippet")));
				converstaionList.add(conversation);
			}
			return converstaionList;
		} catch (Exception e) {
			System.out.println(">>>>>>>>>>exception occured<<<<<<<<<<");
			return converstaionList;
		} finally {
			c.close();
			c = null;
		}
	}

	public ArrayList<SmsBean> addInfosToConversation(
			ArrayList<SmsBean> conversationList) {
		ArrayList<SmsBean> addedInfosConversationList = new ArrayList<SmsBean>();
		Uri uri_sms_all = Uri.parse(URI_SMS_ALL);
		ContentResolver cr = mContext.getContentResolver();
		Cursor c = null;

		for (SmsBean conversation : conversationList) {
			c = cr.query(uri_sms_all, SMS_COLUMNS, "thread_id = "
					+ conversation.getThread_id(), null, null);
			if (c == null || c.getCount() == 0)
				return addedInfosConversationList;
			c.moveToFirst();
			String address = c.getString(c.getColumnIndex("address"));
			System.out.println("address:" + address);
			Long time = c.getLong(c.getColumnIndex("date"));
			String read = c.getString(c.getColumnIndex("read"));
			SmsBean addedInfoBean = new SmsBean(mContext, conversation,
					address, time, read);
			addedInfosConversationList.add(addedInfoBean);
		}
		return addedInfosConversationList;
	}

	public String formatDateToString(long longDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date dateDate = new Date(longDate);
		String time = sdf.format(dateDate);
		return time;
	}

	public String formatTimeStampString(Context context, long longDate,
			boolean fullFormat) {
		Time then = new Time();
		then.set(longDate);
		Time now = new Time();
		now.setToNow();

		// Basic settings for formatDateTime() we want for all cases.
		int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT
				| DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_CAP_AMPM;

		// If the message is from a different year, show the date and year.
		if (then.year != now.year) {
			format_flags |= DateUtils.FORMAT_SHOW_YEAR
					| DateUtils.FORMAT_SHOW_DATE;
		} else if (then.yearDay != now.yearDay) {
			// If it is from a different day than today, show only the date.
			format_flags |= DateUtils.FORMAT_SHOW_DATE;
		} else {
			// Otherwise, if the message is from today, show the time.
			format_flags |= DateUtils.FORMAT_SHOW_TIME;
		}

		// If the caller has asked for full details, make sure to show the date
		// and time no matter what we've determined above (but still make
		// showing
		// the year only happen if it is a different year from today).
		if (fullFormat) {
			format_flags |= (DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
		}

		return DateUtils.formatDateTime(context, longDate, format_flags);
	}

	public String getNameFromAddress(String address) {
		String contactName = null;
		if (address == null || address == "")
			return address;
		Uri uri_qcontact = Uri.parse(CONTACTS_LOOKUP + address);
		Cursor cur = mContext.getContentResolver().query(uri_qcontact, null,
				null, null, null);
		if (cur.moveToFirst()) {
			contactName = cur.getString(cur.getColumnIndex("display_name"));
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + contactName);
		}
		cur.close();
		cur = null;
		if (contactName == null || contactName == "")
			contactName = address;
		return contactName;

		/*
		 * String name = null; if (address == null || address == "") return
		 * address; Uri uri_person = Uri.withAppendedPath(
		 * ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, address);
		 * String[] projection = new String[] {Phone.DISPLAY_NAME}; Cursor c =
		 * mContext.getContentResolver().query(uri_person, projection, null,
		 * null, null); while (c.moveToNext()) { int index_name =
		 * c.getColumnIndex(Phone.DISPLAY_NAME); name = c.getString(index_name);
		 * } c.close(); c = null; if (name == null || name == "") name =
		 * address; return name;
		 */
	}

}
