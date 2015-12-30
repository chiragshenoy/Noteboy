package noteboy.noteboy;

import android.app.Application;
import android.support.v7.widget.ThemedSpinnerAdapter;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.interceptors.ParseStethoInterceptor;

/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


//        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
//        Parse.addParseNetworkInterceptor(new ParseStethoInterceptor());
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "a8cAez0DOO17ghQEYQcB6Ni7nGTD0c6RkSwTWbj3", "SVLzidFiHWpRZXCBkwmQj2FxWum78N4884IKTSGX");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}