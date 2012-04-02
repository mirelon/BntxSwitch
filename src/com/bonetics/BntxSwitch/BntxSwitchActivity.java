package com.bonetics.BntxSwitch;

import java.io.IOException;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BntxSwitchActivity extends Activity {
	
	private Bulb bulb;
	private Resources resources;
	private Handler m_handler;
	private int m_interval = 1000;
	private Runnable m_statusChecker;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resources = getResources();
        bulb = new Bulb(resources);
        
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
        
        m_interval = 1000;
        m_handler = new Handler();
        m_statusChecker = new Runnable() {

			@Override
			public void run() {
				try {
					if(bulb.getStatus()) {
						imageButton1.setImageResource(R.drawable.bulbon);
					} else {
						imageButton1.setImageResource(R.drawable.bulboff);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m_handler.postDelayed(this, m_interval);
				
			}
        	
        };
    }
    
    @Override
    public void onStart() {
    	super.onStart();
        m_statusChecker.run();
    }
    
    @Override
    public void onStop() {
    	m_handler.removeCallbacks(m_statusChecker);
    	super.onStop();
    }
}