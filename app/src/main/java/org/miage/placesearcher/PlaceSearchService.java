package org.miage.placesearcher;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.miage.placesearcher.event.EventBusManager;
import org.miage.placesearcher.event.SearchResultEvent;
import org.miage.placesearcher.model.Place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void searchPlacesFromAddress(final String search) {
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
                    if (response != null && response.body() != null) {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        JSONArray jsonPlaces = jsonResult.getJSONArray("features");

                        List<Place> foundPlaces = new ArrayList<>();
                        for (int i = 0; i < jsonPlaces.length(); i++) {
                            JSONObject jsonPlace = jsonPlaces.getJSONObject(i);
                            JSONObject properties = jsonPlace.getJSONObject("properties");
                            String city = properties.getString("city");
                            String street = properties.getString("name");
                            String zipCode = properties.getString("postcode");
                            foundPlaces.add(new Place(0, 0, street, zipCode, city));
                        }
                        EventBusManager.BUS.post(new SearchResultEvent(foundPlaces));
                    }
                } catch (IOException e) {
                    // Silent catch, no places will be displayed
                } catch (JSONException e) {
                    // Silent catch, no places will be displayed
                }
                return null;
            }
        }.execute();
    }
}
