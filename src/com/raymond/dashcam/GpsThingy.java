package com.raymond.dashcam;

import java.util.Date;

import com.raymond.dashcam.Functions.Screens;
import com.raymond.dashcam.datastructure.DataPoint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

public class GpsThingy{
		private static Functions _function = Functions.getInstance();
		
	    private static DialogInterface.OnClickListener _enableGpsPromptListener = new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
	            case DialogInterface.BUTTON_POSITIVE:
	            	_function.changeScreen(Screens.GPSSETTING);
	                break;
	            case DialogInterface.BUTTON_NEGATIVE:
	                _function.changeScreen(Screens.HOME);
	                break;
	            }
	        }
	    };

		private static final LocationListener mLocationListener = new 
				LocationListener() { 
				                @Override 
				                public void onLocationChanged(Location location) { 
				                	_function.makeToast("onLocationChanged Called");
				                	String latitude=""+location.getLatitude();
				                	String longitude=""+location.getLongitude();
				                	
				                	String orientation = ""+location.getBearing();
				                	String speed = ""+location.getSpeed();
				                	
				                	long timestamp = location.getTime();
				                	Date date = new Date(timestamp);
				                	String time = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
				             	    DataPoint point = new DataPoint(latitude,longitude,orientation,speed,time);
				                	par.notifyPoint(point);
				                	
				                } 
				                @Override 
				                public void onProviderDisabled(String provider) { 
				                	AlertDialog.Builder builder = new AlertDialog.Builder(par);
				                	builder.setMessage("Enable GPS?").setPositiveButton("Yes", _enableGpsPromptListener).setNegativeButton("No", _enableGpsPromptListener).show();
				                } 
				                @Override 
				                public void onProviderEnabled(String provider) {
				                	_function.makeToast("Gps Enabled");
				                }
				                @Override
				                public void onStatusChanged(String arg0, int status, Bundle arg2) {
				                	_function.makeToast("onStatusChanged Called");
				                	switch  (status) {
				                	case LocationProvider.OUT_OF_SERVICE:
				                		_function.makeToast("Status Changed: Out of Service");
				                		break;
				                	case LocationProvider.TEMPORARILY_UNAVAILABLE:
				                		_function.makeToast("Status Changed: Temporarily Unavailable");
				                		break;
				                	case LocationProvider.AVAILABLE:
				                		_function.makeToast("Status Changed: Available");
				                		break;
				                	}
				                }
		};
		
		private static MergedScreen par;
		public static LocationListener getLocationListener(MergedScreen parent){
			par = parent;
			return mLocationListener;
		}
}