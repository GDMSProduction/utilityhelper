package com.zammle2009wtfgmail.utilityhelper;

import android.app.ActivityManager;
import java.io.IOException;
import java.io.InputStream;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.CpuUsageInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.HardwarePropertiesManager;
import android.os.BatteryManager;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.PrivateKey;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.zammle2009wtfgmail.utilityhelper.SystemUtils.getCPUFrequencyCurrent01;

public class Hardware_Spec extends AppCompatActivity {

    //Text Views
    private TextView CPUBrand;
    private TextView currentCpuFrequency;
    private TextView CPUusage;
    private TextView activeCores;
    private TextView installedRAM;
    private TextView currentRAMUsage;
    private TextView freeRAM;
    private TextView batteryRemained;
    private TextView batteryTemp;
    private TextView batteryVoltage;
    private TextView batteryCharging;


    private int batLevel;
    private int temperature;
    private int voltage;

    //runnable
    private int timer;
    private Handler handler = new Handler();

    //managers
    private BatteryManager bm;
    private ActivityManager.MemoryInfo mI;
    private ActivityManager activityManager;

//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware__spec);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //show battery temperature, getBatterTemp function defined at the bottom
        // TODO: 8/14/2018 get views by ID here
        CPUBrand = (TextView)findViewById(R.id.iDCPUBrand);
        currentCpuFrequency = (TextView)findViewById(R.id.allCPUFrequency);
        CPUusage = (TextView) findViewById(R.id.iDCPUusage);
        activeCores = (TextView)findViewById(R.id.iDCPUCores);
        installedRAM = (TextView)findViewById(R.id.iDtotalRAM);
        currentRAMUsage = (TextView)findViewById(R.id.iDRAMUsage);
        freeRAM = (TextView)findViewById(R.id.iDFreeRAM);
        batteryRemained = (TextView) findViewById(R.id.iDbatteryRemain);
        batteryVoltage = (TextView)findViewById(R.id.iDbatteryVoltage);
        batteryTemp = (TextView) findViewById(R.id.iDbatteryTemp);
        batteryCharging = (TextView) findViewById(R.id.iDbatteryCharging);




        bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        temperature = getBatterTemp(this);
        voltage = getBatVoltage(this);
        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        timer = 0;
        handler.postDelayed(running, 1000);

        // TODO: 8/14/2018 make the ifcharging to a function later  
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

    }

    //accessors
    //get device battery temperature
    public static int getBatterTemp(Context context) {

        Intent getTheBatteryChange = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int temp = getTheBatteryChange.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10;
        return temp;
    }
    //TODO: Add conversion to Fahrenheit here later
    public static int getBatVoltage(Context context) {
        Intent batInfoReceiver = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int voltage = batInfoReceiver.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
        return voltage;
    }
    //get CPU usage
    private float getCPUUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" +");  // Split on one or more spaces

            long idle1 = Long.parseLong(toks[4]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" +");

            long idle2 = Long.parseLong(toks[4]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            return (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
    //get device CPU brand name
    private String ReadCPUinfo()
    {
        ProcessBuilder cmd;
        String result="";

        try{
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1){
                System.out.println(new String(re));
                result = result + new String(re);
            }
            in.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return result;
    }

    //get number of device CPU cores
    private int getNumCores () {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by one or more digits
                if(Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Default to return 1 core
            return 1;
        }
    }
    //get device Installed RAM
    private String getTotalRAM (){
        String RAMinString = "";
        DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");
        mI = new ActivityManager.MemoryInfo();
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mI);
        double totRam = mI.totalMem;
        double mb = totRam / 1048576.0;
        double gb = totRam / 1073741824.0;

        if (gb > 1) {
            RAMinString = twoDecimalFormat.format(gb).concat(" GB");
        } else if (mb > 1) {
            RAMinString = twoDecimalFormat.format(mb).concat(" MB");
        } else {
            RAMinString = twoDecimalFormat.format(totRam).concat(" KB");
        }

       return RAMinString;
    }
    //get available RAM
    private String getAvailableRAM(){
        DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");
        String availableRAM = "";
        double avaiRAM = mI.availMem;

        double mb = avaiRAM / 1048576.0;
        double gb = avaiRAM / 1073741824.0;

        if (gb > 1) {
            availableRAM = twoDecimalFormat.format(gb).concat(" GB");
        } else if (mb > 1) {
            availableRAM = twoDecimalFormat.format(mb).concat(" MB");
        } else {
            availableRAM = twoDecimalFormat.format(avaiRAM).concat(" KB");
        }

        return availableRAM;

    }
    //get device current RAM
    private String getRAMUsage (){
    double totalRAM = mI.totalMem;
    double avaiRAM = mI.availMem;
        DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");
        String RAMUsage =" ";
        double usage = ((totalRAM - avaiRAM)/ totalRAM * 100.0);
        RAMUsage=twoDecimalFormat.format(usage).concat(" %");
        return  RAMUsage;

    }

    //get current CPU frequency








    //display text in the runnable
    private Runnable running = new Runnable() {
        @Override
        public void run()
        {

                if (timer < 90) {

                    //Textview set text here
                    // TODO: 8/14/2018 set CPU brand name here
//                    CPUBrand.setText(ReadCPUinfo().);
                    // TODO: 8/14/2018 set each cores here;
                    try {
                        currentCpuFrequency.setText("Core1 "+ SystemUtils.getCPUFrequencyCurrent01()+"\n" + "Core2 " + SystemUtils.getCPUFrequencyCurrent02()+"\n" +"Core3 "+ SystemUtils.getCPUFrequencyCurrent03()+"\n" +"Core4 "+SystemUtils.getCPUFrequencyCurrent04()+"\n");
                    }
                    catch(Exception e) {

                    }

                    CPUusage.setText(String.valueOf(getCPUUsage()) + " %");
                    activeCores.setText(String.valueOf(getNumCores()));
                    installedRAM.setText(getTotalRAM());
                    currentRAMUsage.setText(getRAMUsage());
                    freeRAM.setText(getAvailableRAM());
                    batteryRemained.setText(String.valueOf(batLevel) + "%");
                    batteryVoltage.setText(String.valueOf(voltage) + " mV");
                    batteryTemp.setText(String.valueOf(temperature) + " ℃");
                    
                    timer += 1;
                    handler.postDelayed(this, 3000);
                }


        }
    };



}
//helper class for reading CPU info
// TODO: 2018/8/23 read CPU info helper class

