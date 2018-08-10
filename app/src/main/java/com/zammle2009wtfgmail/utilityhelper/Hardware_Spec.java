package com.zammle2009wtfgmail.utilityhelper;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.io.RandomAccessFile;
import java.security.PrivateKey;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Hardware_Spec extends AppCompatActivity {

    private TextView CPUName;
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

    //Jake's approach
    private int JakeNum = 0;
    private Handler handler = new Handler();

    private BatteryManager bm;


    private ActivityManager.MemoryInfo mI;
    private ActivityManager activityManager;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware__spec);
        //show battery temperature, getBatterTemp function defined at the bottom
        temperature = getBatterTemp(this);
        batteryTemp = (TextView) findViewById(R.id.batteryTemp);
        batteryTemp.setText(String.valueOf(temperature) + " ℃");
       //Jake's approach

        JakeNum = 0;
        handler.postDelayed(running, 1000);

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
        batteryVoltage = (TextView) findViewById(R.id.batteryVoltage);
        voltage = getBatVoltage(this);
        batteryVoltage.setText(String.valueOf(voltage) + " mV");

        //show chipset brand model
        CPUName = (TextView)findViewById(R.id.Chip);
//        CPUName.setText(getCPUInfo());
        //show current CPU currency of each thread

        //show current CPU usage in %
        CPUusage = (TextView) findViewById(R.id.CPUusage);
        CPUusage.setText(String.valueOf(readUsage()) + " %");

        //show number of active CPU cores
        activeCores = (TextView)findViewById(R.id.activeCores);
        int toTalNumberofCores = getNumCores();
        activeCores.setText(String.valueOf(getNumCores()));

        //show total device installed RAM
        installedRAM = (TextView)findViewById(R.id.RamInstalled);
        //show current RAM usage in percentage
        currentRAMUsage = (TextView)findViewById(R.id.currentRAMusagePercentage);

        //moved below to runnable object
//        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(mi);
//        double  availableGigs = (mi.availMem / 0x100000L)/1024;// mi.availMem might be an integer, trying to figure out how to get a double
//        short percentAvail = (short) ((1-mi.availMem / (double)mi.totalMem) * 100.0);
//        currentRAMUsage.setText(String.valueOf(percentAvail)+" %");

        //show current available RAM in Gygabytes
        freeRAM = (TextView)findViewById(R.id.freeRAMinGig);
//        freeRAM.setText(String.valueOf(availableGigs)+" GB");

    }


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

    //function to get CPU usage
    private float readUsage() {
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
    //function returns  the name of the device CPU
    public Map<String, String> getCPUInfo() throws IOException {

        Map<String, String> output = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));

        String str;

        while ((str = br.readLine()) != null) {

            String[] data = str.split(":");

            if (data.length > 1) {

                String key = data[0].trim().replace(" ", "_");
                if (key.equals("model_name")) key = "cpu_model";

                String value = data[1].trim();

                if (key.equals("cpu_model"))
                    value = value.replaceAll("\\s+", " ");

                output.put(key, value);

            }

        }

        br.close();

        return output;

    }
    //function returns the number of CPU cores of the user device
    private int getNumCores() {
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
    //function returns the device Installed RAM
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
    //function to get available RAM
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

    private String getRAMUsage (){
        DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");
        String RAMUsage =" ";
        double usage = (mI.availMem/mI.totalMem * 100.0);
        RAMUsage=twoDecimalFormat.format(usage).concat(" %");
        return  RAMUsage;

    }
//    double  percentAvail = (short) ((1-mI.availMem / (double)mI.totalMem) * 100.0);

    private Runnable running = new Runnable() {
        @Override
        public void run()
        {

                if (JakeNum < 120) {
                    // Textview set text here



                    //Text view set texts
                    installedRAM.setText(getTotalRAM());
                    currentRAMUsage.setText(getRAMUsage());
                    freeRAM.setText(getAvailableRAM());



                    //


                    JakeNum += 1;
                    handler.postDelayed(this, 1000);
                }


        }
    };



}