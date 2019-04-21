package com.android.gumeoficial.mapsaplication;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    ///Para el searchview
    SupportMapFragment mapFragment;
    SearchView searchView;
    //Para la vista de satelite
    TextView tvNormal, tvSetellite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Para poder implementar la vista de satellite
        tvNormal = findViewById(R.id.tv_normal);
        tvSetellite = findViewById(R.id.tv_satellite);

        //Implementacion de vista de satellite
        tvNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                tvNormal.setBackgroundResource(R.drawable.mapa);
                tvSetellite.setVisibility(View.VISIBLE);
                tvNormal.setVisibility(View.GONE);

            }
        });

        tvSetellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                tvSetellite.setBackgroundResource(R.drawable.satelite);
                tvNormal.setVisibility(View.VISIBLE);
                tvSetellite.setVisibility(View.GONE);

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        //--------------------------------------------------


        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location= searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder((MainActivity.this));
                    try{
                        addressList = geocoder.getFromLocationName(location,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address =  addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    //Lo siguiente es para la implementacion de la vista de satellite
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
    }
}
