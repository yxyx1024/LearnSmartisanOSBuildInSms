package com.allenwang.pjtsms.aty;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.allenwang.pjtsms.R;
import com.allenwang.pjtsms.SmsBean;
import com.allenwang.pjtsms.SmsProcessor;
import com.allenwang.pjtsms.adapter.ConversationListAdapter;

public class AtyConversationList extends Activity {

	private RelativeLayout mRlTitle = null;
	private Button mBtnEdit = null;
	private Button mBtnNew = null;
	private ListView mLvConversationList = null;
	private ConversationListAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_conversation_list);

		mRlTitle = (RelativeLayout) findViewById(R.id.rlTitle);
		mBtnEdit = (Button) mRlTitle.findViewById(R.id.btnEdit);
		mBtnNew = (Button) mRlTitle.findViewById(R.id.btnNew);
		mLvConversationList = (ListView) findViewById(R.id.lvConversation);

		final SmsProcessor processor = new SmsProcessor(this);
		final ArrayList<SmsBean> addedInfosConversationList = processor
				.addInfosToConversation(processor.getConversations(0));

		mAdapter = new ConversationListAdapter(this, addedInfosConversationList);
		mLvConversationList.setAdapter(mAdapter);

		mLvConversationList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SmsBean addedInfosConversation = addedInfosConversationList
						.get(position);
				String address = addedInfosConversation.getAddress();
				String thread_id = addedInfosConversation.getThread_id();
				String name = addedInfosConversation.getName();
				Intent i = new Intent(AtyConversationList.this,
						AtySingleConversation.class);
				i.putExtra("address", address);
				i.putExtra("thread_id", thread_id);
				i.putExtra("name", name);
				startActivity(i);
			}
		});

		mBtnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mBtnNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(AtyConversationList.this,
						AtyNewConversation.class);
				startActivity(i);
			}
		});
	}

}
