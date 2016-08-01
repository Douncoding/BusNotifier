package com.douncoding.busnotifier.presenter;

import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;

/**
 * 선택된 노선에 대한 비즈니스 로직을 구현하는 클래스
 *
 * {@link com.douncoding.busnotifier.activity.RouteActivity} 의 기능 구현
 */
public class RoutePresenter implements RouteContract.Presenter {

    private final RouteContract.View mRouteView;
    private final RouteRepository mRouteRepository;
    private final RouteStationRepository mRouteStationRepository;

    // 현재 처리중인 노선이름 변수
    private String mRouteName;

    public RoutePresenter(String routeName,
                          RouteContract.View view,
                          RouteRepository routeRepository,
                          RouteStationRepository routeStationRepository) {
        this.mRouteName = routeName;
        this.mRouteView = view;
        this.mRouteRepository = routeRepository;
        this.mRouteStationRepository = routeStationRepository;
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

    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
