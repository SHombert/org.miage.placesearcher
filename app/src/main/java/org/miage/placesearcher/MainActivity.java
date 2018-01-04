package org.miage.placesearcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the rating bar from layout file
        this.mRatingBar = (RatingBar) this.findViewById(R.id.ratingBar);
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();

        // Increment rating bar's rating
        this.mRatingBar.setRating(this.mRatingBar.getRating() + 1);
    }
}
