package org.miage.placesearcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.miage.placesearcher.model.Person;
import org.miage.placesearcher.ui.PersonAdapter;

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

        // Define list of persons
        List<Person> listItems = new ArrayList<Person>();
        for (int i = 0; i < 50; i ++) {
            listItems.add(new Person("FirstName" + i, "LastName" + i));
        }
        // Instanciate a PersonAdapter
        ArrayAdapter adapter = new PersonAdapter(this, listItems);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();
    }
}
