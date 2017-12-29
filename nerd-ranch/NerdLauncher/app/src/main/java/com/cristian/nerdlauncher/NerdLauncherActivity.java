package com.cristian.nerdlauncher;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    Fragment createFragment() {
        return NerdLauncherFragment.newInstance();
    }


}
