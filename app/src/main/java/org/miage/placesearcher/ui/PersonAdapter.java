package org.miage.placesearcher.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.miage.placesearcher.R;
import org.miage.placesearcher.model.Person;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexmorel on 04/01/2018.
 */

public class PersonAdapter extends ArrayAdapter<Person> {

    @BindView(R.id.person_adapter_firstname)
    TextView mFirstNameTextView;

    @BindView(R.id.person_adapter_lastname)
    TextView mLastNameTextView;

    public PersonAdapter(Context context, List<Person> persons) {
        super(context, -1, persons);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View actualView = convertView;
         if (convertView == null) {
             LayoutInflater inflater = (LayoutInflater) getContext()
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             actualView = inflater.inflate(R.layout.person_adapter, parent, false);
         }
        ButterKnife.bind(this, actualView);
        mFirstNameTextView.setText(getItem(i).getFirstName());
        mLastNameTextView.setText(getItem(i).getLastName());
        return actualView;
    }
}