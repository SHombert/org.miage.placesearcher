package org.miage.placesearcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexmorel on 04/01/2018.
 */

public class PlaceDetailActivity extends AppCompatActivity {

    @BindView(R.id.activity_detail_place_street)
    TextView mPlaceStreet;
    private String mPlaceStreetValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        mPlaceStreetValue = getIntent().getStringExtra("placeStreet");
        mPlaceStreet.setText(mPlaceStreetValue);
    }

    @OnClick(R.id.activity_detail_place_street)
    public void clickedOnPlaceStreet() {
        finish();
    }

    @OnClick(R.id.activity_detail_button_search)
    public void clickedOnGoogleSearch() {
        // Open browser using an Intent
        Uri url = Uri.parse("https://www.google.fr/search?q=" + mPlaceStreetValue);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }

    @OnClick(R.id.activity_detail_button_share)
    public void clickedOnShare() {
        // Open share picker using an Intent
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "J'ai découvert " + mPlaceStreetValue + " grâce à Place Searcher !");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
