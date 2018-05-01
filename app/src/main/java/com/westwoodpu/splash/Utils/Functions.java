package com.westwoodpu.splash.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.westwoodpu.splash.R;

/**
 * Created by stevepu on 4/29/18.
 */

public class Functions {
    public static void changeMainFragement(FragmentActivity fragmentActivity, Fragment fragment) {
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
}
