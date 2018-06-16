package com.eltrio723.lipomanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BatteryArrayAdapter extends ArrayAdapter<Battery> {

    private Context context;
    private List<Battery> batteries;

    public BatteryArrayAdapter(Context context, int resource, ArrayList<Battery> batteries){
        super(context,resource,batteries);

        this.context = context;
        this.batteries = batteries;
    }



    public View getView(int position, View convertView, ViewGroup parent){
        Battery battery = batteries.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.battery_item,null);

        TextView id = (TextView) view.findViewById(R.id.textView_Id);
        TextView state = (TextView) view.findViewById(R.id.textView_State);
        TextView capacity = (TextView) view.findViewById(R.id.textView_Capacity);
        TextView discharge = (TextView) view.findViewById(R.id.textView_Discharge);
        TextView voltage = (TextView) view.findViewById(R.id.textView_Voltage);
        TextView cells = (TextView) view.findViewById(R.id.textView_Cells);

        id.setText(String.valueOf(battery.getId()));
        state.setText(battery.getState().toString());
        capacity.setText(String.valueOf(battery.getCapacity()));
        discharge.setText(String.valueOf(battery.getDischarge()));
        voltage.setText(String.valueOf(battery.getCurrentVoltage()));
        cells.setText(String.valueOf(battery.getCells()));

        return view;

    }




}
