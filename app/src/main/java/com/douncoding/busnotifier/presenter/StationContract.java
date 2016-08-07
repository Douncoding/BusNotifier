package com.douncoding.busnotifier.presenter;

import com.douncoding.busnotifier.data.BusArrival;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.Station;

import java.util.HashMap;

/**
 * {@link com.douncoding.busnotifier.fragment.StationFragment} 의 비즈니스 로직
 */
public class StationContract {

    public interface Presenter extends BasePresenter {
        void initialize();
        void resume();
        void pause();
    }

    public interface View extends BaseView {
        // 기본정보 출력
        void setStationBasicInfo(Station station);

        /**
         * 버스 도착정보를 새롭개 그림
         *
         * @param type 버스타입
         * @param map 버스도착정보
         */
        void showArrivalInfo(String type, HashMap<Route, BusArrival> map);

        // 시간만 갱신
        void updateTime();
    }
}
