package com.example.covidtracer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MeetingActivity extends FragmentActivity implements OnMapReadyCallback {

    private LatLng coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);


        String metUserID = getIntent().getStringExtra("MET_USER_ID");

        float latitude = getIntent().getFloatExtra("LATITUDE", 26.4499f);
        float longitude = getIntent().getFloatExtra("LONGITUDE", 80.3319f);
        coordinates = new LatLng(latitude, longitude);

        String date = getIntent().getStringExtra("DATE");
        String healthStatus = getIntent().getStringExtra("HEALTH_STATUS");

        TextView meetingDateView = findViewById(R.id.meetingDate);
        TextView healthStatusView = findViewById(R.id.healthStatus);

        meetingDateView.setText(String.format(getApplicationContext().getResources().getString(R.string.meeting_date),
                date));

        int textColor = Color.GRAY;

        if (healthStatus != null) {
            if (healthStatus.toUpperCase().contains("COVID"))
                textColor = Color.rgb(235, 64, 52);
            else if (healthStatus.toLowerCase().contains("healthy"))
                textColor = Color.rgb(72, 240, 108);
        }

        healthStatusView.setTextColor(textColor);
        healthStatusView.setText(String.format(getApplicationContext().getResources().getString(R.string.health_status),
                healthStatus));


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(16);

        CircleOptions options = new CircleOptions()
                .radius(20)
                .strokeWidth(3)
                .center(coordinates);

        googleMap.addMarker(new MarkerOptions().position(coordinates).title("Point of Contact"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        googleMap.addCircle(options);
    }

}