package com.bonetics.BntxSwitch;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.res.Resources;
import android.util.Log;

public class Bulb {

	private boolean turnedOn;

	private String url_turn_on;
	private String url_turn_off;
	private String url_status;
	
	private boolean connected;

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		Log.d("Bulb", "setConnected: " + String.valueOf(connected));
		this.connected = connected;
	}

	public Bulb(Resources resources) {
		url_turn_on = resources.getString(R.string.url_turn_on);
		url_turn_off = resources.getString(R.string.url_turn_off);
		url_status = resources.getString(R.string.url_status);
	}

	public boolean isTurnedOn() {
		return turnedOn;
	}

	public void setTurnedOn(boolean turnedOn) {
		this.turnedOn = turnedOn;
	}

	public void turnOn() {
		if(!isConnected())return;
		Log.d("Bulb", "turnOn");
		this.sendRequest(this.url_turn_on);
		this.setTurnedOn(true);
	}

	public void turnOff() {
		if(!isConnected())return;
		Log.d("Bulb", "turnOff");
		this.sendRequest(this.url_turn_off);
		this.setTurnedOn(false);
	}

	private void sendRequest(String url) {
		try {
			Jsoup.connect(url).get();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean getStatus() throws IOException {
		if(!isConnected()) {
			throw new IOException("Bulb::getStatus(): not connected");
		}
		Document doc = Jsoup.connect(this.url_status).get();
		return doc.select("center > font").html().equals("ON");
	}

}
