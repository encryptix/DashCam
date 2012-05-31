package com.raymond.dashcam;

import com.raymond.dashcam.Functions.Screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DashCamActivity extends Activity {
    /** Called when the activity is first created. */
    Button gps;
    Button camera;
    
	private Functions _functions;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Functions.createInstance(this);
        _functions = Functions.getInstance();
        
        camera = _functions.getButton(this,R.id.cameraButton);
        gps = _functions.getButton(this,R.id.gpsButton);
        gps.setOnClickListener(_listenerGPS);
        camera.setOnClickListener(_listenerCamera);
    }
    
    private OnClickListener _listenerGPS = new OnClickListener() {
        public void onClick(View v) {
        	_functions.changeScreen(Screens.GPS);
        }
    };
    
    private OnClickListener _listenerCamera = new OnClickListener() {
        public void onClick(View v) {
        	_functions.changeScreen(Screens.CAMERA);
        }
    };
}