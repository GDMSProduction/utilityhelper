package com.zammle2009wtfgmail.utilityhelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.HardwarePropertiesManager;
import android.os.BatteryManager;

import org.w3c.dom.Text;

import java.security.PrivateKey;

public class Hardware_Spec extends AppCompatActivity {

    private TextView batteryRemained;
    private TextView batteryTemp;
    private TextView batteryVoltage;
    private TextView batteryCharging;
    private int batLevel;
    private int temperature;
    private int voltage;
    private BatteryManager bm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware__spec);
        //show battery temperature, getBatterTemp function defined at the bottom
        temperature = getBatterTemp(this);
        batteryTemp = (TextView) findViewById(R.id.batteryTemp);
        batteryTemp.setText(String.valueOf(temperature) + " ℃");

        //show remaining battery capacity
        bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        batteryRemained = (TextView) findViewById(R.id.batteryRemain);
        batteryCharging = (TextView) findViewById(R.id.batteryCharging);
        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        batteryRemained.setText(String.valueOf(batLevel) + "%");

        //checking if the device is currently charging
        //intent filter, filt the wanted action
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getBaseContext().registerReceiver(null, ifilter);
        final float chargingStatus = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        final boolean isCharging = chargingStatus == BatteryManager.BATTERY_STATUS_CHARGING || chargingStatus == BatteryManager.BATTERY_STATUS_FULL;


        if (isCharging) {
            batteryCharging.setText("Charging");
        } else {
            batteryCharging.setText("No");
        }

        //show battery voltage
        batteryVoltage = (TextView)findViewById(R.id.batteryVoltage);
        voltage =getBatVoltage(this);
        batteryVoltage.setText(String.valueOf(voltage)+ " mV");

//    private BroadcastReceiver isCharging = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            batteryCharging.setText("Charging");
//        }
//    };
//    private  BroadcastReceiver notCharging = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            batteryCharging.setText("No");
//        }
//    };
//    = this.getSystemService(HardwarePropertiesManager.class);

//    BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
//    int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY
////        float [] returnedTemp = myHardWareManager.getDeviceTemperatures(HardwarePropertiesManager.DEVICE_TEMPERATURE_BATTERY,HardwarePropertiesManager.TEMPERATURE_CURRENT);
//      batteryTemp = (TextView)findViewById(R.id.batteryTemp);
//        batteryTemp.setText ((int) returnedTemp[0]);
//        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batterStatus = this.registerReceiver(null,iFilter);


    }


    public static int getBatterTemp(Context context) {

        Intent getTheBatteryChange = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int temp = getTheBatteryChange.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10;
        return temp;
    }
    //TODO: Add conversion to Fahrenheit here later

    public static int getBatVoltage (Context context){
        Intent batInfoReceiver = context.registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int voltage = batInfoReceiver.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
        return voltage;
    }
}