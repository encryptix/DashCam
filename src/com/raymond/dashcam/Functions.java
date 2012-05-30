package com.raymond.dashcam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Functions {
	public enum Screens {
	    HOME,GPS,CAMERA 
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
        	int c = 4;
    	}
    }
}
