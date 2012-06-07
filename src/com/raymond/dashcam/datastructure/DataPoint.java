package com.raymond.dashcam.datastructure;

public class DataPoint {
	private String _latitude;
	private String _longitude;
	private String _orientation;
	private String _speed;
	private String _time;
	
	public DataPoint(String latitude,String longitude,String orientation,String speed,String time){
		set_latitude(latitude);
		set_longitude(longitude);
		set_orientation(orientation);
		set_speed(speed);
		set_time(time);
	}

	public String get_latitude() {
		return _latitude;
	}

	private void set_latitude(String _latitude) {
		this._latitude = _latitude;
	}

	public String get_speed() {
		return _speed;
	}

	private void set_speed(String _speed) {
		this._speed = _speed;
	}

	public String get_longitude() {
		return _longitude;
	}

	private void set_longitude(String _longitude) {
		this._longitude = _longitude;
	}

	public String get_orientation() {
		return _orientation;
	}

	private void set_orientation(String _orientation) {
		this._orientation = _orientation;
	}

	public String get_time() {
		return _time;
	}

	private void set_time(String _time) {
		this._time = _time;
	}
}
