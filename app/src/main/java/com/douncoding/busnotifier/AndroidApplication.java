package com.douncoding.busnotifier;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.douncoding.busnotifier.data.repository.DatabaseContract;
import com.douncoding.busnotifier.data.repository.DatabaseHelper;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.data.repository.StationRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
