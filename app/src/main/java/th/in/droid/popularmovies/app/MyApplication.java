package th.in.droid.popularmovies.app;

import android.app.Application;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Hack: force use of overflow menu on devices with menu button
        // http://stackoverflow.com/questions/9286822/how-to-force-use-of-overflow-menu-on-devices-with-menu-button
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ignored) {
        }
    }
}
