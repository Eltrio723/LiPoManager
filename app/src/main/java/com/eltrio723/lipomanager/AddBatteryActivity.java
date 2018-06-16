package com.eltrio723.lipomanager;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddBatteryActivity extends AppCompatActivity {

    List<State> states;
    List<Connector> connectors;
    Boolean isAdvanced;
    Button buttonCancel, buttonAccept;
    Spinner statesSpinner, connectorsSpinner;
    Switch advanced;
    LinearLayout advancedLayout;
    ArrayAdapter<State> adapterState;
    ArrayAdapter<Connector> adapterConnector;

    EditText textId, textCapacity, textDischarge, textCells, textBrand, textCurrentVoltage;
    EditText textTimesUsed, textBuyDate, textLastUsed, textLastCharged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_battery);


        textId = findViewById(R.id.editText_Id);
        textCapacity = findViewById(R.id.editText_Capacity);
        textDischarge = findViewById(R.id.editText_Discharge);
        textCells = findViewById(R.id.editText_Cells);
        textBrand = findViewById(R.id.editText_Brand);
        textCurrentVoltage = findViewById(R.id.editText_CurrentVoltage);
        textTimesUsed = findViewById(R.id.editText_TimesUsed);
        textBuyDate = findViewById(R.id.editText_BuyDate);
        textLastUsed = findViewById(R.id.editText_LastUsed);
        textLastCharged = findViewById(R.id.editText_LastCharged);



        advancedLayout = findViewById(R.id.linearLayout_Advanced);
        advancedLayout.setVisibility(View.INVISIBLE);
        isAdvanced = false;
        advanced = (Switch) findViewById(R.id.switch_Advanced);
        advanced.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    advancedLayout.setVisibility(View.VISIBLE);
                    isAdvanced = true;
                }
                else {
                    advancedLayout.setVisibility(View.INVISIBLE);
                    isAdvanced = false;
                }
            }
        });

        states = Arrays.asList(State.values());
        connectors = Arrays.asList(Connector.values());

        statesSpinner = findViewById(R.id.spinner_State);
        connectorsSpinner = findViewById(R.id.spinner_Connector);

        adapterState = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, states);
        statesSpinner.setAdapter(adapterState);
        adapterConnector = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, connectors);
        connectorsSpinner.setAdapter(adapterConnector);

        textBuyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(textBuyDate);
            }
        });

        textLastUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(textLastUsed);
            }
        });

        textLastCharged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(textLastCharged);
            }
        });



        buttonAccept = findViewById(R.id.button_Accept);
        buttonCancel = findViewById(R.id.button_Cancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAdvanced){
                    Battery battery = createCompleteBattery();

                    if(battery!=null){
                        BatteryManager.getInstance().addBattery(battery);
                        finish();
                    }

                }
                else {
                    Battery battery = createBasicBattery();
                    if(battery!=null){
                        BatteryManager.getInstance().addBattery(battery);
                        finish();
                    }

                }
            }
        });


    }


    private void showDatePickerDialog(final EditText et){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + " / " + twoDigits(month+1) + " / " + year;
                et.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private String twoDigits(int n){
        return (n<9) ? ("0"+n) : String.valueOf(n);
    }


    private Battery createBasicBattery(){

        if(textId.getText().toString().isEmpty() || textCapacity.getText().toString().isEmpty() ||
                textDischarge.getText().toString().isEmpty() || textCells.getText().toString().isEmpty()){
            return null;
        }

        int id = Integer.parseInt(textId.getText().toString());
        int capacity = Integer.parseInt(textCapacity.getText().toString());
        int discharge = Integer.parseInt(textDischarge.getText().toString());
        int cells = Integer.parseInt(textCells.getText().toString());

        return new Battery(id, capacity, discharge, cells);
    }

    private Battery createCompleteBattery(){

        if(textId.getText().toString().isEmpty() || textCapacity.getText().toString().isEmpty() ||
                textDischarge.getText().toString().isEmpty() || textCells.getText().toString().isEmpty() ||
                textBrand.getText().toString().isEmpty() || textCurrentVoltage.getText().toString().isEmpty()
                || textTimesUsed.getText().toString().isEmpty()){
            return null;
        }

        int id = Integer.parseInt(textId.getText().toString());
        int capacity = Integer.parseInt(textCapacity.getText().toString());
        int discharge = Integer.parseInt(textDischarge.getText().toString());
        int cells = Integer.parseInt(textCells.getText().toString());

        String brand = textBrand.getText().toString();
        double currentVoltage = Double.parseDouble(textCurrentVoltage.getText().toString());
        int timesUsed = Integer.parseInt(textTimesUsed.getText().toString());

        State state = (State) statesSpinner.getSelectedItem();
        Connector connector = (Connector) connectorsSpinner.getSelectedItem();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date buyDate, lastUsed, lastCharged;
        try {
            buyDate = formatter.parse(textBuyDate.getText().toString());
        } catch (ParseException e){
            e.printStackTrace();
            buyDate = Calendar.getInstance().getTime();
        }
        try {
            lastUsed = formatter.parse(textLastUsed.getText().toString());
        } catch (ParseException e){
            e.printStackTrace();
            lastUsed = Calendar.getInstance().getTime();
        }
        try {
            lastCharged = formatter.parse(textLastCharged.getText().toString());
        } catch (ParseException e){
            e.printStackTrace();
            lastCharged = Calendar.getInstance().getTime();
        }


        Calendar.getInstance().getTime();


        return new Battery(id, capacity, discharge, cells, currentVoltage, timesUsed, brand, state, connector, buyDate, lastUsed, lastCharged);
    }

}
