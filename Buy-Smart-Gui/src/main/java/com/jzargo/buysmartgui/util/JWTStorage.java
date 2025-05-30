package com.jzargo.buysmartgui.util;

import lombok.SneakyThrows;

import java.util.prefs.Preferences;

public class JWTStorage {
    public static final String KEY = "jwtToken";
    public static final Preferences prefs = Preferences.userNodeForPackage(JWTStorage.class);

    @SneakyThrows
    public static void saveToken(String token){
        prefs.put(KEY, token);
        prefs.flush();
    }

    public static String loadToken(){
        return prefs.get(KEY, null);
    }

    public static void remove(){
        prefs.remove(KEY);
    }
}
