package com.douncoding.busnotifier.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.douncoding.busnotifier.data.Route;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * 즐겨찾기 및 최근검색 기록 관리
 */
public class BookmarkRepository extends BaseRepository<Route> {
    private static BookmarkRepository instance = null;
    public static final int TYPE_BOOKMARK = DatabaseContract.Bookmark.Type.BOOMARK.ordinal();
    public static final int TYPE_RECENT = DatabaseContract.Bookmark.Type.RECENT.ordinal();

    public BookmarkRepository(Context context) {
        super(context,
                DatabaseContract.Bookmark.TABLE_NAME,
                DatabaseContract.Bookmark.getColumnNames());
    }

    public static BookmarkRepository getInstance(Context context) {
        if (instance == null) {
            instance = new BookmarkRepository(context);
        }
        return instance;
    }


    public boolean isContain(int routeId) {
        String SELECT_SQL = String.format(Locale.KOREA, "SELECT count(*) FROM %s WHERE %s = %d and %s = %d",
                TABLE_NAME, DatabaseContract.Bookmark.BOOKMARK_ID, routeId,
                DatabaseContract.Bookmark.FLAG, TYPE_BOOKMARK);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(SELECT_SQL, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count!=0;
    }

    public List<Route> getList(int type) {
        String SELECT_SQL = String.format(Locale.KOREA, "SELECT * FROM %s WHERE %s = %d",
                TABLE_NAME, DatabaseContract.Bookmark.FLAG, type);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_SQL, null);

        Gson gson = new Gson();
        List<Route> results = new LinkedList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    String json = cursor.getString(cursor.getColumnIndex(DatabaseContract.Bookmark.JSON_ROUTE));
                    results.add(gson.fromJson(json, Route.class));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return results;
    }

    public void add(Route route, int type) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Bookmark.BOOKMARK_ID, route.getIdRoute());
        contentValues.put(DatabaseContract.Bookmark.JSON_ROUTE, route.toSerialize());
        contentValues.put(DatabaseContract.Bookmark.FLAG, type);

        try {
            if (type == TYPE_BOOKMARK) {
                db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            } else {
                db.insertOrThrow(TABLE_NAME, null, contentValues);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void del(Route route) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(TABLE_NAME,
                    DatabaseContract.Bookmark.BOOKMARK_ID + "= ?",
                    new String[]{String.valueOf(route.getIdRoute())});
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
