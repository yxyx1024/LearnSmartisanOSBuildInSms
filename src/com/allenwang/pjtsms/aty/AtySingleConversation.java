package com.allenwang.pjtsms.aty;

import java.util.ArrayList;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenwang.pjtsms.ConversationBean;
import com.allenwang.pjtsms.R;
import com.allenwang.pjtsms.SmsProcessor;
import com.allenwang.pjtsms.adapter.SingleConversationAdapter;

public class AtySingleConversation extends Activity implements OnClickListener {

	private LinearLayout mLlOperate = null;
	private LinearLayout mLlSend = null;
	private RelativeLayout mRlTitle = null;
	private ListView mLvConversationList = null;
	private Button mBtnBack = null;
	private Button mBtnCall = null;
	private Button mBtnContact = null;
	private Button mBtnEdit = null;
	private Button mBtnSend = null;
	private EditText mEtSend = null;
	private TextView mTvName = null;

	private boolean isContactExist = true;

	private AsyncQueryHandler asyncQuery = null;
	private ArrayList<ConversationBean> conversationList = null;
	private SmsProcessor processor = null;

	private String name = null;
	private String address = null;
	private String thread_id = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_single_conversation);
		processor = new SmsProcessor();
		mLlOperate = (LinearLayout) findViewById(R.id.llOperate);
		mLlSend = (LinearLayout) findViewById(R.id.llSend);
		mRlTitle = (RelativeLayout) findViewById(R.id.rlTitle);
		mLvConversationList = (ListView) findViewById(R.id.lvConversation);
		mBtnBack = (Button) mRlTitle.findViewById(R.id.btnBack);
		mBtnCall = (Button) mLlOperate.findViewById(R.id.btnCall);
		mBtnContact = (Button) mLlOperate.findViewById(R.id.btnContact);
		mBtnEdit = (Button) mRlTitle.findViewById(R.id.btnEdit);
		mBtnSend = (Button) mLlSend.findViewById(R.id.btnSend);
		mTvName = (TextView) mRlTitle.findViewById(R.id.tvName);
		mEtSend = (EditText) mLlSend.findViewById(R.id.etSend);
		address = getIntent().getStringExtra("address");
		name = getIntent().getStringExtra("name");
		thread_id = getIntent().getStringExtra("thread_id");
		if (name.equals(address)) {
			isContactExist = false;
			mBtnContact.setText("添加到联系人");
		}

		System.out.println(thread_id);
		mTvName.setText(name);

		initialize(thread_id);

		mBtnBack.setOnClickListener(this);
		mBtnCall.setOnClickListener(this);
		mBtnContact.setOnClickListener(this);
		mBtnEdit.setOnClickListener(this);
		mBtnSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAdd:

			break;
		case R.id.btnBack:
			finish();
			break;
		case R.id.btnCall:
			Uri uri_call = Uri.parse("tel:" + address);
			Intent i = new Intent(Intent.ACTION_CALL, uri_call);
			startActivity(i);
			break;
		case R.id.btnContact:
			if (isContactExist) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
				startActivity(intent);
			} else {
				Intent intent = new Intent(Intent.ACTION_EDIT,
						Uri.parse("content://com.android.contacts/contacts/"
								+ "1"));
				startActivity(intent);
			}
			break;
		case R.id.btnEdit:

			break;
		case R.id.btnSend:
			String context = mEtSend.getText().toString();
			break;
		}
	}
	
	public void initialize(String thread_id) {
		asyncQuery = new MessageAsynQueryHandler(getContentResolver());
		conversationList = new ArrayList<ConversationBean>();
		Uri uri = Uri.parse(SmsProcessor.URI_SMS_ALL);
		String[] projection = new String[] { "date", "address", "person",
				"body", "type" };
		asyncQuery.startQuery(0, null, uri, projection, "thread_id = "
				+ thread_id, null, "date asc");
	}

	private class MessageAsynQueryHandler extends AsyncQueryHandler {

		public MessageAsynQueryHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor c) {
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				int layoutId = 0;
				for (int i = 0; i < c.getCount(); i++) {
					c.moveToPosition(i);
					if (c.getInt(c.getColumnIndex("type")) == 1) {
						layoutId = R.layout.single_conversation_adapter_message_in;
					} else {
						layoutId = R.layout.single_conversation_adapter_message_out;
					}
					ConversationBean bean = new ConversationBean(
							c.getString(c.getColumnIndex("address")),
							processor.formatTimeStampString(
									AtySingleConversation.this,
									c.getLong(c.getColumnIndex("date")), false),
							c.getString(c.getColumnIndex("body")), layoutId);
					conversationList.add(bean);
				}

				if (conversationList.size() > 0) {
					mLvConversationList
							.setAdapter(new SingleConversationAdapter(
									AtySingleConversation.this,
									conversationList));
					// 隐藏列表项之间的分割线
					mLvConversationList.setDivider(null);
					// 界面显示在最后一条短信上
					mLvConversationList.setSelection(conversationList.size());
				} else {
					Toast.makeText(AtySingleConversation.this, "无短信",
							Toast.LENGTH_LONG).show();
				}
			}
			super.onQueryComplete(token, cookie, c);
		}
	}

}
