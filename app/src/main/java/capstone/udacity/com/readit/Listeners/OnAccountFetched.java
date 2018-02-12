package capstone.udacity.com.readit.Listeners;

import capstone.udacity.com.readit.Models.Account;
import capstone.udacity.com.readit.Models.Book;

/**
 * Created by Sayed El-Abady on 2/10/2018.
 */

public interface OnAccountFetched {
    void onSuccess(Account account);

    void onFailed(String errorMessage);

}
