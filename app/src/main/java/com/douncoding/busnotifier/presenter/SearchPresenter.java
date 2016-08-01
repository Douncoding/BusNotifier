package com.douncoding.busnotifier.presenter;

import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.repository.RouteRepository;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class SearchPresenter implements SearchContract.Presenter {

    String mTargetRouteName;
    RouteRepository mRouteRepository;

    SearchContract.View mView;

    public SearchPresenter(SearchContract.View view,
                           String target,
                           RouteRepository routeRepository) {
        if (view == null || target == null || routeRepository == null) {
            throw new IllegalArgumentException("");
        }

        this.mView = view;
        this.mTargetRouteName = target;
        this.mRouteRepository = routeRepository;
    }

    @Override
    public void searchRouteByName() {
        List<Route> routeList = mRouteRepository.findByLikeName(mTargetRouteName);
        List<Route> filterList = new LinkedList<>();

        // 필터링: 세부정보를 알수 없는 경우 검색목록에서 제외한다
        for (Route route : routeList) {
            if (route.getDescription() != null) {
                filterList.add(route);
            }
        }

        mView.showSearchRouteList(filterList);
    }

    @Override
    public void insertRecentSearchList(Route route) {

    }
}
