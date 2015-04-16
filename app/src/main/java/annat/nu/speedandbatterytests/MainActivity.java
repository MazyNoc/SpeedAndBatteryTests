package annat.nu.speedandbatterytests;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.config.BaseDatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.sql.language.From;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Collection;
import java.util.List;

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
                    int records = 100_000;
                    nativeCreate(records);
                    dbflowCreate(records);
                }
            });

            view.findViewById(R.id.selectData).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nativeSelect();
                    dbflowSelect();
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

        private void nativeSelect() {
            final long start = System.currentTimeMillis();
            List<MyObject> dbFlowTable1s = MyDatabase.instance.queryData();
            long end = System.currentTimeMillis();
            Log.d("SpeedTest", String.format("native selected %d record, %d ms", dbFlowTable1s.size(), end - start));
        }

        private void dbflowCreate(final int records) {
            final Collection<DbFlowTable1> dbFlowTable1 = CreateMany.createDbFlowTable1(records);

            BaseDatabaseDefinition database = FlowManager.getDatabaseForTable(DbFlowTable1.class);
            final long start = System.currentTimeMillis();
            TransactionManager.transact(database.getWritableDatabase(), new Runnable() {
                @Override
                public void run() {
                    for (DbFlowTable1 flowTable1 : dbFlowTable1) {
                        flowTable1.insert(false);
                    }
                }
            });
            long end = System.currentTimeMillis();
            Log.d("SpeedTest", String.format("DbFlow insert %d record, %d ms", records, end - start));

        }

        private void dbflowSelect() {
            final long start = System.currentTimeMillis();
            List<DbFlowTable1> dbFlowTable1s = new Select().from(DbFlowTable1.class).queryList();
            long end = System.currentTimeMillis();
            Log.d("SpeedTest", String.format("DbFlow selected %d record, %d ms", dbFlowTable1s.size(), end - start));
        }

    }
}
