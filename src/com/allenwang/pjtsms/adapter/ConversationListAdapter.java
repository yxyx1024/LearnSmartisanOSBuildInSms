package com.allenwang.pjtsms.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allenwang.pjtsms.R;
import com.allenwang.pjtsms.SmsBean;

/**
 * 主界面短信列表的适配器类
 * @author AllenWang
 *
 */

public class ConversationListAdapter extends BaseAdapter {
	
	private Context mContext = null;
	//成员变量,存放主界面中要显示的短信对象的容器
	private ArrayList<SmsBean> mAddedInfosConversationList = null;
	
	//构造方法,该Adapter实在主界面类中初始化的,初始化时传入主界面的Context对象
	public ConversationListAdapter(Context context,
			ArrayList<SmsBean> addedInfosConversationList) {
		this.mContext = context;
		this.mAddedInfosConversationList = addedInfosConversationList;
	}

	@Override
	public int getCount() {
		return mAddedInfosConversationList.size();
	}

	@Override
	public SmsBean getItem(int position) {
		return mAddedInfosConversationList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	//每当列表中需要显示新的列表项时调用该方法
	public View getView(int position, View convertView, ViewGroup parent) {
		//定义并初始化一个中转站类
		ViewHolder vh = new ViewHolder();
		//若convertView==null说明没有可复用的View
		if(convertView == null) {
			//使用传入的主界面的Context对象构造一个View对象(列表项)并赋值给convertView
			convertView = LayoutInflater.from(mContext).inflate(R.layout.conversation_list_adapter, parent, false);
			//将列表项中的4个部分分别赋值给中转站中的4个对象
			vh.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
			vh.tvCount = (TextView) convertView.findViewById(R.id.tvCount);
			vh.tvName = (TextView) convertView.findViewById(R.id.tvName);
			vh.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			//否则说明存在可以复用的View
		} else {
			//否则将convertView中保存的4个对象赋值给中转站
			//Returns the Object stored in this view as a tag
			vh = (ViewHolder) convertView.getTag();
		}
		//现在无论converView是否为空,列表项中的4个对象都保存在中转站中了
		//取出与列表项对应的短信对象中的相关数据,并设置中转站中4个对象的文字
		vh.tvBody.setText(mAddedInfosConversationList.get(position).getSnippet());
		vh.tvCount.setText("(" + mAddedInfosConversationList.get(position).getMsg_count() + ")");
		vh.tvName.setText(mAddedInfosConversationList.get(position).getName());
		vh.tvTime.setText(mAddedInfosConversationList.get(position).getTime());
		//将中转站中的4个对象传回给convertView(列表项)
		convertView.setTag(vh);
		vh = null;
		//将列表项返回
		return convertView;
	}
	
	//中转站类
	public class ViewHolder {
		public TextView tvBody;  
		public TextView tvCount;  
		public TextView tvName;  
        public TextView tvTime;  
	}

}
