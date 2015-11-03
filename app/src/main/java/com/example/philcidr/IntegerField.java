package com.example.philcidr;

import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Dani on 10/28/2015.
 */
public class IntegerField extends InputField {

    public int oldInteger = -3;
    public int newInteger = -3;
    public String oldString = "";
    public String newString = "";
    public RuleSet rules;

    public IntegerField(Context context) {
        super(context);
    }

    public IntegerField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntegerField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected static class RuleSet {


        public void isEmptyString(IntegerField thisField) {
            // printVariables(thisField, "newString is empty, setting newInteger to -1");
            thisField.newInteger = -1;
            thisField.setText("");
            // makeToast("no new value");
            // printVariables(thisField, "no newInteger, newString is empty, setting text to oldString");
            thisField.setText(thisField.oldString);
            // printVariables(thisField, "value has not changed, lose focus event concluded");
        }

        public void isSame(IntegerField thisField) {
            // this assignment is really unnecessary but it's good to be consistent
            // printVariables(thisField, "oldString equals newString, setting newInteger = oldInteger");
            thisField.newInteger = thisField.oldInteger;
            thisField.setText(thisField.oldString);
            // makeToast("no change");
            // printVariables(thisField, "no change in values");
        }

        public void isDifferent(IntegerField thisField, int value) {
            // makeToast("newInteger = "+newInteger);
            // printVariables(thisField, "integer successfully parsed from newString, set to newInteger");
        }

        public void isOutOfRange(IntegerField thisField) {
            //System.out.println(e.getMessage());
            //System.out.println(e.getClass().getName());

            // if there is a value but it is invalid, send this control value
            // printVariables(thisField, "parse failed, setting newInteger to -2");
            thisField.newInteger = -2;
            // printVariables(thisField, "input was invalid for integer");
        }

        public void isOtherError(IntegerField thisField, Throwable e) {
            // if there was some other error, just treat it like no value was entered
            // printVariables(thisField, "unhandled exception, acting as though no value was entered");
            thisField.newInteger = thisField.oldInteger;
            if (thisField.newInteger == -1) {
                thisField.setText("");
            }
            else {
                thisField.setText(thisField.oldString);
            }

            // printVariables(thisField, "done. error info incoming...");

            // and print the error
            System.out.println(thisField.getId() + " | " + thisField.getTag().toString() + ": Something went very wrong");
            System.out.println(e.getMessage());
            System.out.println(e.getClass().getName());
        }
    }

    public void setRules(RuleSet r) {
        rules = r;
    }

    @Override
    void focusGained(EditText editText) {
        // makeToast("you are focused!");
        printVariables(editText, "focus gained");

        // set the value of the oldString variable
        oldString = editText.getText().toString();
        printVariables(editText, "got oldString");

        // if it's an empty string, no reason to try to parse it
        if (oldString.length() == 0) {
            oldInteger = -1;
            // makeToast("no oldInteger!");
            printVariables(editText, "no oldInteger, oldString is empty");
        }
        // if it's not an empty string, try to parse it
        else {
            try {
                // Try to parse an integer from the EditText and set it to oldInteger
                oldInteger = Integer.parseInt(oldString);
                // makeToast("oldInteger = "+oldInteger, Toast.LENGTH_LONG).show();
                printVariables(editText, "integer parsed from oldInteger");
            } catch (Exception e) {
                // If something else went wrong, make everything the same as if it was empty
                oldString = "";
                oldInteger = -1;
                // makeToast("something went wrong parsing the oldInteger");
                printVariables(editText, "something went wrong parsing the oldInteger");
                System.out.println(e.getMessage());
                System.out.println(e.getClass().getName());
            }
        }

        // reset the text to blank so that you can begin typing
        editText.setText("");
    }

    @Override
    void focusLost(EditText editText) {

        // makeToast("you lose focus!");
        printVariables(editText, "focus lost");

        // set the value of the newString variable
        newString = editText.getText().toString();
        printVariables(editText, "got newString");

        // if the new value is an empty string, there's no reason to parse it.
        if (newString.length() == 0) {
            rules.isEmptyString(this);
            return;
        }

        // if the string value of the oldString and newString are equal, why go any further?
        if (oldString.equals(newString)) {
            rules.isSame(this);
            return;
        }

        // if the values are different and it is not an empty string, time to parse
        try {
            // Try to parse your value, as it exists when you lose focus
            newInteger = Integer.parseInt(newString);
        }
        // This exception will be thrown if parsing the input results in an invalid integer
        catch(NumberFormatException e){
            rules.isOutOfRange(this);
            return;
        }
        // This catch handles any other exceptions thrown while parsing
        catch(Exception e){
            rules.isOtherError(this, e);
            return;
        }

        // if you get this far, the value has changed
        rules.isDifferent(this, newInteger);
        printVariables(editText, "lose focus event concluded");
    }

    @Override
    Integer parse(String inputString){
        //TODO: Write this method
        //TODO: Be amazing
        return 0;
    }



    public void valueChanged(View view) {
        ((EditText)view).setText(((Integer) newInteger).toString());
    }

    protected void printVariables(View view, String firstMessage){
        System.out.println(
                "*********************************************************************\n" +
                        "*   " + view.getId() + " | " + view.getTag().toString() + ": \n" +
                        "*       " + firstMessage + "\n" +
                        "*\n" +
                        "*           oldString = " + oldString + "\n" +
                        "*           oldInteger = " + oldInteger + "\n" +
                        "*\n" +
                        "*           newString = " + newString + "\n" +
                        "*           newInteger = " + newInteger + "\n" +
                        "*\n" +
                        "*           EditText value = " + ((EditText) view).getText().toString() + "\n" +
                        "*********************************************************************");
    }

    protected void printVariables(View view, String firstMessage, boolean toast){
        printVariables(view, firstMessage);
        if (toast) makeToast(firstMessage);
    }

    protected void printVariables(View view){
        System.out.println(
                "*********************************************************************\n"+
                        "*   " + view.getId() + " | " + view.getTag().toString() + ": \n"+
                        "*       no message\n"+
                        "*\n"+
                        "*           oldString = " + oldString + "\n"+
                        "*           oldInteger = " + oldInteger + "\n"+
                        "*\n"+
                        "*           newString = " + newString + "\n"+
                        "*           newInteger = " + newInteger + "\n"+
                        "*\n"+
                        "*           EditText value = " + ((EditText)view).getText().toString() + "\n"+
                        "*********************************************************************");
    }
}
