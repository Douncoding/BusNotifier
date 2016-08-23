package com.douncoding.busnotifier.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.Navigator;
import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.BusLocation;
import com.douncoding.busnotifier.data.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 노선별 정류장 목록 뷰
 *
 * TODO 1. 현재위치의 정류장
 * TODO 3. 하차정류장 선택 화면
 */
public class StationRouteView extends RelativeLayout {
    private OnListener onListener;
    public interface OnListener {
        void onAlarmClicked(Station destStation, Station targetStation, String nearPlate);
    }

    RecyclerView mStationRouteView;
    RecyclerView.LayoutManager mLayoutManager;
    RouteAdapter mAdapter;

    ArrayList<Station> mStationList = new ArrayList<>();
    HashMap<Integer, String> mBusLocationMap = new HashMap<>();

    public StationRouteView(Context context) {
        super(context);
        init();
    }

    public StationRouteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_station_route, this);

        mStationRouteView = (RecyclerView)findViewById(R.id.route_list);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RouteAdapter();

        mStationRouteView.setLayoutManager(mLayoutManager);
        mStationRouteView.setAdapter(mAdapter);
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public void setUpStationList(List<Station> stationList, List<BusLocation> busLocationList) {
        mStationList.clear();
        mStationList.addAll(stationList);

        mBusLocationMap.clear();
        for (BusLocation location : busLocationList) {
            mBusLocationMap.put(location.getIdStation(), location.getPlateNo());
        }

        mAdapter.notifyDataSetChanged();
    }

    class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.DataHolder> {

        class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            StationRouteMarkerLineView mMarkerView;
            TextView mStationName;
            TextView mStationCode;
            TextView mStationSETime;
            ImageView mAlarmButton;

            public DataHolder(View itemView) {
                super(itemView);

                mMarkerView = (StationRouteMarkerLineView)itemView.findViewById(R.id.marker_line_view);
                mStationName = (TextView)itemView.findViewById(R.id.station_name_txt);
                mStationCode = (TextView)itemView.findViewById(R.id.station_code_txt);
                mAlarmButton = (ImageView)itemView.findViewById(R.id.take_off_btn);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Station station = mStationList.get(getPosition());
                Navigator.navigateToStation(getContext(), station);
            }
        }

        @Override
        public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_station_route_list_item, parent, false);
            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(DataHolder holder, final int position) {
            final Station station = mStationList.get(position);

            if (station != null) {
                holder.mStationName.setText(station.getName());
                String mobileCode = station.getMobileCode();
                if (mobileCode.equals("0") || mobileCode.equals("00000")) {
                    mobileCode = "미정차";
                }
                holder.mStationCode.setText(mobileCode);

                String plate = mBusLocationMap.get(station.getIdStation());
                if (plate != null) {
                    holder.mMarkerView.visible(plate.substring(plate.length() - 4, plate.length()));
                } else {
                    holder.mMarkerView.invisible();
                }
            }

            holder.mAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nearBusPlateNumber = null;
                    for (int i = position; i > 0; i--) {
                        nearBusPlateNumber = mBusLocationMap.get(mStationList.get(i).getIdStation());
                        if (nearBusPlateNumber != null) {
                            break;
                        }
                    }

                    final Station target = mStationList.get(position - 2);
                    final Station destination =  mStationList.get(position);

//                    Log.e("CHECK", "목적지:" + destination.toSerialize());
//                    Log.e("CHECK", "알람위치:" + target.toSerialize());
//                    Log.e("CHECK", "가장가까운벗그번호:" + nearBusPlateNumber);
                    if (onListener != null) {
                        onListener.onAlarmClicked(destination, target, nearBusPlateNumber);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mStationList.size();
        }
    }
}
