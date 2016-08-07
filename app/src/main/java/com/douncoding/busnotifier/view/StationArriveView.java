package com.douncoding.busnotifier.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.BusArrival;
import com.douncoding.busnotifier.data.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    // 내부 데이터 저장소
    HashMap<Route, BusArrival> mLocalDataStore = new HashMap<>();
    // 리스트뷰에 추가되는 순서
    List<Route> mRouteList = new ArrayList<>();

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
    }

    public void setDataStore(String type, HashMap<Route, BusArrival> map) {
        if (map == null || map.size() == 0) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        mRouteTypeText.setText(type);
        mLocalDataStore = map;
        mRouteList.addAll(new ArrayList<>(map.keySet()));
        mAdapter.notifyDataSetChanged();
    }

    class RouteArriveAdapter extends RecyclerView.Adapter<RouteArriveAdapter.DataHolder> {
        class DataHolder extends RecyclerView.ViewHolder {
            TextView mRouteName;
            TextView mRouteType;
            TextView mFirstArriveTime, mFirstArriveLoc;
            TextView mSecondArriveTime, mSecondArriveLoc;
            ViewGroup mFirstContainer, mSecondContainer;

            public DataHolder(View itemView) {
                super(itemView);

                mRouteName = (TextView)itemView.findViewById(R.id.route_name_txt);
                mRouteType = (TextView)itemView.findViewById(R.id.route_type_txt);
                mFirstArriveTime = (TextView)itemView.findViewById(R.id.first_arrive_time_txt);
                mFirstArriveLoc = (TextView)itemView.findViewById(R.id.first_arrive_loc_txt);
                mSecondArriveTime = (TextView)itemView.findViewById(R.id.second_arrive_time_txt);
                mSecondArriveLoc = (TextView)itemView.findViewById(R.id.second_arrive_loc_txt);
                mFirstContainer = (ViewGroup)itemView.findViewById(R.id.first_container);
                mSecondContainer= (ViewGroup)itemView.findViewById(R.id.second_container);
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
            BusArrival arrival = mLocalDataStore.get(route);

            if (route != null) {
                holder.mRouteName.setText(route.getRouteName());
                holder.mRouteType.setText(route.getDescription());
//                Log.d("CHECK", "First:" + arrival.getPredictTime()[0] + "/" + arrival.getLocationNo()[0]);
//                Log.d("CHECK", "Second:" + arrival.getPredictTime()[1] + "/" + arrival.getLocationNo()[1]);
                holder.mFirstArriveTime.setText(String.valueOf(arrival.getPredictTime()[0]));
                holder.mFirstArriveLoc.setText(String.valueOf(arrival.getLocationNo()[0]));

                if (arrival.getPredictTime()[1] != 0 && arrival.getLocationNo()[1] != 0) {
                    holder.mSecondContainer.setVisibility(VISIBLE);
                    holder.mSecondArriveTime.setText(String.valueOf(arrival.getPredictTime()[1]));
                    holder.mSecondArriveLoc.setText(String.valueOf(arrival.getLocationNo()[1]));
                } else {
                    holder.mSecondContainer.setVisibility(INVISIBLE);
                    holder.mSecondArriveTime.setText(" ");
                    holder.mSecondArriveLoc.setText(" ");
                }
            }
        }

        @Override
        public int getItemCount() {
            return mLocalDataStore.size();
        }
    }
}
