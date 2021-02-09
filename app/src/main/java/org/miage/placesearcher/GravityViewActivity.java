package org.miage.placesearcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.gofynd.gravityview.GravityView;

/**
 * Activité vide supplémentaire pour faire un test du framework
 * Accessible depuis la PlaceDetailActivity
 */
public class GravityViewActivity extends AppCompatActivity {

    GravityView gravityView;
    @BindView(R.id.bg)
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravityview);

        ButterKnife.bind(this);

        String imageUrl = "https://maps.googleapis.com/maps/api/streetview?size=600x400&location="+
                getIntent().getDoubleExtra("latitude",0)+","+
                getIntent().getDoubleExtra("longitude",0)+
                "&key=AIzaSyBcRt3W8uifSjrhXBu2467iz9ciQqMNTzM";

        gravityView = GravityView.getInstance(this);
        if(!gravityView.deviceSupported()){
            Toast.makeText(this,"Device Not supported",Toast.LENGTH_SHORT).show();
        }else{
            gravityView.setImage(imageView,R.drawable.test2).center();
            
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gravityView.registerListener();
    }
    @Override
    protected void onStop() {
        super.onStop();
        gravityView.unRegisterListener();
    }

}
