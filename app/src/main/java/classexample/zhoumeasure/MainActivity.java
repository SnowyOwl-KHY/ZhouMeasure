package classexample.zhoumeasure;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import classexample.zhoumeasure.camera.CameraFragment;
import classexample.zhoumeasure.photo.PhotoFragment;
import classexample.zhoumeasure.reference.ReferenceFragment;


public class MainActivity extends Activity {

    private ReferenceFragment referenceFragment = new ReferenceFragment();
    private CameraFragment cameraFragment = new CameraFragment();
    private PhotoFragment photoFragment = new PhotoFragment();

    public ReferenceFragment getReferenceFragment() {
        return referenceFragment;
    }

    public CameraFragment getCameraFragment() {
        return cameraFragment;
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
        getFragmentManager().beginTransaction().detach(cameraFragment).attach(photoFragment).commit();
    }

    public void jumpToCameraFragment() {
        getFragmentManager().beginTransaction().detach(photoFragment).attach(cameraFragment).commit();
    }

}
