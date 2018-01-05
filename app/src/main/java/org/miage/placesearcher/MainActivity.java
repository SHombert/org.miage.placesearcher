package org.miage.placesearcher;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.miage.placesearcher.model.Place;
import org.miage.placesearcher.ui.PlaceAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding ButterKnife annotations now that content view has been set
        ButterKnife.bind(this);

        // Define list of places
        List<Place> places = new ArrayList<Place>();
        for (int i = 0; i < 50; i ++) {
            places.add(new Place(0, 0, "Street" + i, "44000", "Nantes"));
        }

        // Instanciance PlaceAdapter
        PlaceAdapter placeAdapter = new PlaceAdapter(this, places);
        mListView.setAdapter(placeAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AssetFileDescriptor afd = null;
                try {
                    afd = getAssets().openFd("house.mp3");

                    MediaPlayer player = new MediaPlayer();
                    player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    player.prepare();
                    player.start();

                    // Launch PlaceDetailActivity and give the selected place's street
                    Intent seePlaceDetailIntent = new Intent(MainActivity.this, PlaceDetailActivity.class);
                    Place place = (Place) adapterView.getItemAtPosition(i);
                    seePlaceDetailIntent.putExtra("placeStreet", place.getStreet());
                    startActivity(seePlaceDetailIntent);
                } catch (IOException e) {
                    // Silent catch : sound will not be played
                }
            }
        });
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();

        // Create AsyncTask
        AsyncTask<String, Void, Response> asyncTask = new AsyncTask<String, Void, Response>() {

            @Override
            protected Response doInBackground(String... params) {
                // Here we are in a new background thread
                try {
                    final OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(params[0])
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    return response;
                } catch (IOException e) {
                    // Silent catch, no places will be displayed
                }
                return null;
            }

            @Override
            protected void onPostExecute(Response response) {
                super.onPostExecute(response);

                // Here we are in caller Thread (i.e. UI thread here as onResume() is called on UI Thread)
                if (response != null && response.body() != null) {
                    try {
                        Toast toast = Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG);
                        toast.show();
                    } catch (IOException e) {
                       // Silent catch, toast will not be shown
                    }
                }
            }
        };
        asyncTask.execute("https://api-adresse.data.gouv.fr/search/?q=Place%20du%20commerce");
    }
}
