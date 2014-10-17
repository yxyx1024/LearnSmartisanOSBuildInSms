package com.allenwang.pjtsms.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.allenwang.pjtsms.ConversationBean;
import com.allenwang.pjtsms.R;

/**
 * 单个联系人短信界面的短信列表的适配器类
 * @author AllenWang
 *
 */

public class SingleConversationAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<ConversationBean> mConversationList = null;
	
	private LinearLayout mLlListItem = null;
	private LinearLayout mLlMessage = null;
	private LayoutInflater mInflater = null;
	private TextView mTvDate = null;
	private TextView mTvBody = null;

	//构造方法,该Adapter是在单个联系人短信界面初始化的,初始化时传入该界面的Context对象
	public SingleConversationAdapter(Context context,
			ArrayList<ConversationBean> conversationList) {
		this.mContext = context;
		this.mConversationList = conversationList;
	}

	@Override
	public int getCount() {
		return mConversationList.size();
	}

	@Override
	public ConversationBean getItem(int position) {
		return mConversationList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	//每当列表中需要显示新的列表项时调用该方法
	public View getView(int position, View convertView, ViewGroup parent) {
		//获取与该列表项对应的短信对象
		ConversationBean bean = getItem(position);
		//获取该对象中保存的布局方式
		int layoutId = bean.getLayoutId();
		//根据传入的单个联系人短信界面的Context对象初始化一个LinearLayout(列表项的基础层)
		mLlListItem = new LinearLayout(mContext);
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(layoutId, mLlListItem, true);
		mLlListItem.setBackgroundColor(Color.TRANSPARENT);
		
		mLlMessage = (LinearLayout) mLlListItem
				.findViewById(R.id.llMessageBackground);
		mTvBody = (TextView) mLlListItem.findViewById(R.id.tvMessageBody);
		mTvBody.setText(bean.getBody());
		mTvDate = (TextView) mLlListItem.findViewById(R.id.tvMessageDate);
		mTvDate.setText(bean.getDate());

		addListener(mTvBody, mTvDate, mLlMessage, bean);
		return mLlListItem;
	}

	public void addListener(TextView tvBody, TextView tvDate,
			LinearLayout llMessage, ConversationBean bean) {

		llMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		llMessage.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				return false;
			}
		});
	}

}
