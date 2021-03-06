package com.phantomLord.cpufrequtils.app.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.asksven.android.common.privateapiproxies.Wakelock;
import com.phantomLord.cpufrequtils.app.R;
import com.phantomLord.cpufrequtils.app.utils.BatteryStatsUtils;
import com.phantomLord.cpufrequtils.app.utils.SysUtils;

import java.util.ArrayList;

public class CpuWakelocksAdapter extends BaseAdapter {
    ArrayList<Wakelock> partialWakelocks;
    Context context;
    int totaltime;
    LayoutInflater infalter;

    public CpuWakelocksAdapter(Context ctx) {
        this.context = ctx;
        partialWakelocks = BatteryStatsUtils
                .getCpuWakelocksStats(context, true);
        /*
		 * calculate total time
		 */
        totaltime = 0;
        for (Wakelock wl : partialWakelocks) {
            totaltime += wl.getDuration() / 1000;
        }
        infalter = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (partialWakelocks != null)
            return partialWakelocks.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int arg0) {
        return partialWakelocks.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return partialWakelocks.indexOf(arg0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = infalter.inflate(R.layout.cpu_wakelock_row, parent, false);
        TextView wakelockName = (TextView) row
                .findViewById(R.id.cpu_wakelock_name);
        TextView duration = (TextView) row
                .findViewById(R.id.cpu_wakelock_duration);
        TextView wakeCount = (TextView) row
                .findViewById(R.id.cpu_wakelock_count);
        ImageView icon = (ImageView) row.findViewById(R.id.package_icon);
        ProgressBar progress = (ProgressBar) row
                .findViewById(R.id.cpu_wakelock_progress);

        Wakelock mWakelock = partialWakelocks.get(position);
        Drawable drawable = mWakelock.getIcon(context);
        if (drawable != null) {
            icon.setImageDrawable(drawable);
        } else {
            icon.setImageResource(R.drawable.logo);
        }
        wakelockName.setText(mWakelock.getName());
        duration.setText(SysUtils.secToString(mWakelock.getDuration() / 1000));
        wakeCount.setText("x" + mWakelock.getCount()
                + context.getString(R.string.times));
        progress.setMax(totaltime);
        progress.setProgress((int) mWakelock.getDuration() / 1000);
        return row;
    }
}
