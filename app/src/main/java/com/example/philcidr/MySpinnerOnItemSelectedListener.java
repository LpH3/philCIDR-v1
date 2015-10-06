package com.example.philcidr;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by TheArgus on 4/20/2015.
 */
public class MySpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    int previousPosition = 0;
    int newPosition = 0;

    public void valueChanged(AdapterView<?> parent, View view, int position, long id) {

        int index = Integer.parseInt(view.getTag().toString());
        switch (index) {
            case Network.NUM_SUBNETS:
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        newPosition = position;
        if (newPosition != previousPosition) {
            previousPosition = newPosition;
            this.valueChanged(parent, view, position, id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
}
