package edu.intec.willgo;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class AddEditActivity extends ActionBarActivity {


    // has the id of the preference
    public int messageID;
    public SqliteHelper sql;
    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private MapFragment mapView;
    private GoogleMap map;
    private LatLng currLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_form);
        Intent intent = getIntent();

        //Setting up the map
        // Gets the MapView from the XML layout and creates it
        mapView = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //receives the id of the preference, 0 if it is a new one
        messageID = Integer.parseInt(intent.getStringExtra(EXTRA_MESSAGE));

        if(messageID != 0){
            fillFields();
        }else {
            // Updates the location and zoom of the MapView
            Location lastKnownLocation = this.getLastKnownLocation();
            this.updateMarker(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()), null);
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                AddEditActivity.this.updateMarker(latLng, null);
            }
        });

    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    // this method executes when user clicks save on the form
    public void savePreference(View button) {

        EditText preferenceName = (EditText) findViewById(R.id.EditTextName);
        EditText preferencePlace = (EditText) findViewById(R.id.EditTextPlace);
        EditText preferenceLocation = (EditText) findViewById(R.id.EditTextLocation);
        String latLong = this.currLatLng == null ? "" : this.serializeLatLong(this.currLatLng);

        sql = new SqliteHelper(this);
        Preference p = new Preference(messageID, preferenceName.getText().toString(), preferencePlace.getText().toString(), preferenceLocation.getText().toString(), latLong);
        //adds new preference if 0, else updates preference
        if(messageID == 0){

            sql.insert(p);
            this.finish();

        } else {

            sql.update(p);
            this.finish();

        }
    }

    private String serializeLatLong(LatLng latLong){
        String result = "";
        result = latLong.latitude + "," + latLong.longitude;
        return result;
    }

    // this method executes when user clicks cancel on the form
    public void cancelActivity(View button) {
        //finishes current activity and returns to previous activity
        this.finish();
    }


    //fills the fields of the form with preference data
    public void fillFields(){

        EditText preferenceName = (EditText) findViewById(R.id.EditTextName);
        EditText preferencePlace = (EditText) findViewById(R.id.EditTextPlace);
        EditText preferenceLocation = (EditText) findViewById(R.id.EditTextLocation);

        //fill fields from database

        sql = new SqliteHelper(this);
        Preference p = sql.findById(messageID);
        preferenceName.setText(p.getName());
        preferencePlace.setText(p.getPlace());
        preferenceLocation.setText(p.getLocation());

        //Set map coords from preferences
        String coords = p.getCoor();
        String[] latLongStr = coords.split(",");
        if(latLongStr.length == 2){
            double lat = Double.parseDouble(latLongStr[0]);
            double lon = Double.parseDouble(latLongStr[1]);
            this.updateMarker(new LatLng(lat,lon), p);
        }
    }

    private void updateMarker(LatLng latLong, Preference pref){
        String prefName = pref == null ? "" : pref.getName();
        currLatLng = latLong;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currLatLng, 16);

        map.clear();
        map.addMarker(new MarkerOptions().position(currLatLng).title(prefName));
        map.animateCamera(cameraUpdate);
    }
}
