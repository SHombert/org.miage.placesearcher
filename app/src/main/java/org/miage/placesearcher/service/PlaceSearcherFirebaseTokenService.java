package org.miage.placesearcher.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by alexmorel on 14/02/2018.
 */

public class PlaceSearcherFirebaseTokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Step 1: Get  token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("[FireBase Token]", "Refreshed token: " + refreshedToken);

        // Step 2: send token to server
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        // In a true application with a server something should be done here
    }
}
