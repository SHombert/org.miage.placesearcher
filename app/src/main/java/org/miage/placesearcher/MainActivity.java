package org.miage.placesearcher;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.otto.Subscribe;

import org.miage.placesearcher.event.EventBusManager;
import org.miage.placesearcher.event.SearchResultEvent;
import org.miage.placesearcher.model.Place;
import org.miage.placesearcher.ui.PlaceAdapter;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listView) ListView mListView;
    private PlaceAdapter mPlaceAdapter;

    @BindView(R.id.activity_main_search_adress_edittext)
    EditText mSearchEditText;

    @BindView(R.id.activity_main_loader)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding ButterKnife annotations now that content view has been set
        ButterKnife.bind(this);

        // Instanciance PlaceAdapter with empty content
        mPlaceAdapter = new PlaceAdapter(this, new ArrayList<Place>());
        mListView.setAdapter(mPlaceAdapter);

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do when texte is about to change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // While text is changing, hide list and show loader
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Once text has changed
                // Show a loader
                mProgressBar.setVisibility(View.VISIBLE);

                // Launch a search through the PlaceSearchService
                PlaceSearchService.INSTANCE.searchPlacesFromAddress(editable.toString());
            }
        });
    }

    @OnItemClick(R.id.listView)
    public void onItemSelected(int position) {
        // Step 1: play a sound
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("house.mp3");

            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            // Silent catch : sound will not be played
        }

        // Step 2: Launch PlaceDetailActivity and give the selected place's street
        Intent seePlaceDetailIntent = new Intent(MainActivity.this, PlaceDetailActivity.class);
        Place place = (Place) mPlaceAdapter.getItem(position);
        seePlaceDetailIntent.putExtra("placeStreet", place.getStreet());
        startActivity(seePlaceDetailIntent);
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();

        // Register to Event bus : now each time an event is posted, the activity will receive it if it is @Subscribed to this event
        EventBusManager.BUS.register(this);
    }

    @Override
    protected void onPause() {
        // Unregister from Event bus : if event are posted now, the activity will not receive it
        EventBusManager.BUS.unregister(this);

        // Do NOT forget to call super.onPause()
        super.onPause();
    }

    @Subscribe
    public void searchResult(final SearchResultEvent event) {
        // Here someone has posted a SearchResultEvent

        // Step 1: Update adapter's model
        mPlaceAdapter.clear();
        mPlaceAdapter.addAll(event.getPlaces());

        // Step 2: hide loader
        mProgressBar.setVisibility(View.GONE);

    }
}
