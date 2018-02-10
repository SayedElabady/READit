package capstone.udacity.com.readit.Listeners;

import android.net.Uri;

/**
 * Created by Sayed El-Abady on 2/10/2018.
 */

public interface OnUploadComplete {

    void onSuccess(Uri downloadUrl);

    void onFailed(String errorMessage);
}
