package com.douncoding.busnotifier.presenter;

import com.douncoding.busnotifier.data.BusLocation;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.RouteStation;
import com.douncoding.busnotifier.data.Station;

import java.util.List;

/**
 *
 */
public class RouteContract {

    public interface Presenter extends BasePresenter {
        // 초기화
        void initialize();
        // 노선 즐겨찾기 추가
        void changeBookmark();
        // 노선 즐겨찾기 삭제
        void removeBookmark();
        // 버스 위치 갱신
        void updateBusLocation();
        // 현재 사용자 위치
        void currentLocation();
    }

    public interface View extends BaseView {
        // 노선 기본정보 뷰 정보 출력
        void setRouteBasicInfo(Route route);
        // 노선 정류소 목록 뷰 출력
        void setRouteStationList(List<Station> slist, List<BusLocation> blist);
        // 노선 세부정보 현시
        void showRouteDetailView(Route route);
        // 노선 경로 뷰 현시
        void showRouteStationMapView();
        // 즐겨찾기 버튼 상태 변경
        void changeBookmarkState(boolean state);
        // 토스트 메시지
        void showMessage(String message);
        // 현재 사용자의 위치 출력
        void showCurrentLocation();
    }
}
