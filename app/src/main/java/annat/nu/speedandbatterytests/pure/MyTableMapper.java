package annat.nu.speedandbatterytests.pure;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteStatement;

import java.util.Calendar;
import java.util.UUID;

public class MyTableMapper {

   // private int uuidIdx;
    private int uuidMIdx;
    private int uuidLIdx;
    private int versionIdx;
    private int timeStampIdx;
    private int dataIdx;
    public String tablename = "sqlite1";


    public String getInsertStatement(){
        return "Insert into sqlite1 (uuidm, uuidl, version, timestamp, data) values (?,?,?,?,?)";
    }

    public String getSelectStatement() {
        return "select uuidm, uuidl, version, timestamp, data from sqlite1";
    }


    public void init(Cursor cursor){
        //uuidIdx = cursor.getColumnIndex("uuid");
        uuidMIdx = cursor.getColumnIndex("uuidm");
        uuidLIdx = cursor.getColumnIndex("uuidl");
        versionIdx = cursor.getColumnIndex("version");
        timeStampIdx = cursor.getColumnIndex("timestamp");
        dataIdx = cursor.getColumnIndex("data");
    }

    public MyObject create(Cursor cursor){
        MyObject myObject = new MyObject();
        myObject.uuid = new UUID(cursor.getLong(uuidMIdx), cursor.getLong(uuidLIdx));
        //myObject.uuid =cursor.getString(uuidIdx);
        myObject.version = cursor.getInt(versionIdx);
        myObject.timeStamp = Calendar.getInstance();
        myObject.timeStamp.setTimeInMillis(cursor.getLong(timeStampIdx));
        myObject.data = cursor.getString(dataIdx);
        return myObject;
    }

    public ContentValues getvalues(MyObject object, ContentValues values) {
        if(values==null)
            values = new ContentValues(5);

        //values.put("uuid", object.uuid);
        values.put("uuidm", object.uuid.getMostSignificantBits());
        values.put("uuidl", object.uuid.getLeastSignificantBits());
        values.put("version", object.version);
        values.put("timestamp", object.timeStamp.getTimeInMillis());
        values.put("data", object.data);
        return values;
    }

    public void prepareInsert(final DatabaseUtils.InsertHelper helper) {
        //uuidIdx = helper.getColumnIndex("uuid");
        uuidMIdx = helper.getColumnIndex("uuidm");
        uuidLIdx = helper.getColumnIndex("uuidl");
        versionIdx = helper.getColumnIndex("version");
        timeStampIdx = helper.getColumnIndex("timestamp");
        dataIdx = helper.getColumnIndex("data");
    }

    public void bindInsert(MyObject myObject, final SQLiteStatement helper){
        //helper.bindString(1, myObject.uuid);
        helper.bindLong(1, myObject.uuid.getMostSignificantBits());
        helper.bindLong(2, myObject.uuid.getLeastSignificantBits());
        helper.bindLong(3, myObject.version);
        helper.bindLong(4, myObject.timeStamp.getTimeInMillis());
        helper.bindString(5, myObject.data);
    }

}
