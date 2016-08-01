package com.douncoding.busnotifier.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.RouteStation;
import com.douncoding.busnotifier.data.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * 노선별 정류장 목록 뷰
 *
 * TODO 1. 현재위치의 정류장
 * TODO 3. 하차정류장 선택 화면
 */
public class StationRouteView extends RelativeLayout {

    RecyclerView mStationRouteView;
    RecyclerView.LayoutManager mLayoutManager;
    RouteAdapter mAdapter;

    ArrayList<Station> mStationList = new ArrayList<>();

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

    public void setUpStationList(List<Station> list) {
        mStationList.clear();
        mStationList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.DataHolder> {

        class DataHolder extends RecyclerView.ViewHolder {
            TextView mStationName;
            TextView mStationCode;
            TextView mStationSETime;
            ImageView mAlarmButton;

            public DataHolder(View itemView) {
                super(itemView);

                mStationName = (TextView)itemView.findViewById(R.id.station_name_txt);
                mStationCode = (TextView)itemView.findViewById(R.id.station_code_txt);
                // 미사용
                // mStationSETime = (TextView)itemView.findViewById(R.id.station_se_time_txt);
                mAlarmButton = (ImageView)itemView.findViewById(R.id.take_off_btn);
                mAlarmButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("하차 정류장을 선택하시겠습니까?");
                        builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext()
                                        , "공사중..."
                                        , Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    }
                });
            }
        }

        @Override
        public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_station_route_list_item, parent, false);
            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(DataHolder holder, int position) {
            Station station = mStationList.get(position);

            if (station != null) {
                holder.mStationName.setText(station.getName());
                String mobileCode = station.getMobileCode();
                if (mobileCode.equals("0") || mobileCode.equals("00000")) {
                    mobileCode = "미정차";
                }
                holder.mStationCode.setText(mobileCode);
                //holder.mStationSETime.setText(station.get);
            }
        }

        @Override
        public int getItemCount() {
            return mStationList.size();
        }
    }
}
