package io.kuban.print.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;


/**
 * Created by wang on 15/1/10.
 */
public class UserManager {

    private static final String APP_ID = "app_id";
    private static final String APP_SECRET = "app_secret";
    private static final String URL = "url";
    //    private static SecuredPreferenceStore prefStore;
    private Context context;
    private static ACache cache;

    private UserManager(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
//        prefStore = SecuredPreferenceStore.getSharedInstance(context);
        cache = ACache.get(context);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void saveUserObject(String app_id, String app_secret) {
//        prefStore.edit().putString(APP_ID, app_id).apply();
//        prefStore.edit().putString(APP_SECRET, app_secret).apply();
        cache.put(APP_ID, app_id);
        cache.put(APP_SECRET, app_secret);
    }


    public static String getAppId() {
        String app_id = "";
        if (cache != null) {
            app_id = cache.getAsString(APP_ID);
        }
        return app_id;
    }

    public static String getAppSecret() {
        String app_secret = "";
        if (cache != null) {
            app_secret = cache.getAsString(APP_SECRET);
        }
        return app_secret;
    }

    public static void setUrl(String url) {
        cache.put(URL, url);
    }

    public static String getUrl() {
        return cache.getAsString(URL);
    }

    public static void getRemove() {
        cache.remove(URL);
        cache.remove(APP_SECRET);
        cache.remove(APP_ID);
        cache.remove("meeting_rooms_id");
        cache.remove("location_id");
        cache.remove("is_thereare");
    }
}
