package kestone.com.kestone;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 7/27/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    StorageUtilities storage;

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = null;
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        storeDeviceToken(refreshedToken);
    }

    private void storeDeviceToken(String token) {
        storage = new StorageUtilities(getApplicationContext());
        storage.storeDeviceToken(token);
    }
}
