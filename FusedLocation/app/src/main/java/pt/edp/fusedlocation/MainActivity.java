package pt.edp.fusedlocation;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private TextView tv_latitude;
    private TextView tv_longitude;
    private Button bt_show_map;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_latitude = (TextView) findViewById(R.id.textView_latitude);
        tv_longitude = (TextView) findViewById(R.id.textView_longitude);
        bt_show_map = (Button) findViewById(R.id.button_show_map);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(6000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient.connect();

        bt_show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("lat", Double.parseDouble(tv_latitude.getText().toString()));
                i.putExtra("lng", Double.parseDouble(tv_longitude.getText().toString()));
                startActivity(i);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("conn-failed", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (mLastLocation == null) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Toast.makeText(this, "nova localizacao!", Toast.LENGTH_SHORT).show();
        if (tv_latitude.getText().equals(mLastLocation.getLatitude()) && tv_longitude.getText().equals(mLastLocation.getLongitude())) {
            contador++;
        }

        if (contador > 3) {
            tv_latitude.setText(String.valueOf(mLastLocation.getLatitude()));
            tv_longitude.setText(String.valueOf(mLastLocation.getLongitude()));
        } else {
            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            i.putExtra("lat", Double.parseDouble(tv_latitude.getText().toString()));
            i.putExtra("lng", Double.parseDouble(tv_longitude.getText().toString()));
            startActivity(i);
        }
    }
}