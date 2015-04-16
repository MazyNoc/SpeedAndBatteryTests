package annat.nu.speedandbatterytests.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = DbFlowDatabase.NAME, version = DbFlowDatabase.VERSION)
public class DbFlowDatabase {
    public static final String NAME = "DbFlowTest";
    public static final int VERSION = 1;
}
