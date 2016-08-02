package com.douncoding.busnotifier.presenter;

import android.util.Log;

import com.douncoding.busnotifier.data.BusLocation;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.RouteStation;
import com.douncoding.busnotifier.data.Station;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.data.repository.StationRepository;
import com.douncoding.busnotifier.net.api.BusLocationApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 선택된 노선에 대한 비즈니스 로직을 구현하는 클래스
 *
 * {@link com.douncoding.busnotifier.activity.RouteActivity} 의 기능 구현
 */
public class RoutePresenter implements RouteContract.Presenter {

    private final RouteContract.View mRouteView;
    private final StationRepository mStationRepository;
    private final RouteStationRepository mRouteStationRepository;

    // 현재 처리중인 노선이름 변수
    private Route mRoute;
    private ArrayList<Station> mStationList;
    private ArrayList<BusLocation> mBusLocationList;

    public RoutePresenter(Route route,
                          RouteContract.View view,
                          StationRepository stationRepository,
                          RouteStationRepository routeStationRepository) {
        this.mRoute = route;
        this.mRouteView = view;
        this.mStationRepository = stationRepository;
        this.mRouteStationRepository = routeStationRepository;

        this.mStationList = new ArrayList<>();
        this.mBusLocationList = new ArrayList<>();
    }


    @Override
    public void initialize() {
        mRouteView.setRouteBasicInfo(mRoute);

        List<RouteStation> routeStationList =
                mRouteStationRepository.findRouteStationById(mRoute.getIdRoute());

        // 정류소 세부정보 얻기
        for (RouteStation routeStation : routeStationList) {
            mStationList.add(mStationRepository.findStationById(routeStation.getIdStation()).get(0));
        }

        BusLocationApi.getBusLocationList(mRoute.getIdRoute(), new BusLocationApi.OnCallback() {
            @Override
            public void onResponse(List<BusLocation> list) {
                mBusLocationList.addAll(list);
                mRouteView.setRouteStationList(mStationList, mBusLocationList);
            }
        });
    }

    @Override
    public void addBookmark() {

    }

    @Override
    public void removeBookmark() {

    }

    @Override
    public void updateBusLocation() {

    }

    @Override
    public void currentLocation() {

    }
}
