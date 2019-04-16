package hu.pemik.poseidon.gps;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import hu.pemik.poseidon.MainActivity;
import hu.pemik.poseidon.R;

public class GpsDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_demo);
        ActivityCompat.requestPermissions(GpsDemo.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }

    public void showLocation(View view) {
        GpsTracker gt = new GpsTracker(getApplicationContext());
        Location l = gt.getLocation();
        if( l == null){
            Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
        }else {
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
        }
    }
}
