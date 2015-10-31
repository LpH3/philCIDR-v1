package com.example.philcidr;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dani on 10/29/2015.
 */
public abstract class InputField extends EditText implements View.OnFocusChangeListener {

    Activity mActivity;
    Context mContext;

    String oldString = "";
    String newString = "";

    public InputField(Context context) {
        super(context);
        mContext = context;
        setOnFocusChangeListener(this);
    }

    public InputField(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOnFocusChangeListener(this);
    }

    public InputField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOnFocusChangeListener(this);
    }

    public void init(Activity activity) {
        // This called by the containing activity
        mActivity = activity;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        EditText editText = (EditText)view;

        if (b) focusGained(editText);
        else focusLost(editText);

    }

    abstract void focusGained(EditText editText);
    abstract void focusLost(EditText editText);
    abstract Object parse(String inputString);

    protected void makeToast(String toastString) {

        Toast.makeText(mContext, toastString, Toast.LENGTH_LONG).show();
    }

}
