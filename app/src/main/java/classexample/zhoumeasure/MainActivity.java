package classexample.zhoumeasure;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import classexample.zhoumeasure.camera.CameraFragment;
import classexample.zhoumeasure.photo.PhotoFragment;
import classexample.zhoumeasure.reference.ReferenceFragment;


public class MainActivity extends Activity {
    static String TAG = "MainActivity";
    private ReferenceFragment referenceFragment = new ReferenceFragment();
    private CameraFragment cameraFragment = new CameraFragment();
    private PhotoFragment photoFragment = new PhotoFragment();

    public CameraFragment getCameraFragment() {
        return cameraFragment;
    }

    public PhotoFragment getPhotoFragment() {
        return photoFragment;
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV Manager Connected");
                    //from now onwards, you can use OpenCV API
                    Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
                    break;
                case LoaderCallbackInterface.INIT_FAILED:
                    Log.i(TAG, "Init Failed");
                    break;
                case LoaderCallbackInterface.INSTALL_CANCELED:
                    Log.i(TAG, "Install Cancelled");
                    break;
                case LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION:
                    Log.i(TAG, "Incompatible Version");
                    break;
                case LoaderCallbackInterface.MARKET_ERROR:
                    Log.i(TAG, "Market Error");
                    break;
                default:
                    Log.i(TAG, "OpenCV Manager Install");
                    super.onManagerConnected(status);
                    break;
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        //initialize OpenCV manager
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction()
                .add(R.id.mainActivity, photoFragment)
                .add(R.id.mainActivity, cameraFragment)
                .add(R.id.mainActivity, referenceFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void jumpToPhotoFragment() {
        getFragmentManager().beginTransaction().hide(cameraFragment).show(photoFragment).commit();
    }

    public void jumpToCameraFragment() {
        getFragmentManager().beginTransaction().hide(photoFragment).show(cameraFragment).commit();
    }

}
