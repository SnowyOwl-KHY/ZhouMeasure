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
import classexample.zhoumeasure.reference.AddReferenceFragment;
import classexample.zhoumeasure.reference.ReferenceFragment;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private ReferenceFragment referenceFragment = new ReferenceFragment();
    private CameraFragment cameraFragment = new CameraFragment();
    private PhotoFragment photoFragment = new PhotoFragment();
    private AddReferenceFragment addReferenceFragment = new AddReferenceFragment();
    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    // Create and set View
//                    setContentView(R.layout.main);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ReferenceFragment getReferenceFragment() {
        return referenceFragment;
    }

    public CameraFragment getCameraFragment() {
        return cameraFragment;
    }

    public PhotoFragment getPhotoFragment() {
        return photoFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_2, this, mOpenCVCallBack);

        getFragmentManager().beginTransaction()
                .add(R.id.mainActivity, photoFragment)
                .hide(photoFragment)
                .add(R.id.mainActivity, cameraFragment)
                .add(R.id.mainActivity, referenceFragment)
                .add(R.id.mainActivity, addReferenceFragment)
                .hide(addReferenceFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        addReferenceFragment.setPosition(0, -5000);
//        referenceFragment.mBtnPositionAtPhoto = photoFragment.mBlankCameraBtn;
//        referenceFragment.mIvBackImage.setX(photoFragment.mBlankReturnBtn.getX());
//        referenceFragment.mIvBackImage.setY(photoFragment.mBlankReturnBtn.getY());
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
        getFragmentManager().beginTransaction()
                .hide(cameraFragment)
                .show(photoFragment)
                .commit();
    }

    public void jumpToCameraFragment() {
        getFragmentManager().beginTransaction()
                .hide(photoFragment)
                .show(cameraFragment)
                .commit();
    }

    public void jumpToAddFragment() {
        getFragmentManager().beginTransaction()
                .show(addReferenceFragment)
                .commit();
        addReferenceFragment.reset();
        addReferenceFragment.slideIn();
    }

    public void jumpOutFromAddFragment() {
        addReferenceFragment.slideOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //initialize OpenCV manager
    //    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
    }

}
