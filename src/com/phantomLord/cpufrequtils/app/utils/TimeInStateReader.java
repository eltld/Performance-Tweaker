package com.phantomLord.cpufrequtils.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.os.SystemClock;

public class TimeInStateReader implements Constants {
	private ArrayList<CpuState> states;

	private long totaltime = 0;

	public TimeInStateReader() {
		states = new ArrayList<CpuState>();
	}

	public ArrayList<CpuState> getCpuStateTime(boolean withDeepSleep) {
		states.clear();
		BufferedReader bufferedReader;
		Process process = null;
		File statsFile = new File(time_in_states);
		if (statsFile.exists()) {
			if (statsFile.canRead()) {
				String line;
				try {
					process = Runtime.getRuntime()
							.exec("cat " + time_in_states);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				bufferedReader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));

				try {
					while ((line = bufferedReader.readLine()) != null) {
						String entries[] = line.split(" ");
						long time = Long.parseLong(entries[1]) / 100;
						states.add(new CpuState(Integer.parseInt(entries[0]),
								time));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		/*
		 * calculate deep sleep time
		 */

		if (withDeepSleep) {
			long deepSleepTime = (SystemClock.elapsedRealtime() - SystemClock
					.uptimeMillis());
			if (deepSleepTime > 0)
				states.add(new CpuState(0, deepSleepTime/1000));
		}
		return states;
	}

	public long getTotalTimeInState() {
		totaltime = 0;
		for (CpuState state : states) {
			totaltime += state.getTime();

		}
		return totaltime;
	}

	public void clearCpuStates() {
		states.clear();
		totaltime = 0;
	}
}
