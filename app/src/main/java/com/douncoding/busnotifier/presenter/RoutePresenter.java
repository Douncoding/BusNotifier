package com.douncoding.busnotifier.presenter;

import android.util.Log;

import com.douncoding.busnotifier.data.BusLocation;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.RouteStation;
import com.douncoding.busnotifier.data.Station;
import com.douncoding.busnotifier.data.repository.BookmarkRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.data.repository.StationRepository;
import com.douncoding.busnotifier.net.api.BusLocationApi;

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
    private final BookmarkRepository mBookmarkRepository;

    // 현재 처리중인 노선이름 변수
    private Route mRoute;
    private ArrayList<Station> mStationList;
    private ArrayList<BusLocation> mBusLocationList;

    private boolean isBookmark;

    public RoutePresenter(Route route,
                          RouteContract.View view,
                          StationRepository stationRepository,
                          RouteStationRepository routeStationRepository,
                          BookmarkRepository bookmarkRepository) {
        this.mRoute = route;
        this.mRouteView = view;
        this.mStationRepository = stationRepository;
        this.mRouteStationRepository = routeStationRepository;
        this.mBookmarkRepository = bookmarkRepository;

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

        // 북마크 상태 확인 및 변경
        isBookmark = mBookmarkRepository.isContain(mRoute.getIdRoute());
        mRouteView.changeBookmarkState(isBookmark);
    }

    public void addRecent() {
        mBookmarkRepository.add(mRoute, BookmarkRepository.TYPE_RECENT);
    }

    @Override
    public void changeBookmark() {
        if (isBookmark) {
            Log.d("CHECK", "북마크 해제");
            mBookmarkRepository.del(mRoute);
            mRouteView.showMessage("북마크에 제거되었습니다.");
        } else {
            Log.d("CHECK", "북마크 추가");
            mBookmarkRepository.add(mRoute, BookmarkRepository.TYPE_BOOKMARK);
            mRouteView.showMessage("북마크에 추가되었습니다.");
        }

        // 북마크 상태변경
        isBookmark = !isBookmark;
        mRouteView.changeBookmarkState(isBookmark);
    }

    @Override
    public void removeBookmark() {
        mBookmarkRepository.del(mRoute);
    }

    @Override
    public void updateBusLocation() {

    }

    @Override
    public void currentLocation() {

    }
}
