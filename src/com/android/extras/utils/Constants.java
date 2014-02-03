package com.android.extras.utils;

public interface Constants {
	public final static String cpufreq_sys_dir = "/sys/devices/system/cpu/cpu0/cpufreq/";
	public final static String scaling_min_freq = cpufreq_sys_dir + "scaling_min_freq";
	public final static String cpuinfo_min_freq = cpufreq_sys_dir + "cpuinfo_min_freq";
	public final static String scaling_max_freq = cpufreq_sys_dir + "scaling_max_freq";
	public final static String cpuinfo_max_freq = cpufreq_sys_dir + "cpuinfo_max_freq";
	public final static String scaling_cur_freq = cpufreq_sys_dir + "scaling_cur_freq";
	public final static String cpuinfo_cur_freq = cpufreq_sys_dir + "cpuinfo_cur_freq";
	public final static String scaling_governor = cpufreq_sys_dir + "scaling_governor";
	public final static String scaling_available_freq = cpufreq_sys_dir + "scaling_available_frequencies";
	public final static String scaling_available_governors = cpufreq_sys_dir + "scaling_available_governors";
	public final static String tag="Cpu Freq Utilities";
}
