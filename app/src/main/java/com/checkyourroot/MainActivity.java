package com.checkyourroot;

import android.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.widget.*;
import java.io.*;

import java.lang.Process;
import android.graphics.drawable.*;

import com.checkyourroot.RateThisApp;
import android.view.*;
import android.util.*;
import android.view.animation.*;

public class MainActivity extends Activity {
	
	Button bn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
		getActionBar().show();
		getActionBar().setIcon(R.drawable.ic_launcher);
		}
		
		//setStatusBarColor(findViewById(R.id.statusBarBackground),getResources().getColor(android.R.color.holo_blue_light));
		
		if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(Color.parseColor("#0097a7"));
			getActionBar().setIcon(R.drawable.ic_launcher);
			getActionBar().show();
		}

		TextView tv = (TextView) findViewById(R.id.mainTextView1);
		TextView have = (TextView) findViewById(R.id.textHave);
		bn = (Button) findViewById(R.id.mainButton1);
		
		final Animation animFlip = AnimationUtils.loadAnimation(this, R.anim.anim_flip);
		final Animation animFlipBack = AnimationUtils.loadAnimation(this, R.anim.anim_flipback);
		
		bn.setOnClickListener(new View.OnClickListener(){
				public void onClick(final View v) {
					new Handler().postDelayed(new Runnable(){
							@Override
							public void run(){
								v.startAnimation(animFlipBack);
								bn = (Button) findViewById(R.id.mainButton1);
								bn.setText(R.string.check_again);
							}
						},250);
					v.startAnimation(animFlip);
					bncyr();
				}
		});

		tv.setTextColor(Color.parseColor("#ffeeeeee"));
		tv.setText(R.string.click_for_check_root);
		
		have.setText(" ");
//		
//
//		Process p;
//		try {
//			p = Runtime.getRuntime().exec("su");
//			DataOutputStream os = new DataOutputStream(p.getOutputStream());
//			//os.writeBytes("su\n");
//			os.writeBytes("exit\n");
//			os.flush();
//			try {
//				p.waitFor();
//				if (p.exitValue() == 0) {
//					tv.setTextColor(Color.GREEN);
//					tv.setText(R.string.you_have_root_permission);
//					have.setTextColor(Color.GREEN);
//					have.setText("√");
//				} else {
//					tv.setTextColor(Color.RED);
//					tv.setText(R.string.you_have_root_but_not_permission);
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		System.exit(0);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		RateThisApp.onStart(this);
		RateThisApp.showRateDialogIfNeeded(this);
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
	}
	
	public void setStatusBarColor(View statusBar,int color){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			Window w = getWindow();
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			int actionBarHeight = getActionBarHeight();
			int statusBarHeight = getStatusBarHeight();
			statusBar.getLayoutParams().height = actionBarHeight + statusBarHeight;
			statusBar.setBackgroundColor(color);
		}
	}
	
	public int getActionBarHeight(){
		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize,tv,true)){
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
		}
		return actionBarHeight;
	}
	
	public int getStatusBarHeight(){
		int result = 0;
		int resourceld = getResources().getIdentifier("status_bar_height","dimen","android");
		if (resourceld > 0){
			result = getResources().getDimensionPixelSize(resourceld);
		}
		return result;
	}
	
	public void bncyr(){
		final TextView tv = (TextView) findViewById(R.id.mainTextView1);
		final TextView have = (TextView) findViewById(R.id.textHave);
		
		final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, getString(R.string.please_wait) ,"Check Root...", true);
		ringProgressDialog.setCancelable(false);
		
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){
					Process p;
					try {
						// Here you should write your time consuming task...
						// Let the progress ring for 1 seconds...
						p = Runtime.getRuntime().exec("su");
						DataOutputStream os = new DataOutputStream(p.getOutputStream());
						//os.writeBytes("sleep 1\n");
						os.writeBytes("exit\n");
						os.flush();
						try {
							p.waitFor();
							if (p.exitValue() == 0) {
								tv.setTextColor(Color.GREEN);
								tv.setText(R.string.you_have_root_permission);
								have.setTextColor(Color.GREEN);
								have.setText("√");
								ringProgressDialog.dismiss();
							} else {
								tv.setTextColor(Color.RED);
								tv.setText(R.string.you_have_root_but_not_permission);
								have.setText("X");
								have.setTextColor(Color.RED);
								ringProgressDialog.dismiss();
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						tv.setText(R.string.not_root_permission);
						tv.setTextColor(Color.RED);
						have.setText("X");
						have.setTextColor(Color.RED);
						ringProgressDialog.dismiss();
					}
				}
			},1500);
		
	}
	
//	public void prog(){
//		Process p;
//		try {
//			p = Runtime.getRuntime().exec("su");
//			DataOutputStream os = new DataOutputStream(p.getOutputStream());
//			//os.writeBytes("su\n");
//			os.writeBytes("exit\n");
//			os.flush();
//			try {
//				p.waitFor();
//				if (p.exitValue() == 0) {
//					tv.setTextColor(Color.GREEN);
//					tv.setText(R.string.you_have_root_permission);
//					have.setTextColor(Color.GREEN);
//					have.setText("√");
//					ringProgressDialog.dismiss();
//				} else {
//					tv.setTextColor(Color.RED);
//					tv.setText(R.string.you_have_root_but_not_permission);
//					have.setText("X");
//					have.setTextColor(Color.RED);
//					ringProgressDialog.dismiss();
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			tv.setText(R.string.not_root_permission);
//			tv.setTextColor(Color.RED);
//			have.setText("X");
//			have.setTextColor(Color.RED);
//			ringProgressDialog.dismiss();
//		}
//	}

}

