package com.phantomLord.cpufrequtils.app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.phantomLord.cpufrequtils.app.R;

public class CpuFrequencyScalingNotSupportedDialog extends
        DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher)
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.doesnt_support_cpufs))
                .setPositiveButton(getString(R.string.continu),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        })
                .setNegativeButton(getString(R.string.exit),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                getActivity().finish();
                            }
                        }).create();
    }

}
