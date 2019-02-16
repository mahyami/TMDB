package com.swapcard.tmdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fragments.SeriesListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppController.setCurrentActivity(this);

        setView(savedInstanceState);

    }

    private void setView(Bundle savedInstanceState) {
        manageFragment(SeriesListFragment.class, false, savedInstanceState);
    }


    public Fragment manageFragment(Class fragmentClass, boolean addToBackStack, Bundle data) {
        return manageFragment(fragmentClass, addToBackStack, data, R.id.frameMain);
    }

    public Fragment manageFragment(Class fragmentClass, boolean addToBackStack, Bundle data, boolean reCreate) {
        return manageFragment(fragmentClass, addToBackStack, data, R.id.frameMain, reCreate);
    }

    public Fragment manageFragment(Class fragmentClass, boolean addToBackStack, Bundle data, int frameId) {
        return manageFragment(fragmentClass, addToBackStack, data, frameId, false);
    }

    public Fragment manageFragment(Class fragmentClass, boolean addToBackStack, Bundle data, int frameId, boolean reCreate) {
        String tag = fragmentClass.toString();
        Fragment fr;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(frameId);

        if (currentFragment != null) {
            if (!currentFragment.getClass().toString().equals(tag)) {
                currentFragment.setUserVisibleHint(false);
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null || reCreate)
                    fr = createFragment(tag, data);
            } else
                fr = createFragment(tag, data);

            if (fr == null)
                Toast.makeText(this, R.string.fragment_not_found, Toast.LENGTH_LONG).show();
            else if (addToBackStack)
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frameId, fr, tag).commit();
            else
                getSupportFragmentManager().beginTransaction().replace(frameId, fr, tag).commit();
        } else {
            fr = createFragment(tag, data);
            if (fr == null)
                Toast.makeText(this, R.string.fragment_not_found, Toast.LENGTH_LONG).show();
            else if (addToBackStack)
                getSupportFragmentManager().beginTransaction().addToBackStack(tag).replace(frameId, fr).commit();
            else
                getSupportFragmentManager().beginTransaction().replace(frameId, fr).commit();
        }

        return fr;
    }


    private Fragment createFragment(String tag, Bundle bundle) {
        Fragment fragment = null;

        if (tag.equals(SeriesListFragment.class.toString()))
            fragment = new SeriesListFragment();


        if (fragment != null && bundle != null)
            fragment.setArguments(bundle);

        return fragment;


    }

    @Override
    protected void onResume() {
        super.onResume();
        AppController.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AppController.currentFragment != null)
            AppController.currentFragment.setUserVisibleHint(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
