package com.phantomLord.cpufrequtils.app.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.phantomLord.cpufrequtils.app.R;
import com.phantomLord.cpufrequtils.app.adapters.NavigationDrawerListAdapter;
import com.phantomLord.cpufrequtils.app.dialogs.RootNotFoundAlertDialog;
import com.phantomLord.cpufrequtils.app.utils.Constants;
import com.phantomLord.cpufrequtils.app.utils.SysUtils;

public class MainActivity extends ActionBarActivity {
    Context themedContext, context;
    boolean isLight;
    String key;

    private DrawerLayout mDrawerLayout;
    private ListView listView;

    private ActionBar actionBar;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences mPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        key = mPrefs.getString("listPref", "Light");
//		this.setTheme(Constants.THEMES_MAP.get(key));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_layout_navbar);
        // BugSenseHandler.initAndStartSession(MainActivity.this, "4cdc31a1");

        themedContext = getSupportActionBar().getThemedContext();

        context = getBaseContext();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        listView.setAdapter(new NavigationDrawerListAdapter(context));
        listView.setOnItemClickListener(new DrawerItemClickListener());
        listView.setCacheColorHint(0);
        listView.setScrollingCacheEnabled(false);
        listView.setScrollContainer(false);
        listView.setFastScrollEnabled(true);
        listView.setSmoothScrollbarEnabled(true);
        listView.setSelection(0);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer_light, R.string.about,
                R.string.about_content);
        mDrawerToggle.syncState();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new CpuFrequencyFragment())
                .commit();

        if (!(SysUtils.isRooted()))
            new RootNotFoundAlertDialog().show(getSupportFragmentManager(),
                    getString(R.string.app_name));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
		 * The action bar home/up action should open or close the drawer.
		 * mDrawerToggle will take care of this.
		 */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Fragment mfragment = null;
            String[] fragmentNames = Constants.mFragmentsArray;
            switch (position) {
                case 0:
                    mfragment = new CpuFrequencyFragment();
                    actionBar.setTitle(fragmentNames[position]);
                    break;
                case 1:
                    mfragment = new TimeInStatesFragment();
                    actionBar.setTitle(fragmentNames[position]);
                    break;
                case 2:
                    mfragment = new IOControlFragment();
                    actionBar.setTitle(fragmentNames[position]);
                    break;
                case 3:
                    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                    mfragment = new WakeLocksDetectorFragment();
                    actionBar.setTitle(fragmentNames[position]);
                    break;
                case 4:
                    //	mfragment = new SettingsFragment();
                    actionBar.setTitle(getString(R.string.action_settings));
                    break;
            }
            if (mfragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, mfragment).commit();
            }
            mDrawerLayout.closeDrawer(listView);
        }

    }

}
