package com.douncoding.busnotifier.presenter;

import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.repository.RouteRepository;

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
        mView.showSearchRouteList(routeList);
    }

    @Override
    public void insertRecentSearchList(Route route) {

    }
}
