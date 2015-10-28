package com.example.philcidr;

import android.app.Activity;
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

    public int oldValue = -3;
    public int newValue = -3;
    public String oldString = "";
    public String newString = "";

    String rangeError = "Value is out of range";

    private void printVariables(View view, String firstMessage){
        System.out.println(
                "*********************************************************************\n"+
                "*   " + view.getId() + " | " + view.getTag().toString() + ": \n"+
                "*       " + firstMessage + "\n"+
                "*\n"+
                "*           oldString = " + oldString + "\n"+
                "*           oldValue = " + oldValue + "\n"+
                "*\n"+
                "*           newString = " + newString + "\n"+
                "*           newValue = " + newValue + "\n"+
                "*\n"+
                "*           EditText value = " + ((EditText)view).getText().toString() + "\n"+
                "*********************************************************************");
    }

    private void printVariables(View view){
        System.out.println("*********************************************************************");
        System.out.println("*" + view.getId() + " | " + view.getTag().toString() + ": ");
        System.out.println("*    oldString = " + oldString);
        System.out.println("*    oldValue = " + oldValue);
        System.out.println("*    newString = " + newString);
        System.out.println("*    newValue = " + newValue);
        //System.out.println(view.getId() + " | " + view.getTag().toString() + ": oldString = " + oldString);
        //System.out.println(view.getId() + " | " + view.getTag().toString() + ": oldValue = " + newString);
        //System.out.println(view.getId() + " | " + view.getTag().toString() + ": oldValue = " + oldValue);
        //System.out.println(view.getId() + " | " + view.getTag().toString() + ": newValue = " + newValue);
        System.out.println("*********************************************************************");
    }

    public void valueChanged(View view) {
        ((EditText)view).setText(((Integer) newValue).toString());
    }

    /**
     * This override of the onFocusChange method handles situations where the EditText gains or
     * loses focus.
     * TODO: Figure out ways to make this work better for text fields which may accept text
     * TODO: Figure out how to make range issues easier to wor
     * @param view
     * @param b
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        // we slyly set editText to whatever view we have focused upon
        EditText editText = (EditText)view;

        if (b) {
            // Focus is gained
            // Toast.makeText(NetworkConfigActivity.this, "you are focused!", Toast.LENGTH_LONG).show();
            // System.out.println(view.getId() + " | " + view.getTag().toString() + ": focus gained");
            printVariables(view, "focus gained");

            // set the value of the oldString variable
            oldString = editText.getText().toString();
            printVariables(view, "got oldString");

            // if it's an empty string, no reason to try to parse it
            if (oldString.isEmpty()) {
                oldValue = -1;
                // Toast.makeText(NetworkConfigActivity.this, "no oldValue!", Toast.LENGTH_LONG).show();
                // System.out.println(view.getId() + " | " + view.getTag().toString() + ": No oldValue");
                printVariables(view, "no oldValue, oldString is empty");
            }
            // if it's not an empty string, try to parse it
            else {
                try {
                    // Try to parse an integer from the EditText and set it to oldValue
                    oldValue = Integer.parseInt(oldString);
                    // Toast.makeText(NetworkConfigActivity.this, "oldValue = "+oldValue, Toast.LENGTH_LONG).show();
                    printVariables(view, "integer parsed from oldValue");
                } catch (Exception e) {
                    // If something else went wrong, make everything the same as if it was empty
                    oldString = "";
                    oldValue = -1;
                    // Toast.makeText(NetworkConfigActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    // System.out.println(view.getId() + " | " + view.getTag().toString() + ": Something is wrong");
                    printVariables(view, "something went wrong parsing the oldValue");
                    System.out.println(e.getMessage());
                    System.out.println(e.getClass().getName());
                }
            }

            // reset the text to blank so that you can begin typing
            editText.setText("");
        }
        else{
            // Focus lost
            // Toast.makeText(NetworkConfigActivity.this, "you lose focus!", Toast.LENGTH_LONG).show();
            // System.out.println(view.getId() + " | " + view.getTag().toString() + ": focus lost");
            printVariables(view, "focus lost");

            // set the value of the newString variable
            newString = editText.getText().toString();
            printVariables(view, "got newString");

            // if the string value of the oldString and newString are equal, why go any further?
            if (oldString.equals(newString)) {
                // this assignment is really unnecessary but it's good to be consistent
                printVariables(view, "oldString equals newString, setting newValue = oldValue");
                newValue = oldValue;
                editText.setText(oldString);
                // Toast.makeText(NetworkConfigActivity.this, "no change", Toast.LENGTH_LONG).show();
                // System.out.println(view.getId() + " | " + view.getTag().toString() + ": No change in values");
                printVariables(view, "no change in values");
                return;
            }

            // if the new value is an empty string, there's once again no reason to parse it.
            if (newString.isEmpty()) {
                printVariables(view, "newString is empty, setting newValue to -1");
                newValue = -1;
                editText.setText("");
                // Toast.makeText(NetworkConfigActivity.this, "no new value", Toast.LENGTH_LONG).show();
                // System.out.println(view.getId() + " | " + view.getTag().toString() + ": No newValue");
                printVariables(view, "no newValue, newString is empty, setting text to oldString");
                editText.setText(oldString);
                printVariables(view, "value has not changed, lose focus event concluded");
                return;
            }

            // if the values are different and it is not an empty string, time to parse
            try {
                // Try to parse your value, as it exists when you lose focus
                newValue = Integer.parseInt(newString);
                // Toast.makeText(NetworkConfigActivity.this, "newValue = "+newValue, Toast.LENGTH_LONG).show();
                printVariables(view, "integer successfully parsed from newString, set to newValue");
            }
            catch(NumberFormatException e){
                //System.out.println(e.getMessage());
                //System.out.println(e.getClass().getName());

                // if there is a value but it is invalid, send this control value
                printVariables(view, "parse failed, setting newValue to -2");
                newValue = -2;
                printVariables(view, "input was invalid for integer");
            }
            catch(Exception e){
                // if there was some other error, just treat it like no value was entered
                printVariables(view, "unhandled exception, acting as though no value was entered");
                newValue = oldValue;
                if (newValue == -1) {
                    editText.setText("");
                }
                else {
                    editText.setText(oldString);
                }

                printVariables(view, "done. error info incoming...");

                // and print the error
                System.out.println(view.getId() + " | " + view.getTag().toString() + ": Something went very wrong");
                System.out.println(e.getMessage());
                System.out.println(e.getClass().getName());
                return;
            }

            // if you get this far, the value has changed
            this.valueChanged(view);
            printVariables(view, "value has been changed, lose focus event concluded");
        }
    }
}
