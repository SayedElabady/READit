package capstone.udacity.com.readit.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import capstone.udacity.com.readit.Models.Book;
import capstone.udacity.com.readit.R;

/**
 * Created by Sayed on 10/19/2017.
 */
public class GridWidgetService extends RemoteViewsService {
    ArrayList<Book> books;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Context context;

        public GridRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            books = BooksWidgetProvider.bookList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return books != null && books.size() != 0 ? books.size() : 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view_item);
            views.setTextViewText(R.id.widget_grid_view_item, "Book Name : " + books.get(i).getBookName());
            Intent fillInIntent = new Intent();
            fillInIntent.putParcelableArrayListExtra("bookList", books);
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
