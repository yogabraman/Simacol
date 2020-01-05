package com.smart.simacol.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginPref {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "SIMACOL";

    private static final String IS_LOGIN_IN = "isLoginIn";

    public LoginPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLoginStatus(boolean isLoginIn) {
        editor.putBoolean(IS_LOGIN_IN, isLoginIn);
        editor.commit();
    }

    public boolean isLoginIn() {
        return pref.getBoolean(IS_LOGIN_IN, false);
    }
}
