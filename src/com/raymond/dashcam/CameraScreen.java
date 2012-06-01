package com.raymond.dashcam;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraScreen extends Activity{
	private Functions _function;
	private Button _home;
	private Button _record;
	
	Button capture;
	boolean isPreview;
	
	CameraLive cpt;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview);
        _function = Functions.getInstance();
        _record = _function.getButton(this,R.id.button_capture);
        //_home = _function.getButton(this,R.id.B_camera_Home);
        //_home.setOnClickListener(_function.listenerHome);
    	capture = _function.getButton(this, R.id.button_captureCP);
    	
    	capture.setOnClickListener(listenerPreview);
    	isPreview = false;
    	
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        cpt = new CameraLive(this.getApplicationContext(),preview);
    }
    
    OnClickListener listenerPreview = new OnClickListener() {
        public void onClick(View v) {
        	if(isPreview){
        		_function.makeToast("STOPPING RECORD");
        		cpt.UI_StopPreview();
        		capture.setText("Record");
        		isPreview=false;
        	}else{
        		_function.makeToast("STARTING RECORD");
        		cpt.UI_StartPreview();
        		capture.setText("Stop");
        		isPreview=true;
        	}
        }
    };
}
