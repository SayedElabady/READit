package capstone.udacity.com.readit.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import capstone.udacity.com.readit.Models.Book;


/**
 * Created by Sayed on 10/19/2017.
 */

public class BooksService extends IntentService {
    public static final String ACTIVITY_MAIN = "ACTIVITY_MAIN";
    public BooksService() {
        super("UpdateBakingService");
    }
    public static void startUpdateService(Context context , ArrayList<Book> books){
        Intent intent = new Intent(context, BooksService.class);
        intent.putExtra(ACTIVITY_MAIN,books);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            handleUpdateBakingService((ArrayList<Book>) intent.getExtras().get(ACTIVITY_MAIN));
        }

    }
    private void handleUpdateBakingService(ArrayList<Book> books){
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(ACTIVITY_MAIN,books);
        sendBroadcast(intent);
    }
}
