package capstone.udacity.com.readit.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

import capstone.udacity.com.readit.Models.Book;

/**
 * Created by Sayed El-Abady on 2/12/2018.
 */

public class BooksDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "books.db";
    Context context;

    public BooksDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String createMoviesTable = "CREATE TABLE " + DBContract.BookEntry.TABLE_NAME + " ( "
                + DBContract.BookEntry._ID + " INTEGER PRIMARY KEY, "
                + DBContract.BookEntry.COLUMN_BOOK_ID + " TEXT, "
                + DBContract.BookEntry.COLUMN_BOOK_NAME + " TEXT, "
                + DBContract.BookEntry.COLUMN_BOOK_DESCRIPTION + " TEXT, "
                + DBContract.BookEntry.COLUMN_BOOK_CATEGORY + " TEXT, "
                + DBContract.BookEntry.COLUMN_IMAGE_URL + " TEXT, "
                + DBContract.BookEntry.COLUMN_OWNER_ID + " TEXT" +
                ");";

        sqLiteDatabase.execSQL(createMoviesTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.BookEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public ArrayList<Book> getFavouriteBooks() {
        ArrayList<Book> favourites = new ArrayList<>();

        Cursor cursor = context.getContentResolver()
                .query(
                        DBContract.BookEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );

        if (cursor.moveToFirst()) {
            do {
                String bookName = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_BOOK_NAME));
                String description = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_BOOK_DESCRIPTION));
                String category = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_BOOK_CATEGORY));
                String imageUrl = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_IMAGE_URL));
                String ownerID = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_OWNER_ID));
                String id = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_BOOK_ID));
                favourites.add(new Book(id, bookName, description, category, imageUrl, ownerID));
            } while (cursor.moveToNext());
        }
        //
        cursor.close();
        return favourites;
    }

    public boolean isExistInDB(String bookId) {
        Cursor cursor = context.getContentResolver()
                .query(
                        DBContract.BookEntry.CONTENT_URI,
                        null,
                        DBContract.BookEntry.COLUMN_BOOK_ID + " = ?",
                        new String[]{bookId},
                        null
                );
        boolean isExist = cursor.moveToFirst();
        cursor.close();
        return isExist;
    }

    public void addToFavourite(Book book) {
        if (!isExistInDB(book.getId())) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBContract.BookEntry.COLUMN_BOOK_ID, book.getId());
            contentValues.put(DBContract.BookEntry.COLUMN_BOOK_NAME, book.getBookName());
            contentValues.put(DBContract.BookEntry.COLUMN_BOOK_DESCRIPTION, book.getDescription());
            contentValues.put(DBContract.BookEntry.COLUMN_BOOK_CATEGORY, book.getCategory());
            contentValues.put(DBContract.BookEntry.COLUMN_IMAGE_URL, book.getImageUri());
            contentValues.put(DBContract.BookEntry.COLUMN_OWNER_ID, book.getOwnerID());

            Uri uri = context.getContentResolver().insert(DBContract.BookEntry.CONTENT_URI, contentValues);
        }
    }
    public void deleteFavourite(Context context , String movieId){
        context.getContentResolver().delete(
                DBContract.BookEntry.CONTENT_URI,
                DBContract.BookEntry.COLUMN_BOOK_ID + " = ?",
                new String[]{movieId}
        );
    }
}
