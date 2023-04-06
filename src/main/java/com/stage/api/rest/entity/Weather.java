package com.stage.api.rest.entity;

public class Weather {

	private String dataID;
	private String location;
	private String windspeed;
	private String windspeedUnit;
	private String waveheight;
	private String waveheightUnit;
	private String winddirection;
	private String winddirectionUnit;
	private long timestamp;
	
	public Weather(
			String dataID, 
			String location, 
			String windspeed, 
			String windspeedUnit,
			String waveheight, 
			String waveheightUnit, 
			String winddirection, 
			String winddirectionUnit, 
			long timestamp
	) {
		this.dataID = dataID;
		this.location = location;
		this.windspeed = windspeed;
		this.windspeedUnit = windspeedUnit;
		this.waveheight = waveheight;
		this.waveheightUnit = waveheightUnit;
		this.winddirection = winddirection;
		this.winddirectionUnit = winddirectionUnit;
		this.timestamp = timestamp;
	}

	public String getDataID() {
		return dataID;
	}

	public void setDataID(String dataID) {
		this.dataID = dataID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(String windspeed) {
		this.windspeed = windspeed;
	}

	public String getWindspeedUnit() {
		return windspeedUnit;
	}

	public void setWindspeedUnit(String windspeedUnit) {
		this.windspeedUnit = windspeedUnit;
	}

	public String getWaveheight() {
		return waveheight;
	}

	public void setWaveheight(String waveheight) {
		this.waveheight = waveheight;
	}

	public String getWaveheightUnit() {
		return waveheightUnit;
	}

	public void setWaveheightUnit(String waveheightUnit) {
		this.waveheightUnit = waveheightUnit;
	}

	public String getWinddirection() {
		return winddirection;
	}

	public void setWinddirection(String winddirection) {
		this.winddirection = winddirection;
	}

	public String getWinddirectionUnit() {
		return winddirectionUnit;
	}

	public void setWinddirectionUnit(String winddirectionUnit) {
		this.winddirectionUnit = winddirectionUnit;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
