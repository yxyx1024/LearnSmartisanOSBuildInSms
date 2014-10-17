package com.allenwang.pjtsms;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 规定与单个联系人对话页面中的短信显示的格式的类
 * @author AllenWang
 *
 */

public class MessageTextView extends TextView {

	//画笔
	private Paint mPaint = null;
	//最小值
	private float mMinTextSize = 0;
	//最大值
	private float mMaxTextSize = 0;
	
	//构造方法,调用父类的构造方法
	public MessageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//调用初始化方法
		initialise();
	}

	//初始化方法
	private void initialise() {
		//初始化画笔
		mPaint = new Paint();
		//将得到的画笔赋值给自己定义的
		//Returns the base paint used for the text.
		//Please use this only to consult the Paint's properties and not to change them.
		mPaint.set(getPaint());
		//获取TextView的默认文本宽度,并赋值给最大值
		//Returns the size (in pixels) of the default text size in this TextView.
		mMaxTextSize = this.getTextSize();
System.out.println(">>>>>>>>>>MaxTextSize" + mMaxTextSize + "<<<<<<<<<<");
	}

	//调整文本的方法
	//在onSizeChanged()中调用时传入的参数分别是文本内容和当前TextView的宽度
	private void refitText(String text, int viewWidth) {
		//若文本框宽度大于0
		if (viewWidth > 0) {
			//文本框宽度-(文本框左侧裕量+文本框右侧裕量)
			//(左裕量+右裕量)=总宽-文本框宽度
			//文本框宽度*2-总宽
			//Returns the left padding in pixels.
			//Returns the right padding in pixels
			int availableWidth = viewWidth-this.getPaddingLeft()-this.getPaddingRight();
			//将默认文本宽度赋值给attempt变量
			float attempt = mMaxTextSize;
			//当试探值大于mMinTextSize(0)且文本宽度大于可用值
			while((attempt > mMinTextSize) && (mPaint.measureText(text)>availableWidth)) {
				attempt -= 1;
				if (attempt <= mMinTextSize) {
					attempt = mMinTextSize;
					break;
				}
			}
		}
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int before,
			int after) {
		super.onTextChanged(text, start, before, after);
		refitText(text.toString(), this.getWidth());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (w != oldw) {
			refitText(this.getText().toString(), w);
		}
	}
}
