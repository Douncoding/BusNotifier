package com.douncoding.busnotifier.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.view.BookmarkListView;
import com.douncoding.busnotifier.view.NearStationView;
import com.douncoding.busnotifier.view.RecentSearchLogView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 주요기능
 * 1. 즐겨찾기
 * 2. 주변 정류소
 * 3. 최근 검색내역
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.near_station_view)
    NearStationView mNearStationView;

    @BindView(R.id.recent_search_log_view)
    RecentSearchLogView mRecentSearchLogView;

    @BindView(R.id.bookmark_view)
    BookmarkListView mBookmarkListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
