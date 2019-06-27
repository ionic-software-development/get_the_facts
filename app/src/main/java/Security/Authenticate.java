package Security;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class Authenticate {
    private static final String PREFS_NAME_TAG = "twitterPreferences";
    private static final String SHARED_PREF_ACCESS_TOKEN = "accessToken";
    private static final String SHARED_PREF_LOGGED_IN = "loggedIn";

    public static ConfigurationBuilder getConfigurationBuilder() {
        // Must be changed to fetch from env variables or string in context
        ConfigurationBuilder cb = new ConfigurationBuilder();

        return cb.setDebugEnabled(true)
                .setOAuthConsumerKey("vcT2LvKoPcaAwwt4tFeav8nAl")
                .setOAuthConsumerSecret("CoR3CAvxJ4U061fG3JsAiCDG16qD8w1xcMP9Ctwo8oFfIY1Yvp")
                .setOAuthAccessToken("832302076743008256-iG6su5rtDdYe0x1u8mPvxy1bLwPlBq4")
                .setOAuthAccessTokenSecret("1iJFSBwSLrUdWXxlaP3Ro555O2AtEPxk7GMKL3dMdErLl");
    }

    public static Twitter getTwitterInstance() {

        ConfigurationBuilder cb = getConfigurationBuilder();

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;

    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME_TAG, Context.MODE_PRIVATE);
    }

    public static AccessToken getAccessToken(Context context) {
        return new Gson().fromJson(
                getSharedPreferences(context).getString(SHARED_PREF_ACCESS_TOKEN, null),
                AccessToken.class);
    }

    public static boolean getUserLoggedIn(Context context) {
        return getSharedPreferences(context).getBoolean(
                SHARED_PREF_LOGGED_IN, false);
    }

    public static void setAccessToken(@NonNull Context context, @NonNull AccessToken accessToken) {
        getSharedPreferences(context).edit()
                .putString(SHARED_PREF_ACCESS_TOKEN, new Gson()
                        .toJson(accessToken))
                .putBoolean(SHARED_PREF_LOGGED_IN, true).apply();
    }

    public static void clearAccessToken(@NonNull Context context) {
        getSharedPreferences(context).edit()
                .remove(SHARED_PREF_ACCESS_TOKEN)
                .remove(SHARED_PREF_LOGGED_IN).apply();
    }

}
