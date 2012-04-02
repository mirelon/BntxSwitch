package com.bonetics.BntxSwitch;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.net.ConnectivityManager;

public class BntxSwitchActivity extends Activity {
	
	private Bulb bulb;
	private Resources resources;
	private IntentFilter mNetworkStateChangedFilter;
	private BroadcastReceiver mNetworkStateIntentReceiver;
	private BulbStatusChecker bulbStatusChecker;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("log","Starting BntxSwitch, log it.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resources = getResources();
        bulb = new Bulb(resources);
        
        bulbStatusChecker = new BulbStatusChecker((ImageButton) findViewById(R.id.imageButton1), bulb);
        
        mNetworkStateChangedFilter = new IntentFilter();
    	mNetworkStateChangedFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkStateIntentReceiver = new BntxSwitchNetworkListener((TextView) findViewById(R.id.textView1), bulb);        
        
        final ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(BntxSwitchActivity.this.bulb.isTurnedOn()) {
					BntxSwitchActivity.this.bulb.turnOff();
				} else {
					BntxSwitchActivity.this.bulb.turnOn();
				}
			}
        	
        });
    }
    
    @Override
    public void onResume() {
    	Log.d("log", "resume");
    	super.onResume();
    	bulbStatusChecker.resume();
        registerReceiver(mNetworkStateIntentReceiver, mNetworkStateChangedFilter);
    }
    
    @Override
    public void onPause() {
    	Log.d("log","pause");
    	unregisterReceiver(mNetworkStateIntentReceiver);
    	bulbStatusChecker.pause();
    	super.onPause();
    }
}