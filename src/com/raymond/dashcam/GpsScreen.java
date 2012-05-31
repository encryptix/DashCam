package com.raymond.dashcam;

import java.io.FileOutputStream;
import java.util.Date;

import com.raymond.dashcam.Functions.Screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GpsScreen extends Activity implements LocationListener{
	static String FILENAME = "gps_log_ray";
	
	private Button _home;
	private TextView _data;
	
	Functions _function;
	
	private LocationManager _lm;
	private int _noOfFixes;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        _function = Functions.getInstance();
        _home = _function.getButton(this,R.id.B_gps_Home);
        _data = _function.getTextView(this, R.id.TB_gps_data);
        _home.setOnClickListener(_function.listenerHome);
        
        _noOfFixes=0;
        _lm = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
        
    private DialogInterface.OnClickListener _enableGpsPromptListener = new DialogInterface.OnClickListener() {
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
    

    @Override
	public void onResume() {
		_function.makeToast("onResume Called");
		_lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this);
		super.onResume();
	}
    
    @Override
	protected void onPause() {
    	_function.makeToast("onPause Called");
    	_lm.removeUpdates(this);
    	super.onResume();
    }
    
	@Override
	protected void onStop() {
		_function.makeToast("onStop Called");
		_noOfFixes=0;
		super.onStop();
	}
   
    @Override
	public void onProviderDisabled(String arg0) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Enable GPS?").setPositiveButton("Yes", _enableGpsPromptListener).setNegativeButton("No", _enableGpsPromptListener).show();
	}

    @Override
	public void onProviderEnabled(String arg0) {
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
	
	@Override
	public void onLocationChanged(Location location) {
		_function.makeToast("onLocationChanged Called");
		_noOfFixes++;
		String latitude=""+location.getLatitude();
		String longitude=""+location.getLongitude();
		
		String orientation = ""+location.getBearing();
		String speed = ""+location.getSpeed();
		
		long timestamp = location.getTime();
		_function.makeToast("TS: "+timestamp);
		Date date = new Date(timestamp);
		String time = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		String data = "Lat/Long: "+latitude+","+longitude+"\nSpeed/Orien: "+speed+","+orientation+"\nFixes: "+_noOfFixes+"\nTime: "+time;
		_data.setText(data);
		
		try{
			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
			fos.write(data.getBytes());
			fos.close();
		}catch(Exception e){};
	}
}
