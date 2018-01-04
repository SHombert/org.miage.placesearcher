package org.miage.placesearcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RatingBar mRatingBar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the rating bar from layout file
        this.mRatingBar = (RatingBar) this.findViewById(R.id.ratingBar);

        // Get the textview from layout file
        this.mTextView = (TextView) this.findViewById(R.id.textView);

        // Define onClickListener on the text view : decrease rating at each click
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.mRatingBar.setRating(MainActivity.this.mRatingBar.getRating() - 1);
            }
        });
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();

        // Increment rating bar's rating
        this.mRatingBar.setRating(this.mRatingBar.getRating() + 1);
    }
}
