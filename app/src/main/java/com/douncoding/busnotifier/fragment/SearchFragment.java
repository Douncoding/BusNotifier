package com.douncoding.busnotifier.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.douncoding.busnotifier.Navigator;
import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.activity.RouteActivity;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.presenter.SearchContract;
import com.douncoding.busnotifier.presenter.SearchPresenter;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 검색결과 화면
 */
public class SearchFragment extends BaseFragment implements SearchContract.View {

    @BindView(R.id.route_list)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SearchResultAdapter mAdapter;

    SearchContract.Presenter mPresenter;

    private static final String PARAMS_ROUTE_NAME = "ROUTE_NAME";
    public static SearchFragment getInstance(String routeName) {
        Bundle args = new Bundle();
        args.putString(PARAMS_ROUTE_NAME, routeName);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serach, container, false);
        ButterKnife.bind(this, view);

        // 검색목록을 위한 객체 생성
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new SearchResultAdapter();

        // 매개변수 가져오기 및 프리젠터 생성
        String route = getArguments().getString(PARAMS_ROUTE_NAME);
        mPresenter = new SearchPresenter(this, route, RouteRepository.getInstance(getActivity()));

        // 리스트 뷰 설정
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 화면 출력 후 검색결과 생성
        mPresenter.searchRouteByName();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLayoutManager = null;
        mAdapter = null;
    }

    @Override
    public void showSearchRouteList(List<Route> routeList) {
        mAdapter.update(routeList);
    }

    class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.DataHolder> {
        ArrayList<Route> mSearchList = new ArrayList<>();

        public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView mNameText;
            TextView mDescriptionText;

            public DataHolder(View itemView) {
                super(itemView);
                mNameText = (TextView)itemView.findViewById(R.id.route_name_txt);
                mDescriptionText = (TextView)itemView.findViewById(R.id.route_desc_txt);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Route route = mSearchList.get(getPosition());
                if (route != null) {
                    Navigator.navigateToRoute(getActivity(), route, RouteActivity.FROM_SEARCH);
                } else {
                    throw new RuntimeException("Route 객체를 찾을 수 없습니다.");
                }
            }
        }

        @Override
        public DataHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_list_item, parent, false);
            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(DataHolder holder, int position) {
            Route route = mSearchList.get(position);

            if (route != null) {
                holder.mNameText.setText(route.getRouteName());
                holder.mDescriptionText.setText(route.getDescription());
            } else {
                throw new RuntimeException(position + " 위치의 값을 찾을 수 없습니다.");
            }
        }

        public void update(List<Route> routeList) {
            mSearchList.clear();
            mSearchList.addAll(routeList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mSearchList.size();
        }
    }
}
