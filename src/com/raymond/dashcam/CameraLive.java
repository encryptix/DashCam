package com.raymond.dashcam;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

public class CameraLive implements SurfaceHolder.Callback, OnInfoListener 
{   
    public static final int STATE_NONE      = -1;
    public static final int STATE_STOPPED   = 0;
    public static final int STATE_PREVIEW   = 1;
    public static final int STATE_CAPTURE   = 2;
    public static final int STATE_RECORDING = 3;

    private SurfaceHolder   mHolder;
    private MediaRecorder   mRecorder;
    private CameraOverlay     mCameraView;
    private Context         mContext;
    private FrameLayout     mParent;
    private int             mState;
    private boolean         mRecording;


    public CameraLive(Context context, FrameLayout parent)
    {
        //Initiate the Surface Holder properly

        mParent     = parent;
        mContext    = context; 
        mRecorder   = null;
        mState      = STATE_NONE;
        mRecording  = false;
    }


    private void Init()
    {
    	InitWithoutNewFile();
        mRecorder.setOutputFile(getOutputMediaFile().toString());
    }
    
    private void InitWithoutNewFile(){
        mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        mRecorder.setProfile(camcorderProfile_HQ);
    }

    
    /* Create a File for saving an image or video*/ 
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/testRay/", "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");

        return mediaFile;
    }
    
    private void Prepare()
    {
        try
        {
        	/*int noCameras = Camera.getNumberOfCameras();
        	for(int i=0;i<noCameras;i++){
        		Camera c = Camera.open(i);
        		
        	}*/
        	
        	//Camera c = Camera.open(0);
        	//mRecorder.setCamera(c);
        	//ssssssCamera.open(1);
        	//mRecorder.setCamera(Camera.ope));
            mRecorder.setPreviewDisplay(mHolder.getSurface());
            mRecorder.prepare();
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void UI_StartPreview()
    {
        if(mState == STATE_STOPPED || mState == STATE_NONE)
        {
            mRecorder= new MediaRecorder();
            Init();

            mCameraView= new CameraOverlay(mContext);
            mParent.addView(mCameraView);

            this.mHolder = mCameraView.getHolder();
            this.mHolder.addCallback(this);
            this.mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            
            
        }
    }


    public void UI_StopPreview()
    {

        //if(mState == STATE_PREVIEW)
        //{
            /*if(mRecording)
            {
                UI_StopRecord();
            }*/

            if(mRecorder != null)
            {
                mRecorder.release();
                mRecorder= null;
            }

            mParent.removeView(mCameraView);
            //mCameraView= null;
        //}
        
    	if(mRecording)
        {
    		UI_StopRecord();
        }mState= STATE_STOPPED;
    	mState= STATE_STOPPED;
    	
    }

    public boolean UI_StartRecord()
    {
        if(mState != STATE_PREVIEW )
        {
            return false;
        }

        //String path= "/sdcard/PLUS_VIDEO.mp4";

        String state = android.os.Environment.getExternalStorageState();

        if(!state.equals(Environment.MEDIA_MOUNTED))
        {
                return false;
        }
        //File directory = new File(path).getParentFile();
        //if(!directory.exists() && !directory.mkdirs())
        //{
         //       return false;
        //}

        mRecorder.start();
        mRecording= true;

        mState= STATE_RECORDING;

        return true;
    }


    public void UI_StopRecord()
    {
        if(mRecorder != null)
        {
            mRecorder.stop();
            mRecorder.reset();
            
            //InitWithoutNewFile();
            //Prepare();

            mRecording= false;

            mState= STATE_STOPPED;
        }
    }

    public boolean UI_IsRecording()
    {
        return mRecording;
    }



    @Override 
    public void onInfo(MediaRecorder mr, int what, int extra)
    { 
        // TODO Auto-generated method stub 
         Log.i(null, "onInfo"); 
    } 


    @Override
    public void surfaceCreated(SurfaceHolder holder) 
    {   
        Prepare();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
    {
        mState= STATE_PREVIEW;
        this.UI_StartRecord();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) 
    {
        mState= STATE_STOPPED;
        this.UI_StopRecord();
    }
}