package com.example.philcidr;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by TheArgus on 4/20/2015.
 * This is an implimenation of the OnItemSelectedListener.
 * It is used to handle cases where a value is input via selection from a spinner control.
 */
public class MySpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public static final byte NUM_SUBNETS = 0;
    public static final byte NUM_HOSTS = 1;
    public static final byte SUBNET_BITS = 2;
    public static final byte HOST_BITS = 3;

    int previousPosition = 0;
    int newPosition = 0;

    public void valueChanged(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        newPosition = position;
        if (newPosition != previousPosition) {
            previousPosition = newPosition;
            this.valueChanged(parent, view, position, id);
        }
    }

    // TODO: Check this out, see if you can make it work for a case where a selection goes out of range
    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
}
