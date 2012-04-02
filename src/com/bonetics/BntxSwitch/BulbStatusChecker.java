package com.bonetics.BntxSwitch;

import java.io.IOException;

import android.os.Handler;
import android.widget.ImageButton;

public class BulbStatusChecker extends Handler {

	private Bulb bulb;
	private ImageButton imageButton;
	private Runnable m_statusChecker;
	private int m_interval = 1000;
	
	public BulbStatusChecker(ImageButton imageButton, Bulb bulb) {
		super();
		setImageButton(imageButton);
		setBulb(bulb);

		m_statusChecker = new Runnable() {

			@Override
			public void run() {
				try {
					if(BulbStatusChecker.this.getBulb().isConnected()) {
						if(BulbStatusChecker.this.bulb.getStatus()) {
							BulbStatusChecker.this.getImageButton().setImageResource(R.drawable.bulbon);
						} else {
							BulbStatusChecker.this.getImageButton().setImageResource(R.drawable.bulboff);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BulbStatusChecker.this.postDelayed(this, m_interval);
			}
        	
        };
        
	}
	
	public void resume() {

        m_statusChecker.run();
	}
	
	public void pause() {

    	this.removeCallbacks(m_statusChecker);
	}
	public Bulb getBulb() {
		return bulb;
	}
	public void setBulb(Bulb bulb) {
		this.bulb = bulb;
	}

	public ImageButton getImageButton() {
		return imageButton;
	}

	public void setImageButton(ImageButton imageButton) {
		this.imageButton = imageButton;
	}
}
