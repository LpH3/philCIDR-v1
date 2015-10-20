package com.example.philcidr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HostLookupActivity extends Activity {

    View layout_Main;

    TextView label_netAddress;

    EditText txt_hostIP_1;
    EditText txt_hostIP_2;
    EditText txt_hostIP_3;
    EditText txt_hostIP_4;

    EditText txt_subnetIndex;
    EditText txt_hostIndex;

    int maxHostIndex;
    int maxSubnetIndex;

    byte[] maxIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_lookup);

        /** This chunk of code makes the background layout focusable and makes it so that
         *  when you "click" it it sets focus to it and hides the keyboard.
         */
        layout_Main = findViewById(R.id.layout_HostLookup);
        final InputMethodManager imm = (InputMethodManager)getSystemService(
                HostLookupActivity.this.INPUT_METHOD_SERVICE);
        layout_Main.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        Button bt_subnetSetup = (Button)findViewById(R.id.btn_page0);
        Button bt_subnetLookup = (Button)findViewById(R.id.btn_page2);

        // set up the "Max Host Index", "Max Subnet Index", "Max/Min IP" values.
        setMaxSubnetIndex();
        setMaxHostIndex();

        setMaxIP();

        // initialize the netAddress label
        label_netAddress = (TextView)findViewById(R.id.label_netAddress);

        // get a String array for the network address
        String [] netAddr = Network.getStringArray(Network.networkAddress);

        // set that netAddress label to the String array version
        label_netAddress.setText(netAddr[0]+"."+netAddr[1]+"."+netAddr[2]+"."+netAddr[3]+" / "+Network.blockBits);

        txt_hostIP_1 = (EditText)findViewById(R.id.txt_inputIP_1);
        txt_hostIP_1.setText(netAddr[0]);
        if (Network.blockBits >= 8) {
            txt_hostIP_1.setClickable(false);
            txt_hostIP_1.setFocusable(false);
            txt_hostIP_1.setFocusableInTouchMode(false);
        }
        txt_hostIP_2 = (EditText)findViewById(R.id.txt_inputIP_2);
        txt_hostIP_2.setText(netAddr[1]);
        if (Network.blockBits >= 16) {
            txt_hostIP_2.setClickable(false);
            txt_hostIP_2.setFocusable(false);
            txt_hostIP_2.setFocusableInTouchMode(false);
        }
        txt_hostIP_3 = (EditText)findViewById(R.id.txt_inputIP_3);
        txt_hostIP_3.setText(netAddr[2]);
        if (Network.blockBits >= 24) {
            txt_hostIP_3.setClickable(false);
            txt_hostIP_3.setFocusable(false);
            txt_hostIP_3.setFocusableInTouchMode(false);
        }
        txt_hostIP_4 = (EditText)findViewById(R.id.txt_inputIP_4);
        txt_hostIP_4.setText(netAddr[3]);

        txt_subnetIndex = (EditText)findViewById(R.id.txt_subnetIndex);
        txt_subnetIndex.setText("0");
        txt_hostIndex = (EditText)findViewById(R.id.txt_hostIndex);
        txt_hostIndex.setText("0");


        bt_subnetSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HostLookupActivity.this, SubnetSetupActivity.class));
            }
        });

        bt_subnetLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HostLookupActivity.this, SubnetLookupActivity.class));
            }
        });

        txt_subnetIndex.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                int subnetIndex = Integer.parseInt((((EditText)view).getText()).toString());
                int hostIndex = Integer.parseInt((txt_hostIndex.getText()).toString());

                if (subnetIndex < 0) {
                    Toast.makeText(HostLookupActivity.this, "Negative subnet. Don't do that!", Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else if (subnetIndex == 0) {
                    Toast.makeText(HostLookupActivity.this, "'Subnet Zero': Use caution.", Toast.LENGTH_LONG).show();
                    setHostAddress(subnetIndex, hostIndex);
                }
                else if (subnetIndex == maxSubnetIndex) {
                    Toast.makeText(HostLookupActivity.this, "'All-Ones Subnet': Be warned.", Toast.LENGTH_LONG).show();
                    setHostAddress(subnetIndex, hostIndex);
                }
                else if (subnetIndex > maxSubnetIndex) {
                    Toast.makeText(HostLookupActivity.this, "Out of Range. Max Index = " + ((Integer)maxSubnetIndex).toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else {
                    setHostAddress(subnetIndex, hostIndex);
                }
            }
        });

        txt_hostIndex.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                int hostIndex = Integer.parseInt((((EditText)view).getText()).toString());
                int subnetIndex = Integer.parseInt((txt_subnetIndex.getText()).toString());

                if (hostIndex < 0) {
                    Toast.makeText(HostLookupActivity.this, "Negative host. Don't do that!", Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else if (hostIndex == 0) {
                    Toast.makeText(HostLookupActivity.this, "Note: Showing a Subnet Address", Toast.LENGTH_LONG).show();
                    setHostAddress(subnetIndex, hostIndex);
                }
                else if (hostIndex == maxHostIndex) {
                    Toast.makeText(HostLookupActivity.this, "Note: Showing a Broadcast Address", Toast.LENGTH_LONG).show();
                    setHostAddress(subnetIndex, hostIndex);
                }
                else if (hostIndex > maxHostIndex) {
                    Toast.makeText(HostLookupActivity.this, "Out of Range. Max Index = " + ((Integer)maxSubnetIndex).toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else {
                    setHostAddress(subnetIndex, hostIndex);
                }
            }
        });

        txt_hostIP_1.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                Integer val = (int)finalValue;
                Integer minValue = Network.getByteAsUnsignedInt(Network.networkAddress[0]);
                Integer maxValue = Network.getByteAsUnsignedInt(maxIP[0]);

                if (val < minValue) {
                    Toast.makeText(HostLookupActivity.this, "The minimum value for this field is "+minValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                if (val > maxValue) {
                    Toast.makeText(HostLookupActivity.this, "The maximum value for this field is "+maxValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else {
                    setIndexes();
                }
            }
        });
        txt_hostIP_2.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                Integer val = (int)finalValue;
                Integer minValue = Network.getByteAsUnsignedInt(Network.networkAddress[1]);
                Integer maxValue = Network.getByteAsUnsignedInt(maxIP[1]);

                if (val < minValue) {
                    Toast.makeText(HostLookupActivity.this, "The minimum value for this field is "+minValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                if (val > maxValue) {
                    Toast.makeText(HostLookupActivity.this, "The maximum value for this field is "+maxValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else {
                    setIndexes();
                }
            }
        });
        txt_hostIP_3.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                Integer val = (int)finalValue;
                Integer minValue = Network.getByteAsUnsignedInt(Network.networkAddress[2]);
                Integer maxValue = Network.getByteAsUnsignedInt(maxIP[2]);

                if (val < minValue) {
                    Toast.makeText(HostLookupActivity.this, "The minimum value for this field is "+minValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                if (val > maxValue) {
                    Toast.makeText(HostLookupActivity.this, "The maximum value for this field is "+maxValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else {
                    setIndexes();
                }
            }
        });
        txt_hostIP_4.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                Integer val = (int)finalValue;
                Integer minValue = Network.getByteAsUnsignedInt(Network.networkAddress[3]);
                Integer maxValue = Network.getByteAsUnsignedInt(maxIP[3]);

                if (val < minValue) {
                    Toast.makeText(HostLookupActivity.this, "The minimum value for this field is "+minValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                if (val > maxValue) {
                    Toast.makeText(HostLookupActivity.this, "The maximum value for this field is "+maxValue.toString(), Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Long)previousValue).toString());
                }
                else {
                    setIndexes();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.network_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setHostAddress(int subnet, int host) {
        byte[] asIP = Network.getIPBySubnetHost(subnet, host);
        String[] asStringArray = Network.getStringArray(asIP);
        txt_hostIP_1.setText(asStringArray[0]);
        //Toast.makeText(HostLookupActivity.this, asStringArray[0], Toast.LENGTH_LONG).show();
        txt_hostIP_2.setText(asStringArray[1]);
        //Toast.makeText(HostLookupActivity.this, asStringArray[1], Toast.LENGTH_LONG).show();
        txt_hostIP_3.setText(asStringArray[2]);
        //Toast.makeText(HostLookupActivity.this, asStringArray[2], Toast.LENGTH_LONG).show();
        txt_hostIP_4.setText(asStringArray[3]);
        //Toast.makeText(HostLookupActivity.this, asStringArray[3], Toast.LENGTH_LONG).show();

    }

    public void setIndexes() {
        byte[] ip = Network.IPFromStrings(txt_hostIP_1.getText().toString(), txt_hostIP_2.getText().toString(), txt_hostIP_3.getText().toString(), txt_hostIP_4.getText().toString());
        txt_hostIndex.setText(Network.getHostIndexByIp(ip));
        txt_subnetIndex.setText(Network.getSubnetIndexByIp(ip));
    }

    public void setMaxSubnetIndex() {
        int numSubnets = (int)Math.pow(2, (double)Network.subnetBits);
        this.maxSubnetIndex = numSubnets - 1;
    }

    public void setMaxHostIndex() {
        int numHosts = ((int) Math.pow(2, (double)Network.getHostBits()));
        this.maxHostIndex = numHosts - 1;
    }

    public void setMaxIP() {
        this.maxIP = Network.getIPBySubnetHost(this.maxSubnetIndex, this.maxHostIndex);
    }

}
