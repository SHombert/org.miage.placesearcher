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

    public void searchPlacesFromAddress(String search) {
        // Launch Async task
        new PlaceSearchAsyncTask().execute(search);
    }

    private final static class PlaceSearchAsyncTask extends AsyncTask<String, Void, List<Place>> {

        @Override
        protected List<Place> doInBackground(String... params) {
            // Here we are in a new background thread
            try {
                final OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url("https://api-adresse.data.gouv.fr/search/?q=" + params[0])
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
                    return foundPlaces;
                }
            } catch (IOException e) {
                // Silent catch, no places will be displayed
            } catch (JSONException e) {
                // Silent catch, no places will be displayed
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Place> foundPlaces) {
            // Here we are in main thread again
            super.onPostExecute(foundPlaces);

            EventBusManager.BUS.post(new SearchResultEvent(foundPlaces));
        }
    }
}
