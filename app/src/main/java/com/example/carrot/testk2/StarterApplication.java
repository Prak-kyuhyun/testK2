package com.example.carrot.testk2;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by carrot on 2015-12-14.
 */
public class StarterApplication extends Application {

    @Override
    public void onCreate(){
        super. onCreate();

        Parse.initialize(this, "iSnPK1Rxc44bNhPlOEpc7g7W5DafR3yhM9G81BjY", "42MKzXPfSiTnxFEn23nM7GbjzNcipsLz2qg9ldYI");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
