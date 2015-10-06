package com.example.philcidr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

import static android.text.TextUtils.substring;


public class SubnetSetupActivity extends Activity {

    View layout_Main;

    TextView label_netAddress;

    String[] array_numSubnets;
    String[] array_numHosts;
    String[] array_subnetBits;
    String[] array_hostBits;

    ArrayAdapter<String> adapter_numSubnets;
    ArrayAdapter<String> adapter_numHosts;
    ArrayAdapter<String> adapter_subnetBits;
    ArrayAdapter<String> adapter_hostBits;

    TextView txt_hostIPbin_1;
    EditText txt_inputIP_1;
    TextView txt_hostIPbin_2;
    EditText txt_inputIP_2;
    TextView txt_hostIPbin_3;
    EditText txt_inputIP_3;
    TextView txt_hostIPbin_4;
    EditText txt_inputIP_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet_setup);

        /** This chunk of code makes the background layout focusable and makes it so that
         *  when you "click" it it sets focus to it and hides the keyboard.
         */
        layout_Main = findViewById(R.id.layout_SubnetSetup);
        final InputMethodManager imm = (InputMethodManager)getSystemService(
                SubnetSetupActivity.this.INPUT_METHOD_SERVICE);
        layout_Main.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        Button bt_hostLookup = (Button)findViewById(R.id.btn_page1);
        Button bt_subnetLookup = (Button)findViewById(R.id.btn_page2);

        label_netAddress = (TextView)findViewById(R.id.label_netAddress);

        final Spinner spinner_numSubnets = (Spinner)findViewById(R.id.spinner_numSubnets);
        spinner_numSubnets.setTag(Network.NUM_SUBNETS);
        final Spinner spinner_numHosts = (Spinner)findViewById(R.id.spinner_numHosts);
        spinner_numSubnets.setTag(Network.HOSTS_PER_SUBNET);
        final Spinner spinner_subnetBits = (Spinner)findViewById(R.id.spinner_subnetBits);
        spinner_numSubnets.setTag(Network.SUBNET_BITS);
        final Spinner spinner_hostBits = (Spinner)findViewById(R.id.spinner_hostBits);
        spinner_numSubnets.setTag(Network.HOST_BITS);

        txt_hostIPbin_1 = (TextView)findViewById(R.id.txt_hostIPbin_1);
        txt_inputIP_1 = (EditText)findViewById(R.id.txt_inputIP_1);
        txt_hostIPbin_2 = (TextView)findViewById(R.id.txt_hostIPbin_2);
        txt_inputIP_2 = (EditText)findViewById(R.id.txt_inputIP_2);
        txt_hostIPbin_3 = (TextView)findViewById(R.id.txt_hostIPbin_3);
        txt_inputIP_3 = (EditText)findViewById(R.id.txt_inputIP_3);
        txt_hostIPbin_4 = (TextView)findViewById(R.id.txt_hostIPbin_4);
        txt_inputIP_4 = (EditText)findViewById(R.id.txt_inputIP_4);

        UpdateSubnetMask();


        String [] netAddr = Network.byteArrayToStringArray(Network.networkAddress);
        label_netAddress.setText(netAddr[0]+"."+netAddr[1]+"."+netAddr[2]+"."+netAddr[3]+" / "+Network.blockBits);


        array_numSubnets = MakeArray(Network.NUM_SUBNETS);
        array_numHosts = MakeArray(Network.HOSTS_PER_SUBNET);
        array_subnetBits = MakeArray(Network.SUBNET_BITS);
        array_hostBits = MakeArray(Network.HOST_BITS);

        //Set up adapter for Number of Subnets
        adapter_numSubnets = SetUpAdapter(array_numSubnets);
        spinner_numSubnets.setAdapter(adapter_numSubnets);

        // Set up adapter for Number of Hosts
        adapter_numHosts = SetUpAdapter(array_numHosts);
        spinner_numHosts.setAdapter(adapter_numHosts);

        // Set up adapter for # Subnet Bits
        adapter_subnetBits = SetUpAdapter(array_subnetBits);
        spinner_subnetBits.setAdapter(adapter_subnetBits);

        // Set up adapter for # Host Bits
        adapter_hostBits = SetUpAdapter(array_hostBits);
        spinner_hostBits.setAdapter(adapter_hostBits);

        spinner_hostBits.setOnItemSelectedListener(new MySpinnerOnItemSelectedListener(){
            @Override
            public void valueChanged(AdapterView<?> parent, View view, int position, long id){
                Network.setMaskBits(Network.theArray[position][0]);
                spinner_numHosts.setSelection(position);
                spinner_numSubnets.setSelection(position);
                spinner_subnetBits.setSelection(position);
                UpdateSubnetMask();
                //Toast.makeText(SubnetSetupActivity.this, "hostBits", Toast.LENGTH_SHORT).show();
            }
        });
        spinner_numHosts.setOnItemSelectedListener(new MySpinnerOnItemSelectedListener(){
            @Override
            public void valueChanged(AdapterView<?> parent, View view, int position, long id){
                Network.setMaskBits(Network.theArray[position][0]);
                spinner_hostBits.setSelection(position);
                spinner_numSubnets.setSelection(position);
                spinner_subnetBits.setSelection(position);
                UpdateSubnetMask();
                //Toast.makeText(SubnetSetupActivity.this, "numHosts", Toast.LENGTH_SHORT).show();
            }
        });
        spinner_numSubnets.setOnItemSelectedListener(new MySpinnerOnItemSelectedListener(){
            @Override
            public void valueChanged(AdapterView<?> parent, View view, int position, long id){
                Network.setMaskBits(Network.theArray[position][0]);
                spinner_hostBits.setSelection(position);
                spinner_numHosts.setSelection(position);
                spinner_subnetBits.setSelection(position);
                UpdateSubnetMask();
                //Toast.makeText(SubnetSetupActivity.this, "numSubnets", Toast.LENGTH_SHORT).show();
            }
        });
        spinner_subnetBits.setOnItemSelectedListener(new MySpinnerOnItemSelectedListener(){
            @Override
            public void valueChanged(AdapterView<?> parent, View view, int position, long id){
                Network.setMaskBits(Network.theArray[position][0]);
                spinner_hostBits.setSelection(position);
                spinner_numHosts.setSelection(position);
                spinner_numSubnets.setSelection(position);
                UpdateSubnetMask();
                //Toast.makeText(SubnetSetupActivity.this, "subnetBits", Toast.LENGTH_SHORT).show();
            }
        });


        bt_hostLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubnetSetupActivity.this, HostLookupActivity.class));
            }
        });

        bt_subnetLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubnetSetupActivity.this, SubnetLookupActivity.class));
            }
        });


    }

    public void UpdateSubnetMask() {

        /*Integer unsignedIP_1 = (((Byte)Network.subnetMask[0]).intValue() & 0x000000ff);
        Integer unsignedIP_2 = (((Byte)Network.subnetMask[1]).intValue() & 0x000000ff);
        Integer unsignedIP_3 = (((Byte)Network.subnetMask[2]).intValue() & 0x000000ff);
        Integer unsignedIP_4 = (((Byte)Network.subnetMask[3]).intValue() & 0x000000ff);
        txt_inputIP_1.setText(unsignedIP_1.toString());
        txt_inputIP_2.setText(unsignedIP_2.toString());
        txt_inputIP_3.setText(unsignedIP_3.toString());
        txt_inputIP_4.setText(unsignedIP_4.toString());*/

        String[] stringArray = Network.byteArrayToStringArray(Network.subnetMask);
        txt_inputIP_1.setText(stringArray[0]);
        txt_inputIP_2.setText(stringArray[1]);
        txt_inputIP_3.setText(stringArray[2]);
        txt_inputIP_4.setText(stringArray[3]);

        /*txt_hostIPbin_1.setText((Integer.toBinaryString(Network.subnetMask[0])).substring(8));
        txt_hostIPbin_2.setText((Integer.toBinaryString(Network.subnetMask[1])).substring(8));
        txt_hostIPbin_3.setText((Integer.toBinaryString(Network.subnetMask[2])).substring(8));
        txt_hostIPbin_4.setText((Integer.toBinaryString(Network.subnetMask[3])).substring(8));*/

        /*
        String binary1 = Integer.toBinaryString(Network.subnetMask[0]);
        String binary2 = Integer.toBinaryString(Network.subnetMask[1]);
        String binary3 = Integer.toBinaryString(Network.subnetMask[2]);
        String binary4 = Integer.toBinaryString(Network.subnetMask[3]); */

        /*txt_hostIPbin_2.setText(binary2.substring(24));
        txt_hostIPbin_3.setText(binary3.substring(24));
        txt_hostIPbin_4.setText(binary4.substring(24));*/
        /*txt_hostIPbin_1.setText(Integer.toBinaryString(Network.subnetMask[0]));
        txt_hostIPbin_2.setText(Integer.toBinaryString(Network.subnetMask[1]));
        txt_hostIPbin_3.setText(Integer.toBinaryString(Network.subnetMask[2]));
        txt_hostIPbin_4.setText(Integer.toBinaryString(Network.subnetMask[3]));*/

    }

    public ArrayAdapter<String> SetUpAdapter(String[] array){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }


    public String[] MakeArray(int x) {
        String[] returnArray = new String[Network.theArray.length];
        try {
            int count = 0;
            while (count < Network.theArray.length) {
                returnArray[count] = Integer.toString(Network.theArray[count][x]);
                count++;
            }
        }
        catch(Exception e){
            Log.d("error", e.getMessage());
            Toast.makeText(SubnetSetupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return returnArray;
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
}
