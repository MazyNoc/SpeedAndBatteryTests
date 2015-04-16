package annat.nu.speedandbatterytests;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.TransactionManager;

import java.util.Collection;

import annat.nu.speedandbatterytests.dbflow.DbFlowTable1;
import annat.nu.speedandbatterytests.pure.MyDatabase;
import annat.nu.speedandbatterytests.pure.MyObject;


public class MainActivity extends Activity {

    private MyDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FlowManager.init(this);
        mydb = new MyDatabase(this);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            view.findViewById(R.id.createData).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nativeCreate(10000);
                    dbflowCreate(10000);
                }
            });
        }

        private void nativeCreate(int records) {
            Collection<MyObject> myObjects = CreateMany.createMyObject(records);
            long start = System.currentTimeMillis();
            MyDatabase.instance.insertData(myObjects);
            long end = System.currentTimeMillis();
            Log.d("SpeedTest", String.format("native insert %d record, %d ms", records, end - start));
        }

        private void dbflowCreate(int records) {
            Collection<DbFlowTable1> dbFlowTable1 = CreateMany.createDbFlowTable1(records);
            long start = System.currentTimeMillis();
            
            for (DbFlowTable1 flowTable1 : dbFlowTable1) {
                flowTable1.insert(false);
            }
            long end = System.currentTimeMillis();
            Log.d("SpeedTest", String.format("DbFlow insert %d record, %d ms", records, end - start));
        }
    }
}
