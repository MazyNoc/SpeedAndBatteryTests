package annat.nu.speedandbatterytests.pure;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;
import java.util.UUID;

import annat.nu.speedandbatterytests.dbflow.DbFlowDatabase;

public class MyObject{

    public UUID uuid;
    public int version;

    public Calendar timeStamp;

    public String data;
}
