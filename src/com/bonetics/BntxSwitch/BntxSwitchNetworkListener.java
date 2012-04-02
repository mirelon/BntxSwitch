package com.bonetics.BntxSwitch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

public class BntxSwitchNetworkListener extends BroadcastReceiver {

	private Bulb bulb;
	private TextView textView;

	public BntxSwitchNetworkListener(TextView textView, Bulb bulb) {
		super();
		setTextView(textView);
		setBulb(bulb);
	}

	public Bulb getBulb() {
		return bulb;
	}

	public void setBulb(Bulb bulb) {
		this.bulb = bulb;
	}

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			NetworkInfo info = intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			Log.d("BntxSwitchNetworkListener", "Connection status changed: "
					+ info.toString());
			
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			Log.d("BntxSwitchNetworkListener", "wifiManager: " + wifiManager.toString());
			Log.d("BntxSwitchNetworkListener", "wifiInfo: " + wifiInfo.toString());
			Log.d("BntxSwitchNetworkListener", "SSID: " + wifiInfo.getSSID());
			
			bulb.setConnected(info.isAvailable());
			getTextView().setVisibility(
					bulb.isConnected() ? TextView.INVISIBLE : TextView.VISIBLE);
		}
	}
}
