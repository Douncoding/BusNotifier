package com.douncoding.busnotifier.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Route;

import java.util.ArrayList;

/**
 * 정류장 도착예정 버스 목록 뷰
 * - 버스의 타입 별 구분
 * - 따라서, 버스타입 클래스에 종속적
 */
public class StationArriveView extends RelativeLayout {

    TextView mRouteTypeText;
    RecyclerView mRouteListView;
    RecyclerView.LayoutManager mLayoutManager;
    RouteArriveAdapter mAdapter;

    ArrayList<Route> mRouteList = new ArrayList<>();

    public StationArriveView(Context context) {
        super(context);
        init();
    }

    public StationArriveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_station_arrive, this);
        mRouteTypeText = (TextView)findViewById(R.id.route_type_txt);
        mRouteListView = (RecyclerView)findViewById(R.id.route_list);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RouteArriveAdapter();
        mRouteListView.setLayoutManager(mLayoutManager);
        mRouteListView.setAdapter(mAdapter);

        test();
    }

    private void test() {
        for (int i = 0; i < 5; i++) {
            Route route = new Route();
            route.setRouteName("1500");
            mRouteList.add(route);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setRouteTypeName(String type) {
        if (type != null) {
            mRouteTypeText.setText(type);
        }
    }

    class RouteArriveAdapter extends RecyclerView.Adapter<RouteArriveAdapter.DataHolder> {

        class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView mRouteNumberText;
            TextView mStationNameText;
            TextView mFirstArriveText;
            TextView mSecondArriveText;

            public DataHolder(View itemView) {
                super(itemView);

                mStationNameText = (TextView)itemView.findViewById(R.id.station_name_txt);
                mRouteNumberText = (TextView)itemView.findViewById(R.id.route_number_txt);
                mFirstArriveText = (TextView)itemView.findViewById(R.id.first_arrive_txt);
                mSecondArriveText = (TextView)itemView.findViewById(R.id.second_arrive_txt);
            }

            @Override
            public void onClick(View view) {

            }
        }

        @Override
        public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_station_arrive_list_item, parent, false);
            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(DataHolder holder, int position) {
            Route route = mRouteList.get(position);

            if (route != null) {
                holder.mRouteNumberText.setText(route.getRouteName());
            }
        }

        @Override
        public int getItemCount() {
            return mRouteList.size();
        }
    }
}
