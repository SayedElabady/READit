package capstone.udacity.com.readit.Listeners;

import java.util.List;

import capstone.udacity.com.readit.Models.Book;

/**
 * Created by Sayed El-Abady on 2/10/2018.
 */

public interface OnFetchingCompleted {

    void onDataFetched(List<Book> books);

    void onError(String errorMessage);
}
