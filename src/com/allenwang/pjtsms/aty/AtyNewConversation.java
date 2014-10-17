package com.allenwang.pjtsms.aty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.allenwang.pjtsms.R;

public class AtyNewConversation extends Activity {

	private RelativeLayout mRlTitle = null;
	private Button mBtnBack = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_new_conversation);
		
		mRlTitle = (RelativeLayout) findViewById(R.id.rlTitle);
		mBtnBack = (Button) mRlTitle.findViewById(R.id.btnBack);
		
		mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
}
