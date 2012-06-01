package com.raymond.dashcam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class CameraOverlay extends SurfaceView{
	 public CameraOverlay(Context context) {
	        super(context);
	        setWillNotDraw(false);
	 }
	 
	 @Override
	 protected void onDraw(Canvas canvas){
		 Paint p = new Paint(Color.BLUE);
		 p.setTextSize(25f);
		 canvas.drawText("Test", 3.0f, 100.0f, p);
	 }

}
