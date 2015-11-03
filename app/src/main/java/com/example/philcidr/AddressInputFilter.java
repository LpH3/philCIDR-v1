package com.example.philcidr;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Dani on 10/30/2015.
 */
public class AddressInputFilter implements InputFilter {
    public AddressInputFilter() {
        super();
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        return null;
    }
}
