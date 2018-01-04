package org.miage.placesearcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ratingBar) RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding ButterKnife annotations now that content view has been set
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();

        // Increment rating bar's rating
        this.mRatingBar.setRating(this.mRatingBar.getRating() + 1);
    }

    @OnClick(R.id.textView)
    public void clickedOnTextField() {
        // Decrease rating at each click on TextField
        this.mRatingBar.setRating(this.mRatingBar.getRating() - 1);
    }
}
