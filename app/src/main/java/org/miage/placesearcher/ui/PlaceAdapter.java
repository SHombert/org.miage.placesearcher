package org.miage.placesearcher.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.miage.placesearcher.R;
import org.miage.placesearcher.model.Place;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexmorel on 04/01/2018.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {

    @BindView(R.id.place_adapter_street)
    TextView mPlaceStreetTextView;

    @BindView(R.id.place_adapter_zip)
    TextView mPlaceZipTextView;

    @BindView(R.id.place_adapter_city)
    TextView mPlaceCityTextView;

    @BindView(R.id.place_adapter_icon)
    ImageView mPlaceIcon;

    public PlaceAdapter(Context context,List<Place> places) {
        super(context, -1, places);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View actualView = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actualView = inflater.inflate(R.layout.place_adapter, parent, false);
        }
        ButterKnife.bind(this, actualView);
        mPlaceStreetTextView.setText(getItem(position).getStreet());
        mPlaceZipTextView.setText(getItem(position).getZipCode());
        mPlaceCityTextView.setText(getItem(position).getCity());
        if (getItem(position).getStreet().contains("1")) {
            mPlaceIcon.setImageResource(R.drawable.street_icon);
        } else {
            mPlaceIcon.setImageResource(R.drawable.home_icon);
        }
        return actualView;
    }
}
