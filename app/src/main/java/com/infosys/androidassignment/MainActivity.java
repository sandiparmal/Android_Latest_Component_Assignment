package com.infosys.androidassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.infosys.androidassignment.fragments.FactsFragment;
import com.infosys.androidassignment.mvp.base.BaseActivity;

public class MainActivity extends BaseActivity {


    /**
     * Layout resource to be inflated
     *
     * @return layout resource
     */
    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    /**
     * Initializations
     *
     * @param savedState Bundle
     */
    @Override
    protected void init(Bundle savedState) {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new FactsFragment();

            // adding fragments
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
