package com.douncoding.busnotifier.presenter;

import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.RouteStation;
import com.douncoding.busnotifier.data.Station;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.data.repository.StationRepository;

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
    private ArrayList<RouteStation> mForwardList;
    private ArrayList<RouteStation> mReverseList;
    private ArrayList<Station> mStationList;

    public RoutePresenter(Route route,
                          RouteContract.View view,
                          StationRepository stationRepository,
                          RouteStationRepository routeStationRepository) {
        this.mRoute = route;
        this.mRouteView = view;
        this.mStationRepository = stationRepository;
        this.mRouteStationRepository = routeStationRepository;
        this.mForwardList = new ArrayList<>();
        this.mReverseList = new ArrayList<>();
        this.mStationList = new ArrayList<>();
    }


    @Override
    public void initialize() {
        mRouteView.setRouteBasicInfo(mRoute);

        List<RouteStation> routeStationList =
                mRouteStationRepository.findRouteStationById(mRoute.getIdRoute());

        for (RouteStation routeStation : routeStationList) {
            // 방향 별 정렬
            if (routeStation.getUpDown().contains(RouteStation.FORWARD)) {
                mForwardList.add(routeStation);
            } else {
                mReverseList.add(routeStation);
            }
            // 정류소 세부정보 얻기
            mStationList.add(mStationRepository.findStationById(routeStation.getIdStation()).get(0));
        }

        mRouteView.setRouteStationList(mStationList);
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
