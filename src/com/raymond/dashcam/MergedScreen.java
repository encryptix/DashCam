package com.raymond.dashcam;

import com.raymond.dashcam.datastructure.DataPoint;

import android.app.Activity;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

//GPS Stuff should really be a thread which notifies this as a parent (still not hte best but...)
public class MergedScreen extends Activity{
	private Functions _function;
	private Button _capture;
	boolean _isRunning;
	MergedScreen me;
	CameraLive camera;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = this;
        setContentView(R.layout.camera_preview);
        _function = Functions.getInstance();
        //_home = _function.getButton(this,R.id.B_camera_Home);
        //_home.setOnClickListener(_function.listenerHome);
    	_capture = _function.getButton(this, R.id.button_captureCP);
    	
    	_capture.setOnClickListener(_listenerStart);
    	_isRunning = false;
    	
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        camera = new CameraLive(this.getApplicationContext(),preview);
        
        //GPS Stuff
        _locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
      
    private OnClickListener _listenerStart = new OnClickListener() {
        public void onClick(View v) {
        	if(_isRunning){
        		_function.makeToast("STOPPING RECORD");
        		camera.UI_StopPreview();
        		_capture.setText("Record");
        		onStop();
        		_isRunning=false;
        		
        	}else{
        		_function.makeToast("STARTING RECORD");
        		String filename = camera.UI_StartPreview();
        		startLogging(filename);
        		_capture.setText("Stop");
        		_isRunning=true;
        		onResume();
        	}
        }
    };
    
    //Logging stuff
    private Logger _log;
    
    private void startLogging(String filename){
    	_function.makeToast("Filename:"+filename);
    	_log = new Logger(filename);
    }
    
    //GPS stuff
	private HandlerThread _gpsThread; 
    private LocationManager _locationManager; 
    
    protected void notifyPoint(DataPoint point){
    	_function.makeToast("Point Recieved");
    	if(_isRunning){
	    	camera.setOverlayMessage(point);
	    	_log.addPoint(point);
    	}
    }
	
    @Override 
    protected void onResume() {
    	super.onResume();
    	if(_isRunning){
            
            _gpsThread = new HandlerThread("GPS Thread"); 
            _gpsThread.start(); 
            new Handler(_gpsThread.getLooper()).post( 
                    new Runnable() { 
                            @Override 
                            public void run() { 
                            	_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, GpsThingy.getLocationListener(me)); 
                            } 
                    });
    	}
    }
    
    @Override 
    protected void onPause(){ 
    	super.onPause();
    	if(_isRunning){
            _locationManager.removeUpdates(GpsThingy.getLocationListener(me)); 
            _gpsThread.getLooper().quit();
    	}
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	if(_isRunning){
    		_locationManager.removeUpdates(GpsThingy.getLocationListener(me));
    	}
    }
}