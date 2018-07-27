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
    private BatteryManager bm;
    private HardwarePropertiesManager myHardWareManager;
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
//    int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware__spec);
        //instantiate the BatterManager
        bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        batteryRemained = (TextView) findViewById(R.id.batteryRemain);
        batteryCharging = (TextView)findViewById(R.id.batteryCharging);

        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        //intialize an IntentFilter
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getBaseContext().registerReceiver(null,ifilter);
        final float chargingStatus = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        final boolean isCharging = chargingStatus== BatteryManager.BATTERY_STATUS_CHARGING|| chargingStatus==BatteryManager.BATTERY_STATUS_FULL;
        String printBatteryLevel = Integer.toString(batLevel);
        batteryRemained.setText(String.valueOf(batLevel) + "%");

        if (isCharging){
            batteryCharging.setText("Charging");
        }
        else{
            batteryCharging.setText("No");
        }

////        float [] returnedTemp = myHardWareManager.getDeviceTemperatures(HardwarePropertiesManager.DEVICE_TEMPERATURE_BATTERY,HardwarePropertiesManager.TEMPERATURE_CURRENT);
//      batteryTemp = (TextView)findViewById(R.id.batteryTemp);
//        batteryTemp.setText ((int) returnedTemp[0]);
//        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batterStatus = this.registerReceiver(null,iFilter);


    }
}
