package org.miage.placesearcher;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by alexmorel on 05/01/2018.
 */

public class PlaceSearchService {

    public static PlaceSearchService INSTANCE = new PlaceSearchService();

    private PlaceSearchService() {

    }

    public void searchPlacesFromAdress(final String search) {
        // Create AsyncTask
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                // Here we are in a new background thread
                try {
                    final OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url("https://api-adresse.data.gouv.fr/search/?q=" + search)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    Log.d("RECEIVED PLACES", response.body().string());
                } catch (IOException e) {
                    // Silent catch, no places will be displayed
                }
                return null;
            }
        }.execute();
    }
}
