package com.example.philcidr;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Lewis Hubbard on 4/18/2015.
 */

/**
 * This is my OnFocusChangeListener for all the text fields. It goes in and finds out what
 * the old value was, then it saves that until you focus on a new field. Therefore when you
 * first go out of focus from the initial field, it can check the current value against the
 * one that was there when you started. In this way, you can trigger events specifically for
 * user-initiated text changes, and have those events trigger only if the user changes the
 * value while they're in there. :) In this class version I have methods for what code to
 * execute if the value has or has not changed.
 */

public class MyIntEditTextOnFocusChangeListener implements View.OnFocusChangeListener {

    long previousValue;
    long finalValue;

    public void valueChanged(View view){
        EditText theThing = (EditText)view;
        theThing.getText().toString();

    }

    public void valueNotChanged(View view){

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        // we slyly set thisThing to whatever view we have focused upon
        EditText thisThing = (EditText)view;
        if (b){
            // You are focused!
            // (this toast will tell you so)
            // Toast.makeText(NetworkConfigActivity.this, "you are focused!", Toast.LENGTH_LONG).show();
            try {
                // Try to parse an integer from the EditText and set it to previousValue
                previousValue = Long.parseLong(thisThing.getText().toString());
                thisThing.setText("");
                // (this toast will tell you what that value is)
                // Toast.makeText(NetworkConfigActivity.this, "previousValue = "+previousValue, Toast.LENGTH_LONG).show();
            }
            catch(Exception e) {
                // If there is no value to begin with, go ahead and set the previous value to -1
                previousValue = -1;
                // (this toast will tell you there was no value)
                // Toast.makeText(NetworkConfigActivity.this, "no previous value!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            // You lose focus!
            // (this toast will tell you so)
            // Toast.makeText(NetworkConfigActivity.this, "you lose focus!", Toast.LENGTH_LONG).show();
            try {
                // Try to parse your value, as it exists when you lose focus
                finalValue = Long.parseLong(thisThing.getText().toString());
                // (this toast will tell you what that value is, if it exists)
                // Toast.makeText(NetworkConfigActivity.this, "finalValue = "+finalValue, Toast.LENGTH_LONG).show();
            }
            catch(Exception e){
                // If there is no value when you're done, go ahead and set the final value to -1
                finalValue = previousValue;
                String finalValue_string = ((Long)finalValue).toString();
                if (finalValue == -1) {
                    thisThing.setText("");
                }
                else {
                    thisThing.setText(finalValue_string);
                }
                // (this toast will tell you there is no final value)
                // Toast.makeText(NetworkConfigActivity.this, "no final value", Toast.LENGTH_LONG).show();
            }
            if (finalValue != previousValue){
                // Toast.makeText(NetworkConfigActivity.this, "value has changed", Toast.LENGTH_LONG).show();
                // if your value has changed, this code executes
                this.valueChanged(view);
            }
            else {
                // Toast.makeText(NetworkConfigActivity.this, "value has not changed", Toast.LENGTH_LONG).show();
                // if your value has not changed, this code executes
                this.valueNotChanged(view);
            }
        }
    }
}
