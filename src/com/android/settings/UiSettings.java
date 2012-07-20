/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import android.app.ActivityManagerNative;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

import java.util.ArrayList;

public class UiSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "UiSettings";

	private static final String KEY_UI_MODE = "ui_mode";
	private static final String KEY_SLIDING_NAVBAR = "sliding_navbar";

    private CheckBoxPreference mSlidingNavbar;
	private CheckBoxPreference mUiMode;

    private final Configuration mCurConfig = new Configuration();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentResolver resolver = getActivity().getContentResolver();

        addPreferencesFromResource(R.xml.ui_settings);

       	mUiMode = (CheckBoxPreference) findPreference(KEY_UI_MODE);
        mUiMode.setPersistent(false);
		mUiMode.setChecked(Settings.System.getInt(getContentResolver(),
								Settings.System.UI_MODE, 0) == 1);
		mUiMode.setOnPreferenceChangeListener(this);

       	mSlidingNavbar = (CheckBoxPreference) findPreference(KEY_SLIDING_NAVBAR);
        mSlidingNavbar.setPersistent(false);
		mSlidingNavbar.setChecked(Settings.System.getInt(getContentResolver(),
								Settings.System.NAVIGATION_BAR_USE_SLIDER, 0) == 1);
		mSlidingNavbar.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
		if (KEY_UI_MODE.equals(key)) {
			boolean value = ((Boolean) objValue.equals(Boolean.TRUE));
			Settings.System.putInt(getContentResolver(), Settings.System.UI_MODE,
					value ? 1 : 0);
			Log.d(TAG, "UI mode = " + value);
		}

		if (KEY_SLIDING_NAVBAR.equals(key)) {
			boolean value = ((Boolean) objValue.equals(Boolean.TRUE));
			Settings.System.putInt(getContentResolver(), Settings.System.NAVIGATION_BAR_USE_SLIDER,
					value ? 1 : 0);
			Log.d(TAG, "sliding navbar = " + value);
		}

        return true;
    }
}
