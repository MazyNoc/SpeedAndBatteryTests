package annat.nu.speedandbatterytests.pure;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Calendar;
import java.util.UUID;

public class MyTableMapper {

    private int uuidMIdx;
    private int uuidLIdx;
    private int versionIdx;
    private int timeStampIdx;
    private int dataIdx;
    public String tablename = "sqlite1";

    public void init(Cursor cursor){
        uuidMIdx = cursor.getColumnIndex("uuidm");
        uuidLIdx = cursor.getColumnIndex("uuidl");
        versionIdx = cursor.getColumnIndex("version");
        timeStampIdx = cursor.getColumnIndex("timestamp");
        dataIdx = cursor.getColumnIndex("data");
    }

    public MyObject create(Cursor cursor){
        MyObject myObject = new MyObject();
        myObject.uuid = new UUID(cursor.getLong(uuidMIdx), cursor.getLong(uuidLIdx));
        myObject.version = cursor.getInt(versionIdx);
        myObject.timeStamp = Calendar.getInstance();
        myObject.timeStamp.setTimeInMillis(cursor.getLong(timeStampIdx));
        myObject.data = cursor.getString(dataIdx);
        return myObject;
    }

    public ContentValues getvalues(MyObject object, ContentValues values) {
        if(values==null)
            values = new ContentValues(5);

        values.put("uuidm", object.uuid.getMostSignificantBits());
        values.put("uuidl", object.uuid.getLeastSignificantBits());
        values.put("version", object.version);
        values.put("timestamp", object.timeStamp.getTimeInMillis());
        values.put("data", object.data);
        return values;
    }
}
