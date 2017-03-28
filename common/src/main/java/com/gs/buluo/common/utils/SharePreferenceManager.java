package com.gs.buluo.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceManager
{
    private final static String PreferenceFileName = "tripe_preference";
    public static SharedPreferences sharePreference;
    public static Editor edit;
    private static SharePreferenceManager mShare = null;

    public static SharePreferenceManager getInstance(Context context){
        if(mShare == null){
            mShare = new SharePreferenceManager();
        }

        if (sharePreference == null) {
            sharePreference = context.getSharedPreferences(PreferenceFileName, Context.MODE_PRIVATE);
        }
        return mShare;
    }

    public  void setValue(String name, String value) {
        edit = sharePreference.edit();
        edit.putString(name, value);
        edit.commit();
    }
    public void setValue(String name, boolean value)
    {
        edit = sharePreference.edit();
        edit.putBoolean(name, value);
        edit.commit();
    }

    public void setValue(String name, int value)
    {
        edit = sharePreference.edit();
        edit.putInt(name, value);
        edit.commit();
    }

    public void setValue(String name, long value){
        edit = sharePreference.edit();
        edit.putLong(name, value);
        edit.commit();
    }

    public void clearValue(String name){
        edit = sharePreference.edit();
        edit.remove(name);
        edit.commit();
    }

    public void setValue(String name, float value)
    {
        edit = sharePreference.edit();
        edit.putFloat(name, value);
        edit.commit();
    }

    public Long getLongValue(String name){
        return sharePreference.getLong(name, 0);
    }

    public boolean getBooeanValue(String name) {
        return sharePreference.getBoolean(name, true);
    }
    public boolean getBooeanValue(String name, boolean defalut) {
        return sharePreference.getBoolean(name, defalut);
    }

    public int getIntValue(String name) {
        return sharePreference.getInt(name, 0);
    }
    public int getIntValueForAec(String name) {
        return sharePreference.getInt(name, -100);
    }

    public int getIntValue(String name, int def) {
        return sharePreference.getInt(name, def);
    }
    public String getStringValue(String name) {
        String string = sharePreference.getString(name, "");
        return string;
    }
    public float getFloatValue(String name)
    {
        return sharePreference.getFloat(name, 0f);
    }
    public float getFloatValue(String name, float def)
    {
        return sharePreference.getFloat(name, def);
    }
}
