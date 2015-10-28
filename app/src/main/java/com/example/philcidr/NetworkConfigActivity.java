package com.example.philcidr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;


public class NetworkConfigActivity extends Activity {

    View layout_Main;
    Integer classfulSelection = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_config);

        /** This chunk of code makes the background layout focusable and makes it so that
         *  when you "click" it it sets focus to it and hides the keyboard.
         */
        layout_Main = findViewById(R.id.layout_NetworkConfig);
        final InputMethodManager imm = (InputMethodManager)getSystemService(
                NetworkConfigActivity.this.INPUT_METHOD_SERVICE);
        layout_Main.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        final Button bt_startCalc = (Button)findViewById(R.id.btn_GO);
        final Switch sw_addrType = (Switch)findViewById(R.id.switch_addrType);
        final EditText txt_inputIP_1 = (EditText)findViewById(R.id.txt_inputIP_1);
        final EditText txt_inputIP_2 = (EditText)findViewById(R.id.txt_inputIP_2);
        final EditText txt_inputIP_3 = (EditText)findViewById(R.id.txt_inputIP_3);
        final EditText txt_inputIP_4 = (EditText)findViewById(R.id.txt_inputIP_4);
        final RadioButton rad_classA = (RadioButton)findViewById(R.id.radio_classA);
        final RadioButton rad_classB = (RadioButton)findViewById(R.id.radio_classB);
        final RadioButton rad_classC = (RadioButton)findViewById(R.id.radio_classC);
        final EditText txt_cidrBlockBits = (EditText)findViewById(R.id.txt_cidrBlockBits);
        final View layout_cidrInputContainer = (View)findViewById(R.id.layout_cidrInputContainer);
        final View layout_classInputContainer = (View)findViewById(R.id.layout_classInputContainer);

        int ipByte1; //= Integer.parseInt(txt_inputIP_1.getText().toString());
        int ipByte2; //= Integer.parseInt(txt_inputIP_2.getText().toString());
        int ipByte3; //= Integer.parseInt(txt_inputIP_3.getText().toString());
        int ipByte4; //= Integer.parseInt(txt_inputIP_4.getText().toString());

        rad_classA.setTag("A");
        rad_classA.setTag("B");
        rad_classA.setTag("C");



        /**
         * This is my event listener for the switch. It registers whether you checked it to
         * on or off and reacts accordingly, by making the associated inputs for CIDR/Classful
         * visible or hidden.
         */
        sw_addrType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    layout_cidrInputContainer.setVisibility(View.GONE);
                    layout_classInputContainer.setVisibility(View.VISIBLE);
                }
                else {
                    layout_cidrInputContainer.setVisibility(View.VISIBLE);
                    layout_classInputContainer.setVisibility(View.GONE);
                }
            }
        });

        /**
         * This is the listener for the radio buttons that allow for the selection of the various
         * classful network address types (A,B,C being the primary ones included here.
         * TODO: Add a setting in options to allow for more classful subnet types (D, custom)
         */
        RadioButton.OnCheckedChangeListener myRadioButtonOnCheckedChangeListener = new RadioButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    /**
                     * These next two lines of code, in effect, read the first character of the text
                     * of whichever radio button was selected, so that this can be used for a switch
                     * statement.
                     * TODO: Re-consider somehow tagging the button elements themselves.
                     */
                    String theButton = compoundButton.getText().toString();
                    char buttonTag = theButton.charAt(0);
                    switch (buttonTag) {
                        case 'A':
                            //code to execute if "classA" is selected
                            //Toast.makeText(NetworkConfigActivity.this, "classA selected", Toast.LENGTH_SHORT).show();
                            classfulSelection = 8;
                            break;
                        case 'B':
                            //code to execute if "classB" is selected
                            //Toast.makeText(NetworkConfigActivity.this, "classB selected", Toast.LENGTH_SHORT).show();
                            classfulSelection = 16;
                            break;
                        case 'C':
                            //code to execute if "classC" is selected
                            //Toast.makeText(NetworkConfigActivity.this, "classC selected", Toast.LENGTH_SHORT).show();
                            classfulSelection = 24;
                            break;
                        default:
                            Toast.makeText(NetworkConfigActivity.this, "something went weird, selecting Class C", Toast.LENGTH_SHORT).show();
                            rad_classC.setSelected(true);
                            classfulSelection = 24;
                    }
                }
            }
        };

        // we can give all the relevant EditText views the same listener
        txt_inputIP_1.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                if ((newValue < 1 )|(newValue > 254)) {
                    Toast.makeText(NetworkConfigActivity.this, "Out of Range", Toast.LENGTH_SHORT).show();
                    ((EditText)view).setText(oldString);
                }
            }
        });
        txt_inputIP_2.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener() {
            @Override
            public void valueChanged(View view) {
                if ((newValue < 0) | (newValue > 254)) {
                    Toast.makeText(NetworkConfigActivity.this, "Out of Range", Toast.LENGTH_SHORT).show();
                    ((EditText) view).setText(oldString);
                }
            }
        });
        txt_inputIP_3.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                if ((newValue < 0 )|(newValue > 254)) {
                    Toast.makeText(NetworkConfigActivity.this, "Out of Range", Toast.LENGTH_SHORT).show();
                    ((EditText) view).setText(oldString);
                }

            }
        });
        txt_inputIP_4.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view){
                if ((newValue < 0 )|(newValue > 254)) {
                    Toast.makeText(NetworkConfigActivity.this, "Out of Range", Toast.LENGTH_SHORT).show();
                    ((EditText) view).setText(oldString);
                }

            }
        });
        txt_cidrBlockBits.setOnFocusChangeListener(new MyIntEditTextOnFocusChangeListener(){
            @Override
            public void valueChanged(View view) {
                if ((newValue < 0) | (newValue > 32)) {
                    Toast.makeText(NetworkConfigActivity.this, "Out of Range", Toast.LENGTH_SHORT).show();
                    ((EditText) view).setText(oldString);
                }
                else if (newValue == 32) {

                }

            }
        });

        rad_classA.setOnCheckedChangeListener(myRadioButtonOnCheckedChangeListener);
        rad_classB.setOnCheckedChangeListener(myRadioButtonOnCheckedChangeListener);
        rad_classC.setOnCheckedChangeListener(myRadioButtonOnCheckedChangeListener);

        /**
         * This is my OnClickListener for the "GO" button, that you will press when all your
         * network stuff is taken care of.
         */
        bt_startCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean noGo = false;
                int index = 0;
                int blockBits;

                if (!(sw_addrType.isChecked())) {
                    if (txt_cidrBlockBits.getText().toString().equals("")) {
                        Toast.makeText(NetworkConfigActivity.this, "Block bits value cannot be blank", Toast.LENGTH_LONG).show();
                        noGo = true;
                    }
                }
                else {
                    txt_cidrBlockBits.setText(classfulSelection.toString());
                }

                // Put IP Address Elements in an array;
                String[] ipArray = new String[] {
                        txt_inputIP_1.getText().toString(),
                        txt_inputIP_2.getText().toString(),
                        txt_inputIP_3.getText().toString(),
                        txt_inputIP_4.getText().toString()
                };
                //(inefficiently) check out all the values to make sure they're parsable (present)
                while (index < ipArray.length) {
                    if (ipArray[index].equals("")) {
                        Toast.makeText(NetworkConfigActivity.this, "All address fields must have value", Toast.LENGTH_LONG).show();
                        noGo = true;
                        index = ipArray.length;
                    }
                    index ++;
                }

                if (!noGo) {
                    Network.initNetwork(Integer.parseInt(txt_cidrBlockBits.getText().toString()));
                    Network.setSubnetMask(Network.netMask);
                    Network.setNetworkAddress(ipArray[0], ipArray[1], ipArray[2], ipArray[3]);
                    startActivity(new Intent(NetworkConfigActivity.this, SubnetSetupActivity.class));
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

    //TODO: figure out the right way to deal with pausing this activity.
    void KillActivity() {
        this.finish();
    }
}
