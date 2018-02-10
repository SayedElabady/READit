package capstone.udacity.com.readit.Listeners;

/**
 * Created by Sayed El-Abady on 2/2/2018.
 */

public interface OnFinishListener {
    void onSuccess();

    void onFailed(String errorMessage);
}
