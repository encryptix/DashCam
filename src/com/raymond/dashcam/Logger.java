package com.raymond.dashcam;

import java.io.File;
import java.io.FileOutputStream;

import com.raymond.dashcam.datastructure.DataPoint;

import android.util.Log;

public class Logger {
	private File _file;
	
	public Logger(String filepathToVideo){
		String dateTime = filepathToVideo.substring(filepathToVideo.indexOf('_')+1,filepathToVideo.length()-4);
		
		File mediaStorageDir = Functions.getInstance().fileLocation();
		_file = new File(mediaStorageDir.getPath() + File.separator +"_"+ dateTime + ".xml");
	}
	
	public void addPoint(DataPoint point){
		String data="<point>";
		
		data+="<latitude>";
		data+=point.get_latitude();
		data+="</latitude>";
		
		data+="<longitude>";
		data+=point.get_longitude();
		data+="</longitude>";
		
		data+="<speed>";
		data+=point.get_speed();
		data+="</speed>";
		
		data+="<time>";
		data+=point.get_time();
		data+="</time>";
		
		//point.get_orientation();
		data+="</point>";
		appendString(data);
	}
	
	private void appendString(String string){
		try{
            FileOutputStream fos = new FileOutputStream(_file,true);
			fos.write(string.getBytes());
			fos.close();
		}catch(Exception e){
			Log.e("Logger", e.getLocalizedMessage());
		}
	}
}
