package com.eltrio723.lipomanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewBatteryActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.eltrio723.lipomanager.MESSAGE";

    Battery battery;

    ListView listView;
    Button buttonCharge, buttonUse, buttonAddNote;
    TextView textId, textCapacity, textDischarge, textCells, textState, textBrand, textCurrentVoltage;
    TextView textConnector, textBuyDate, textLastUsed, textLastCharged, textTimesUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_battery);

        Intent intent = getIntent();
        final int index = intent.getIntExtra(EXTRA_MESSAGE,0);

        battery = BatteryManager.getInstance().getBatteryByIndex(index);

        textId = findViewById(R.id.textView_Id);
        textCapacity = findViewById(R.id.textView_Capacity);
        textDischarge = findViewById(R.id.textView_Discharge);
        textCells = findViewById(R.id.textView_Cells);
        textState = findViewById(R.id.textView_State);
        textBrand = findViewById(R.id.textView_Brand);
        textCurrentVoltage = findViewById(R.id.textView_CurrentVoltage);
        textConnector = findViewById(R.id.textView_Connector);
        textBuyDate = findViewById(R.id.textView_BuyDate);
        textLastUsed = findViewById(R.id.textView_LastUsed);
        textLastCharged = findViewById(R.id.textView_LastCharge);
        textTimesUsed = findViewById(R.id.textView_TimesUsed);

        textId.setText(String.valueOf(battery.getId()));
        textCapacity.setText(String.valueOf(battery.getCapacity()));
        textDischarge.setText(String.valueOf(battery.getDischarge()));
        textCells.setText(String.valueOf(battery.getCells()));
        textState.setText(battery.getState().toString());
        textBrand.setText(battery.getBrand());
        textCurrentVoltage.setText(String.valueOf(battery.getCurrentVoltage()));
        textConnector.setText(battery.getConnector().toString());
        textBuyDate.setText(battery.getBuyDate().toString());
        textLastUsed.setText(battery.getLastUsed().toString());
        textLastCharged.setText(battery.getLastCharged().toString());
        textTimesUsed.setText(String.valueOf(battery.getTimesUsed()));


        listView = findViewById(R.id.listView_Notes);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, battery.getNotes());
        listView.setAdapter(arrayAdapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                callDeleteNoteDialog(pos);

                return true;
            }
        });


        buttonUse = findViewById(R.id.button_Use);
        buttonCharge = findViewById(R.id.button_Charge);
        buttonAddNote = findViewById(R.id.button_AddNote);

        updateButtons();

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewBatteryActivity.this);
                builder.setTitle(R.string.AddNote);

                final EditText input = new EditText(ViewBatteryActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint(R.string.TypeNote);
                builder.setView(input);

                builder.setPositiveButton(R.string.Accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addNote(input.getText().toString());
                        updateData();
                    }
                });

                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        buttonCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(battery.getState()!=State.CHARGED && battery.getState()!=State.CHARGING){
                    BatteryManager.getInstance().startCharge(battery);
                    buttonCharge.setText(R.string.EndCharge);
                    textState.setText(battery.getState().toString());
                }
                else {
                    BatteryManager.getInstance().endCharge(battery);
                    buttonCharge.setText(R.string.Charge);
                    textState.setText(battery.getState().toString());
                }
                updateData();
            }
        });

        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(battery.getState()==State.CHARGED || battery.getState()==State.USED){
                    BatteryManager.getInstance().startUse(battery);
                    buttonUse.setText(R.string.EndUse);
                    textState.setText(battery.getState().toString());
                }
                else {
                    callUsedDialog();
                }
                updateData();
            }
        });

    }


    void addNote(String s){
        battery.addNote(s);
        updateData();
    }

    void updateData(){
        BatteryManager.getInstance().updateBattery(battery);
        updateButtons();
        refresh();
    }

    void refresh(){
        textCurrentVoltage.setText(String.valueOf(battery.getCurrentVoltage()));
        textLastUsed.setText(battery.getLastUsed().toString());
        textLastCharged.setText(battery.getLastCharged().toString());
        textTimesUsed.setText(String.valueOf(battery.getTimesUsed()));
        textState.setText(battery.getState().toString());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, battery.getNotes());
        listView.setAdapter(arrayAdapter);
    }

    void updateButtons(){
        if (battery.getState()==State.CHARGING || battery.getState()==State.DEPLETED){
            buttonUse.setEnabled(false);
        }
        else {
            buttonUse.setEnabled(true);
        }
        if (battery.getState()==State.IN_USE || battery.getState()==State.CHARGED){
            buttonCharge.setEnabled(false);
        }
        else {
            buttonCharge.setEnabled(true);
        }
        if(battery.getState()==State.CHARGING){
            buttonCharge.setText(R.string.EndCharge);
        }
        if(battery.getState()==State.IN_USE){
            buttonUse.setText(R.string.EndUse);
        }
    }

    void callDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewBatteryActivity.this);

        builder.setMessage(R.string.delete_message);
        builder.setTitle(R.string.delete_title);

        builder.setPositiveButton(R.string.Delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BatteryManager.getInstance().deleteBattery(battery);
                finish();
            }
        });

        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void callDeleteNoteDialog(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewBatteryActivity.this);

        builder.setMessage(R.string.delete_note_message);
        builder.setTitle(R.string.delete_note_title);

        final int p = pos;

        builder.setPositiveButton(R.string.Delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                battery.deleteNote(p);
                updateData();
            }
        });

        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void callStoreDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewBatteryActivity.this);

        builder.setMessage(R.string.store_message);
        builder.setTitle(R.string.store_title);

        final EditText input = new EditText(ViewBatteryActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton(R.string.Store, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = input.getText().toString();
                BatteryManager.getInstance().storeBattery(battery,s);
                updateData();
            }
        });

        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void callUsedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewBatteryActivity.this);
        builder.setTitle(R.string.AddNote);

        builder.setMessage(R.string.used_message);
        builder.setTitle(R.string.used_title);

        final EditText input = new EditText(ViewBatteryActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton(R.string.Store, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = input.getText().toString();
                BatteryManager.getInstance().endUse(battery, s);
                buttonUse.setText(R.string.Use);
                textState.setText(battery.getState().toString());
                updateData();
            }
        });

        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_battery_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            callDeleteDialog();
            return true;
        }
        else if(item.getItemId()==R.id.action_store){
            callStoreDialog();
        }
        return super.onOptionsItemSelected(item);
    }

}
