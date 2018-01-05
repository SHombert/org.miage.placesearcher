package org.miage.placesearcher;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.miage.placesearcher.model.Place;
import org.miage.placesearcher.ui.PlaceAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

                    Intent seePlaceDetailIntent = new Intent(MainActivity.this, PlaceDetailActivity.class);
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
    }
}
