package annat.nu.speedandbatterytests.pure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper{


    private static SQLiteDatabase db;

    public static MyDatabase instance;
    private SQLiteStatement mSqLiteStatement;
    private SQLiteStatement mSqLiteSelectStatement;

    public MyDatabase(Context context ) {
        super(context, "mydatabase.db", null, 1);
        db = this.getWritableDatabase();
        instance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table sqlite1 (uuidm BIGINT, uuidl BIGINT, version INTEGER, timestamp BIGINT, data TEXT, primary key(uuidm, uuidl))");
        db.execSQL("Create table sqlite2 (uuid TEXT,version INTEGER, timestamp BIGINT, data TEXT, primary key(uuid))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(Collection<MyObject> objects){
        db.beginTransaction();

        MyTableMapper tbm = new MyTableMapper();
        if(mSqLiteStatement == null)
        mSqLiteStatement = db.compileStatement(tbm.getInsertStatement());

        for (MyObject object : objects) {
            tbm.bindInsert(object, mSqLiteStatement);
            mSqLiteStatement.executeInsert();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List<MyObject> queryData() {
        MyTableMapper tbm = new MyTableMapper();
        List<MyObject> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(tbm.getSelectStatement(), null);
        tbm.init(cursor);
        while (cursor.moveToNext()){
            result.add(tbm.create(cursor));
        }
        return result;
    }
}
