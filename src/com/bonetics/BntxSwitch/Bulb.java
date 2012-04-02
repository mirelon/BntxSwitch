package com.bonetics.BntxSwitch;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.res.Resources;

public class Bulb {

	private boolean turnedOn;

	private String url_turn_on;
	private String url_turn_off;
	private String url_status;

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
		this.sendRequest(this.url_turn_on);
		this.setTurnedOn(true);
	}

	public void turnOff() {
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
		Document doc = Jsoup.connect(this.url_status).get();
		return doc.select("center > font").html().equals("ON");

	}

}
