package com.douncoding.busnotifier.presenter;

import android.util.Log;

import com.douncoding.busnotifier.data.BusArrival;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.RouteStation;
import com.douncoding.busnotifier.data.Station;
import com.douncoding.busnotifier.data.repository.DatabaseContract;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.net.api.BusArrivalApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class StationPresenter implements StationContract.Presenter {

    private final StationContract.View mStationView;
    private final Station mStation;
    private final RouteRepository routeRepository;

    private HashMap<Route, BusArrival> mDataStore;

    public StationPresenter(Station station, StationContract.View view,
                            RouteRepository routeRepository) {
        this.mStation = station;
        this.mStationView = view;
        this.routeRepository = routeRepository;
        this.mDataStore = new HashMap<>();
    }

    @Override
    public void initialize() {
        mStationView.setStationBasicInfo(mStation);
        updateLocalDataStore();
    }

    private void updateLocalDataStore() {
        mDataStore.clear();
        BusArrivalApi.getBusArrivalList(mStation.getIdStation(), new BusArrivalApi.OnCallback() {
            @Override
            public void onResponse(List<BusArrival> list) {
                for (BusArrival arrival : list) {
                    mDataStore.put(routeRepository.findById(arrival.getIdRoute()), arrival);
//                    Log.d("CHECK", new Gson().toJson(arrival));
                }
                mStationView.showArrivalInfo(" ", mDataStore);
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
