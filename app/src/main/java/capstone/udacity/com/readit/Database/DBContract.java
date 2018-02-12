package capstone.udacity.com.readit.Database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sayed El-Abady on 2/11/2018.
 */

public class DBContract {
    public static final String CONTENT_AUTHORITY = "capstone.udacity.com.readit";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String BOOK_PATH = "books";

    public static class BookEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(BOOK_PATH).build();

        public static final String TABLE_NAME = "books";
        public static final String COLUMN_BOOK_NAME = "book_name";
        public static final String COLUMN_BOOK_DESCRIPTION = "book_description";
        public static final String COLUMN_BOOK_CATEGORY = "book_category";
        public static final String COLUMN_IMAGE_URL = "book_image_url";
        public static final String COLUMN_OWNER_ID = "book_owner_id";
        public static final String COLUMN_BOOK_ID = "book_id";



    }
}
