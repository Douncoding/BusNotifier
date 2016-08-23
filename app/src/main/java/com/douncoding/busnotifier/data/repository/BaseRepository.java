package com.douncoding.busnotifier.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.RawRes;
import android.util.Log;

import com.douncoding.busnotifier.view.BookmarkListView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by douncoding on 16. 7. 31..
 */
public abstract class BaseRepository<T> {
    private static final String TAG = BaseRepository.class.getSimpleName();

    protected final Context context;
    protected final DatabaseHelper mDatabaseHelper;
    protected final String TABLE_NAME;

    protected final String[] columns;

    public BaseRepository(Context context, String tableName, String[] columns) {
        this.context = context;
        this.mDatabaseHelper = DatabaseHelper.getInstance(context);
        this.TABLE_NAME = tableName;
        this.columns = columns;
    }

    public interface OnListener {
        void onCreate();
    }

    public void createLocalDataStore(@RawRes final int target, final OnListener onListener) {
        if (mDatabaseHelper.isThereTable(TABLE_NAME)) {
            Log.i(TAG, TABLE_NAME + "는 이미 존재하는 테이블");
            if (onListener != null)
                onListener.onCreate();
        } else {
            Log.i(TAG, TABLE_NAME + " 로컬 파일기반 데이터베이스 생성 시작");
            InputStream in = this.context.getResources().openRawResource(target);
            new AsyncTask<InputStream, Void, Void>() {
                @Override
                protected Void doInBackground(InputStream... params) {
                    new RawDataReader(params[0]) {
                        @Override
                        void onPostLineParse(List<String> values) {
                            insert(values);
                        }

                        @Override
                        void onFinished() {
                            Log.i(TAG, TABLE_NAME + "로컬 파일기반 데이터베이스 생성 완료");
                        }
                    };
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    mDatabaseHelper.insertTableVersion(TABLE_NAME, null);

                    if (onListener != null)
                        onListener.onCreate();
                }
            }.execute(in);
        }
    }

    public void insert(List<String> values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < values.size(); i++) {
                contentValues.put(columns[i], values.get(i));
            }
            db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
