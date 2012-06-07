package com.raymond.dashcam;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Functions {
	public enum Screens {
	    HOME,GPS,CAMERA, GPSSETTING 
	}
	
	
	private static Functions _instance;	
	public static void createInstance(DashCamActivity parent){
		_instance = new Functions(parent);
	}
	public static Functions getInstance(){
		return _instance;
	}
	
	private DashCamActivity _parent;
	private Functions(DashCamActivity parent){
		_parent = parent;
	}
	
    public Button getButton(Activity screen, int id){
    	try{
    		return (Button) screen.findViewById(id);
    	}catch(Exception e){
    		makeToast("Error getting button: "+e.getLocalizedMessage());
    		return null;
    	}
    }
    
    public TextView getTextView(Activity screen, int id){
    	try{
    		return (TextView) screen.findViewById(id);
    	}catch(Exception e){
    		makeToast("Error getting textview: "+e.getLocalizedMessage());
    		return null;
    	}
    }
    
    public void makeToast(String message){
    	Context context = _parent.getApplicationContext();
    	CharSequence text = ""+message;
    	int duration = Toast.LENGTH_SHORT;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
    
    public void changeScreen(Screens screen){
    	Intent i = new Intent();
    	switch (screen) {
        case HOME:
        	i.setClassName("com.raymond.dashcam", "com.raymond.dashcam.DashCamActivity");
        	_parent.startActivity(i);
        	break;
        case GPS:
        	i.setClassName("com.raymond.dashcam", "com.raymond.dashcam.GpsScreen");
            _parent.startActivity(i);
            break;
        case CAMERA:
        	i.setClassName("com.raymond.dashcam", "com.raymond.dashcam.MergedScreen");
            _parent.startActivity(i);
        	break;
        case GPSSETTING:
        	i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        	_parent.startActivity(i);
        	break;
    	}
    }
    
    OnClickListener listenerHome = new OnClickListener() {
        public void onClick(View v) {
        	_instance.changeScreen(Screens.HOME);
        }
    };
    
    public void foo(){
    	PackageManager pm = _parent.getPackageManager();
    	//pm.
    }
    
    public File fileLocation(){
    	// To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/testRay/", "MyCameraApp");
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        return mediaStorageDir;
    }
}
