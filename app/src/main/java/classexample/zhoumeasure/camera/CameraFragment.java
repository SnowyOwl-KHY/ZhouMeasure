package classexample.zhoumeasure.camera;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import classexample.zhoumeasure.MainActivity;
import classexample.zhoumeasure.R;
import classexample.zhoumeasure.photo.PhotoFragment;

/**
 * Created by Xiaozhou on 2015/8/7.
 */
public class CameraFragment extends Fragment {

    private static final String TAG = "CameraFragment";
    private Camera camera;
    private boolean preview = false;
    static public Bitmap sendBM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Enter it");
        final View rootView = inflater.inflate(R.layout.fragment_camera, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SurfaceView surfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(200, 200);
        surfaceView.getHolder().addCallback(new SurfaceViewCallback());

        return rootView;
    }

    public void takePhoto() {
        Log.e(TAG, "take photo");
        camera.takePicture(null, null, new TakePictureCallback());
    }

    private final class SurfaceViewCallback implements SurfaceHolder.Callback {
        /**
         * surfaceView 被创建成功后调用此方法
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            /*
			 * 在SurfaceView创建好之后 打开摄像头
			 * 注意是 android.hardware.Camera
			 */
            camera = Camera.open();
			/*
			 * This method must be called before startPreview().otherwise surfaceview没有图像
			 */
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Camera.Parameters parameters = camera.getParameters();
			/* 设置预览照片的大小，此处设置为全屏 */
            parameters.setPreviewSize(200, 200);
			/* 每秒从摄像头捕获5帧画面， */
            parameters.setPreviewFrameRate(25);
			/* 设置照片的输出格式:jpg */
            parameters.setPictureFormat(PixelFormat.JPEG);
			/* 照片质量 */
            parameters.set("jpeg-quality", 85);
            WindowManager wm = (WindowManager)getActivity().findViewById(R.id.RelLayout).getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            int width = display.getWidth();  // deprecated
            int height = display.getHeight();
            parameters.setPictureSize(1024, 768);
			/* 设置照片的大小：此处照片大小等于屏幕大小 */
            //Display display = getActivity().getWindowManager().getDefaultDisplay();
            Log.d("ly", "ddf"+display.getWidth()+" "+display.getHeight());
            parameters.setPictureSize(display.getWidth(), display.getHeight());
            parameters.setPreviewSize(width, height);
            camera.setParameters(parameters);
            camera.startPreview();
            /**
             * Installs a callback to be invoked for every preview frame in addition to displaying them on the screen.
             * The callback will be repeatedly called for as long as preview is active. This method can be called at
             * any time, even while preview is live. Any other preview callbacks are overridden.
             * a callback object that receives a copy of each preview frame, or null to stop receiving
             */
            preview = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.d(TAG, "surfaceChanged");
        }

        /**
         * SurfaceView 被销毁时释放掉 摄像头
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
				/* 若摄像头正在工作，先停止它 */
                if (preview) {
                    camera.stopPreview();
                    //camera.release();  //fashengshashi
                    preview = false;
                    //camera = null;
                }
                //如果注册了此回调，在release之前调用，否则release之后还回调，crash
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
            }
        }
    }

    /**
     * 处理照片被拍摄之后的事件
     */
    private final class TakePictureCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            sendBM = BitmapFactory.decodeByteArray(data, 0, data.length);
//                    File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//            Log.d(TAG, "@!!@#!!" + System.currentTimeMillis() + ".jpg");
//            FileOutputStream fos;
//            try {
//                fos = new FileOutputStream(file);
//                fos.write(data);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            // 在拍照的时候相机是被占用的,拍照之后需要重新预览
            PhotoFragment.instance.setBitmap(sendBM);
            camera.startPreview();
        }
    }
}