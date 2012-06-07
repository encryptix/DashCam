package com.raymond.dashcam;

import com.raymond.dashcam.datastructure.DataPoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class CameraOverlay extends SurfaceView{
	private String _displayMe;
	
	 public CameraOverlay(Context context,String displayOverviewText) {
	        super(context);
	        _displayMe=displayOverviewText;
	        
	 }
	 
	 @Override
	 protected void onDraw(Canvas canvas){
		 Paint p = new Paint(Color.BLUE);
		 p.setTextSize(25f);
		 if(canvas!=null){
			 canvas.drawText(_displayMe, 3.0f, 100.0f, p);
			 super.onDraw(canvas);
		 }
	 }
	 
	 public void setDisplayMessage(DataPoint point){
		 Functions.getInstance().makeToast("Update Display called");
		 _displayMe = point.get_speed()+"\n"+
		 point.get_time();
		 this.postInvalidate();
	 }
}
