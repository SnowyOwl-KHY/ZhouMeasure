package classexample.zhoumeasure;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import classexample.zhoumeasure.photo.PhotoFragment;
import classexample.zhoumeasure.reference.AddReferenceFragment;


public class MainActivity extends Activity {
    PhotoFragment mPhotoFragment;
    AddReferenceFragment mAddReferenceFragment;
    FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTransaction = getFragmentManager().beginTransaction();

        //new objects
        mPhotoFragment = new PhotoFragment();
        mAddReferenceFragment = new AddReferenceFragment();



        mTransaction.add(R.id.mainContainer, mPhotoFragment).commit();

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
}
