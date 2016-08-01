package com.douncoding.busnotifier.presenter;

import com.douncoding.busnotifier.data.Route;

import java.util.List;

/**
 * {@link com.douncoding.busnotifier.fragment.SearchFragment} 의 Presenter
 */
public class SearchContract {

    public interface Presenter extends BasePresenter {
        // 노선 검색
        void searchRouteByName();
        // 최근 검색기록 추가
        void insertRecentSearchList(Route route);
    }

    public interface View extends BaseView {
        // 검색 목록 출력
        void showSearchRouteList(List<Route> routeList);
    }
}
