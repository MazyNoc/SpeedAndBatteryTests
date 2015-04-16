package annat.nu.speedandbatterytests.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;
import java.util.UUID;

@Table(databaseName = DbFlowDatabase.NAME)
public class DbFlowTable1 extends BaseModel{

    @Column(columnType = Column.PRIMARY_KEY)
    public String uuid;

    public int version;

    @Column
    public Calendar timeStamp;

    @Column
    public String data;
}
