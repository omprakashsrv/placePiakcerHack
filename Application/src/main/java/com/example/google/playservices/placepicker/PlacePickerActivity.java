package com.example.google.playservices.placepicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by OMPRAKASH on 17-04-2017.
 */

public class PlacePickerActivity extends AppCompatActivity implements DialogInterface.OnCancelListener {

    public static final int PLACE_PICKER_REQUEST = 0x50;
    public static final String PLACE_PICKER_TYPE = "PLACE_PICKER_TYPE";
    private int placePickerType;

    public static final int MAP_PLACE_PICKER = 0;
    public static final int AUTO_COMPLETE_PLACE_PICKER = MAP_PLACE_PICKER + 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_picker_activity);
        placePickerType = getIntent().getIntExtra(PLACE_PICKER_TYPE, MAP_PLACE_PICKER);
        if (placePickerType == MAP_PLACE_PICKER) {
            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                int connectionStatusCode = e.getConnectionStatusCode();
            } catch (GooglePlayServicesNotAvailableException e) {
                int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            }
        } else {
            try {
                Intent placePickerIntent;
                PlaceAutocomplete.IntentBuilder builder = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN);
                placePickerIntent = builder.build(this);
                startActivityForResult(placePickerIntent, PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                int connectionStatusCode = e.getConnectionStatusCode();
            } catch (GooglePlayServicesNotAvailableException e) {
                int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            data = new Intent();
        }
        data.putExtra(PLACE_PICKER_TYPE, placePickerType);
        if (requestCode == PLACE_PICKER_REQUEST) {
            setResult(resultCode, data);
        }
        finish();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
    }
}
