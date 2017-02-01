package pt.edp.fusedlocation;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double lat = getIntent().getDoubleExtra("lat",0);
        double lng = getIntent().getDoubleExtra("lng",0);
        LatLng aqui = new LatLng(lat,lng);

        mMap.addMarker(new MarkerOptions().position(aqui).title("Eu estou aqui!")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aqui, 20));

    }
}
