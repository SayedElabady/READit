package capstone.udacity.com.readit.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Sayed El-Abady on 2/12/2018.
 */

public class BookProvider extends ContentProvider{
    private SQLiteOpenHelper sqLiteOpenHelper;

    public static final int FAVOURITE = 100;

    private static UriMatcher uriMatcher = createURIMatcher();

    private static UriMatcher createURIMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.BOOK_PATH, FAVOURITE);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        sqLiteOpenHelper = new BooksDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case FAVOURITE:
                retCursor = sqLiteDatabase.query(DBContract.BookEntry.TABLE_NAME,
                        strings
                        , s
                        , strings1
                        , null
                        , null
                        , s1);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri returnUri;
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match) {
            case FAVOURITE:
                long id = sqLiteDatabase.insert(DBContract.BookEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(DBContract.BookEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        int rowsDeleted;
        int match = uriMatcher.match(uri);
        switch (match) {
            case FAVOURITE:
                rowsDeleted = sqLiteDatabase.delete(DBContract.BookEntry.TABLE_NAME, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
