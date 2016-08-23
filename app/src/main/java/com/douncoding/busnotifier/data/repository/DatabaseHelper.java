package com.douncoding.busnotifier.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.douncoding.busnotifier.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/**
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static Context context;
    private static DatabaseHelper instance = null;
    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            DatabaseHelper.context = context;
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context,
                DatabaseContract.DATABASE_NAME,
                null,
                DatabaseContract.DATABASE_VERSION);
    }

    private void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Route.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Version.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Station.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.RouteStation.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Bookmark.TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.Version.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(DatabaseContract.Route.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(DatabaseContract.Station.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(DatabaseContract.RouteStation.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(DatabaseContract.Bookmark.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropTable(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    /**
     * 앱 최초 설치시 백업된 데이터베이스를 사용하도록 유도
     */
    public static boolean copyToInitDatabase(Context context) {
//        InputStream inputStream = context.getResources().openRawResource(R.raw.backup);
//        String dst = context.getDatabasePath(DatabaseContract.DATABASE_NAME).getAbsolutePath();
//        File dstFile = new File(dst);
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream(dstFile);
//
//            byte buf[] = new byte[1024];
//            int len;
//            while ((len = inputStream.read(buf)) > 0) {
//                outputStream.write(buf, 0, len);
//            }
//
//            outputStream.close();
//            inputStream.close();
//            Log.e("CHECK", "초기화 데이터베이스 복사완료: ");
//            return true;
//        } catch (Exception e) {
//            Log.e("CHECK", "초기화 데이터베이스 복사실패: " + e.toString());
//            return false;
//        }
        return false;
    }

    public static void exportDatabase() {
        try {
            File sd = new File("/sdcard/Download");
            if (sd.canWrite()) {
                String currentDBPath =  DatabaseHelper.context.getDatabasePath(
                        DatabaseContract.DATABASE_NAME).getAbsolutePath();
                String backupDBPath = "backupname.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    Log.d(TAG, "파일크기:" +  src.size());
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Log.i(TAG, "데이터베이스 백업완료:" + backupDB.getPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 테이블이 이미 존재하는지 확인
     * @param tableName 대상 테이블
     * @return 존재여부
     */
    public boolean isThereTable(String tableName) {
        boolean isExist = false;
        SQLiteDatabase db = getReadableDatabase();

        String SELECT_SQL = String.format("SELECT * FROM %s WHERE %s = \'%s\'",
                DatabaseContract.Version.TABLE_NAME, DatabaseContract.Version.NAME, tableName);
        Cursor cursor = db.rawQuery(SELECT_SQL, null);

        try {
            if (cursor.moveToFirst()) {
                Log.i(TAG, "ROUTE TABLE 버전:" + cursor.getString(
                        cursor.getColumnIndex(DatabaseContract.Version.VERSION)));
                isExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return isExist;
    }

    public void insertTableVersion(String tableName, String tableVersion) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Version.NAME, tableName);
            values.put(DatabaseContract.Version.VERSION, tableVersion);

            db.insertOrThrow(DatabaseContract.Version.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
