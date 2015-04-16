package annat.nu.speedandbatterytests.pure;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper{


    private static SQLiteDatabase db;

    public static MyDatabase instance;

    public MyDatabase(Context context ) {
        super(context, "mydatabase.db", null, 1);
        db = this.getWritableDatabase();
        instance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table sqlite1 (uuidm BIGINT, uuidl BIGINT, version INTEGER, timestamp BIGINT, data TEXT, primary key(uuidm, uuidl))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(Collection<MyObject> objects){
        db.beginTransaction();

        MyTableMapper tbm = new MyTableMapper();
        ContentValues contentValues = null;
        for (MyObject object : objects) {
            contentValues = tbm.getvalues(object, contentValues);
            db.insert(tbm.tablename, null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
