package comw.example.user.testtaska.DataBase;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

public class DataBaseProvider extends ContentProvider{

    private static final String DB_NAME = "linksDB";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE_NAME = "linksImageTab"; // CONTACT_TABLE

    // поля
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TIME_OF_USE = "timeOfUse";

    // Скрипт создания таблицы
    private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LINK + " TEXT, "
            + COLUMN_STATUS + " INTEGER, "
            + COLUMN_TIME_OF_USE + " LONG" + ");";

    static final String AUTHORITY = "comw.example.user.testbigdigappa.DataBase";

    static final String LINKS_PATH = "linksImageTab";

    public static final Uri LINK_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + LINKS_PATH);

    static final String LINKS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + LINKS_PATH;

    static final String LINKS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + LINKS_PATH;

    static final int URI_LINKS = 1;

    static final int URI_LINKS_ID = 2;


    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,LINKS_PATH,URI_LINKS);
        uriMatcher.addURI(AUTHORITY,LINKS_PATH + "/#", URI_LINKS_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // проверяем Uri
        switch (uriMatcher.match(uri)){

            case URI_LINKS:// общий Uri
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = COLUMN_STATUS + " ASC"; // DESC
                }
                break;

            case URI_LINKS_ID:
                String id = uri.getLastPathSegment();

                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = COLUMN_ID + " = " + id;
                } else {
                    selection = selection + " AND " + COLUMN_ID + " = " + id;
                }
                break;
        }

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DB_TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                LINK_CONTENT_URI);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_LINKS:
                return LINKS_CONTENT_TYPE;
            case URI_LINKS_ID:
                return LINKS_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) != URI_LINKS){
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(DB_TABLE_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(LINK_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);

        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case URI_LINKS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = COLUMN_ID + " = " + id;
                } else {
                    selection = selection + " AND " + COLUMN_ID  + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(DB_TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case URI_LINKS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = COLUMN_ID + " = " + id;
                } else {
                    selection = selection + " AND " + COLUMN_ID  + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(DB_TABLE_NAME,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
