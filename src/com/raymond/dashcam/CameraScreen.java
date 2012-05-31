package com.raymond.dashcam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class CameraScreen extends Activity{
	private Functions _function;
	private Button _home;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        _function = Functions.getInstance();
        _home = _function.getButton(this,R.id.B_camera_Home);
        _home.setOnClickListener(_function.listenerHome);
    }
}
