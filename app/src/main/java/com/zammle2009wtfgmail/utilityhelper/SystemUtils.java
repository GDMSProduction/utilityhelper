package com.zammle2009wtfgmail.utilityhelper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Scanner;

public class SystemUtils {

    static DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");




        public static String getCPUFrequencyCurrent01() throws Exception {

            double cpuint = com.zammle2009wtfgmail.utilityhelper.SystemUtils.readSystemFileAsInt("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            String cpu = twoDecimalFormat.format(cpuint/1048576.0).concat(" GHz");
            return  cpu;
        }
        public static String getCPUFrequencyCurrent02() throws Exception {
            double cpuint = com.zammle2009wtfgmail.utilityhelper.SystemUtils.readSystemFileAsInt("/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq");
            String cpu = twoDecimalFormat.format(cpuint/1048576.0).concat(" GHz");
            return  cpu;
        }
        public static String getCPUFrequencyCurrent03() throws Exception {
            double cpuint = com.zammle2009wtfgmail.utilityhelper.SystemUtils.readSystemFileAsInt("/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq");
            String cpu = twoDecimalFormat.format(cpuint/1048576.0).concat(" GHz");
            return  cpu;
        }

    public static String getCPUFrequencyCurrent04() throws Exception {
        double cpuint = com.zammle2009wtfgmail.utilityhelper.SystemUtils.readSystemFileAsInt("/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq");
        String cpu = twoDecimalFormat.format(cpuint/1048576.0).concat(" GHz");
        return  cpu;
    }
        public static int readSystemFileAsInt(final String pSystemFile) throws Exception {
            InputStream in = null;
            try {
                final Process process = new ProcessBuilder(new String[] { "/system/bin/cat", pSystemFile }).start();

                in = process.getInputStream();
                final String content = readFully(in);
                return Integer.parseInt(content);
            } catch (final Exception e) {
                throw new Exception(e);
            }
        }

        public static final String readFully(final InputStream pInputStream) throws IOException {
            final StringBuilder sb = new StringBuilder();
            final Scanner sc = new Scanner(pInputStream);
            while(sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            return sb.toString();
        }
}
