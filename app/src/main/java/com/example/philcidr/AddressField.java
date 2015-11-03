package com.example.philcidr;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Dani on 10/29/2015.
 */
public class AddressField extends IntegerField {

    /*
    Editable ip;
    InputFilter[] filters;
    */

    public AddressField(Context context) {
        super(context);
        //this.setText("0");
        /*
        ip = this.getEditableText();
        filters = new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        int sourceInt;
                        if (source.length() == 0){
                            printVariables("new text is blank");
                            return "";
                        }
                        if (dest.toString() == source.toString()){
                            printVariables("new text is not so new");
                            return null;
                        }
                        else {
                            try {
                                sourceInt = Integer.parseInt(source.toString());
                            } catch (NumberFormatException e) {
                                printVariables("number is too big");
                                return dest;
                            } catch (Exception e) {
                                printVariables("something else went weird");
                                System.out.println(e.getMessage());
                                System.out.println(e.getClass().getName());
                                return dest;
                            }
                        }
                        printVariables("text changed to "+sourceInt);
                        return null;
                    }
                }
        };
        ip.setFilters(filters);
        */
    }

    public AddressField(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this.setText("0");
        /*
        ip = this.getEditableText();
        filters = new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        int sourceInt;
                        if (source.length() == 0){
                            printVariables("new text is blank");
                            return "";
                        }
                        if (dest.toString() == source.toString()){
                            printVariables("new text is not so new");
                            return null;
                        }
                        else {
                            try {
                                sourceInt = Integer.parseInt(source.toString());
                            } catch (NumberFormatException e) {
                                printVariables("number is too big");
                                return dest;
                            } catch (Exception e) {
                                printVariables("something else went weird");
                                System.out.println(e.getMessage());
                                System.out.println(e.getClass().getName());
                                return dest;
                            }
                        }
                        printVariables("text changed to "+sourceInt);
                        return null;
                    }
                }
        };
        ip.setFilters(filters);
        */
    }

    public AddressField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //this.setText("0");
        /*
        ip = this.getEditableText();
        filters = new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        int sourceInt;
                        if (source.length() == 0){
                            printVariables("new text is blank");
                            return "";
                        }
                        if (dest.toString() == source.toString()){
                            printVariables("new text is not so new");
                            return null;
                        }
                        else {
                            try {
                                sourceInt = Integer.parseInt(source.toString());
                            } catch (NumberFormatException e) {
                                printVariables("number is too big");
                                return dest;
                            } catch (Exception e) {
                                printVariables("something else went weird");
                                System.out.println(e.getMessage());
                                System.out.println(e.getClass().getName());
                                return dest;
                            }
                        }
                        printVariables("text changed to "+sourceInt);
                        return null;
                    }
                }
        };
        ip.setFilters(filters);
        */
    }

}
