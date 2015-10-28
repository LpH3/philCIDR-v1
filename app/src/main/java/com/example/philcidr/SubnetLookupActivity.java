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


public class SubnetLookupActivity extends Activity {

    View layout_Main;

    Button bt_subnetSetup;
    Button bt_hostLookup;

    TextView label_netAddress;
    EditText txt_subnetIndex;

    EditText txt_IP_subnetAddr_1;
    EditText txt_IP_subnetAddr_2;
    EditText txt_IP_subnetAddr_3;
    EditText txt_IP_subnetAddr_4;

    EditText txt_IP_firstHost_1;
    EditText txt_IP_firstHost_2;
    EditText txt_IP_firstHost_3;
    EditText txt_IP_firstHost_4;

    EditText txt_IP_lastHost_1;
    EditText txt_IP_lastHost_2;
    EditText txt_IP_lastHost_3;
    EditText txt_IP_lastHost_4;

    EditText txt_IP_broadcast_1;
    EditText txt_IP_broadcast_2;
    EditText txt_IP_broadcast_3;
    EditText txt_IP_broadcast_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet_lookup);

        /** This chunk of code makes the background layout focusable and makes it so that
         *  when you "click" it it sets focus to it and hides the keyboard.
         */
        layout_Main = findViewById(R.id.layout_SubnetLookup);
        final InputMethodManager imm = (InputMethodManager)getSystemService(
                SubnetLookupActivity.this.INPUT_METHOD_SERVICE);
        layout_Main.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        //initialize navigation buttons
        bt_subnetSetup = (Button)findViewById(R.id.btn_page0);
        bt_hostLookup = (Button)findViewById(R.id.btn_page1);

        //set nav button onClickListeners
        bt_subnetSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openActivity = new Intent(SubnetLookupActivity.this, SubnetSetupActivity.class);
                openActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(openActivity);
            }
        });

        //set nav button onClickListeners
        bt_hostLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubnetLookupActivity.this, HostLookupActivity.class));
            }
        });

        //initialize IP Label
        label_netAddress = (TextView)findViewById(R.id.label_netAddress);

        //set value of IP Label
        String [] netAddr = Network.getStringArray(Network.networkAddress);
        label_netAddress.setText(netAddr[0]+"."+netAddr[1]+"."+netAddr[2]+"."+netAddr[3]+" / "+Network.blockBits);

        // initialize Subnet Index Field
        txt_subnetIndex = (EditText)findViewById(R.id.txt_subnetIndex);

        // initialize Subnet Address fields
        txt_IP_subnetAddr_1 = (EditText)findViewById(R.id.txt_IP_subnetAddr_1);
        txt_IP_subnetAddr_2 = (EditText)findViewById(R.id.txt_IP_subnetAddr_2);
        txt_IP_subnetAddr_3 = (EditText)findViewById(R.id.txt_IP_subnetAddr_3);
        txt_IP_subnetAddr_4 = (EditText)findViewById(R.id.txt_IP_subnetAddr_4);

        // initialize First Host IP fields
        txt_IP_firstHost_1 = (EditText)findViewById(R.id.txt_IP_firstHost_1);
        txt_IP_firstHost_2 = (EditText)findViewById(R.id.txt_IP_firstHost_2);
        txt_IP_firstHost_3 = (EditText)findViewById(R.id.txt_IP_firstHost_3);
        txt_IP_firstHost_4 = (EditText)findViewById(R.id.txt_IP_firstHost_4);

        // initialize Last Host IP fields
        txt_IP_lastHost_1 = (EditText)findViewById(R.id.txt_IP_lastHost_1);
        txt_IP_lastHost_2 = (EditText)findViewById(R.id.txt_IP_lastHost_2);
        txt_IP_lastHost_3 = (EditText)findViewById(R.id.txt_IP_lastHost_3);
        txt_IP_lastHost_4 = (EditText)findViewById(R.id.txt_IP_lastHost_4);

        // initialize Broadcast Address fields
        txt_IP_broadcast_1 = (EditText)findViewById(R.id.txt_IP_broadcast_1);
        txt_IP_broadcast_2 = (EditText)findViewById(R.id.txt_IP_broadcast_2);
        txt_IP_broadcast_3 = (EditText)findViewById(R.id.txt_IP_broadcast_3);
        txt_IP_broadcast_4 = (EditText)findViewById(R.id.txt_IP_broadcast_4);

        // give the Subnet Index field a listener
        txt_subnetIndex.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener() {
            @Override
            public void valueChanged(View view) {
                //EditText thisView = (EditText)view;
                //int subnet = Integer.parseInt(thisView.getText().toString());
                int subnet = newValue;
                int numSubnets = (int)Math.pow(2, (double)Network.subnetBits);
                int maxSubnet = numSubnets - 1;

                // deal with input validation
                // yes, I'm ashamed of myself.
                if (subnet == -1) {
                    Toast.makeText(SubnetLookupActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Integer) oldValue).toString());
                }
                else if (subnet < 0) {
                    Toast.makeText(SubnetLookupActivity.this, "Negative subnet. Don't do that!", Toast.LENGTH_LONG).show();
                    ((EditText)view).setText(((Integer) oldValue).toString());
                }
                else if (subnet == 0) {
                    Toast.makeText(SubnetLookupActivity.this, "'Subnet Zero': Use caution.", Toast.LENGTH_LONG).show();
                    setSubnetAddress(subnet);
                    setFirstHostAddress(subnet);
                    setLastHostAddress(subnet);
                    setBroadcastAddress(subnet);
                }
                else if (subnet == maxSubnet) {
                    Toast.makeText(SubnetLookupActivity.this, "'All-Ones Subnet': Be warned.", Toast.LENGTH_LONG).show();
                    setSubnetAddress(subnet);
                    setFirstHostAddress(subnet);
                    setLastHostAddress(subnet);
                    setBroadcastAddress(subnet);
                }
                else if (subnet >= numSubnets) {
                    Toast.makeText(SubnetLookupActivity.this, "Out of Range. Max Index = " + ((Integer)maxSubnet).toString(), Toast.LENGTH_LONG).show();
                    if (oldValue != -1) {
                        ((EditText)view).setText(((Integer) oldValue).toString());
                    }
                    else {
                        ((EditText) view).setText("");
                    }
                }
                else {
                    setSubnetAddress(subnet);
                    setFirstHostAddress(subnet);
                    setLastHostAddress(subnet);
                    setBroadcastAddress(subnet);
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

    public void setSubnetAddress(int subnet) {
        byte[] asIP = Network.getIPBySubnetHost(subnet, 0);
        String[] asStringArray = Network.getStringArray(asIP);
        txt_IP_subnetAddr_1.setText(asStringArray[0]);
        txt_IP_subnetAddr_2.setText(asStringArray[1]);
        txt_IP_subnetAddr_3.setText(asStringArray[2]);
        txt_IP_subnetAddr_4.setText(asStringArray[3]);
    }

    public void setFirstHostAddress(int subnet) {
        byte[] asIP = Network.getIPBySubnetHost(subnet, 1);
        String[] asStringArray = Network.getStringArray(asIP);
        txt_IP_firstHost_1.setText(asStringArray[0]);
        txt_IP_firstHost_2.setText(asStringArray[1]);
        txt_IP_firstHost_3.setText(asStringArray[2]);
        txt_IP_firstHost_4.setText(asStringArray[3]);
    }

    public void setLastHostAddress(int subnet) {
        int host = ((int)Math.pow(2, (double) (Network.IP_ADDRESS_SIZE - Network.maskBits))) - 2;
        byte[] asIP = Network.getIPBySubnetHost(subnet, host);
        String[] asStringArray = Network.getStringArray(asIP);
        txt_IP_lastHost_1.setText(asStringArray[0]);
        txt_IP_lastHost_2.setText(asStringArray[1]);
        txt_IP_lastHost_3.setText(asStringArray[2]);
        txt_IP_lastHost_4.setText(asStringArray[3]);
    }

    public void setBroadcastAddress(int subnet) {
        int host = ((int)Math.pow(2, (double) (Network.IP_ADDRESS_SIZE - Network.maskBits))) - 1;
        byte[] asIP = Network.getIPBySubnetHost(subnet, host);
        String[] asStringArray = Network.getStringArray(asIP);
        txt_IP_broadcast_1.setText(asStringArray[0]);
        txt_IP_broadcast_2.setText(asStringArray[1]);
        txt_IP_broadcast_3.setText(asStringArray[2]);
        txt_IP_broadcast_4.setText(asStringArray[3]);
    }
}
