package com.douncoding.busnotifier.fragment;

import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.view.StationArriveView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class StationFragment extends BaseFragment {

    @BindView(R.id.content_container) ViewGroup mRootContainer;

    HashMap<String, StationArriveView> mArriveInfo = new HashMap<>();

    private void test() {
        StationArriveView arriveView1 = new StationArriveView(getActivity());
        arriveView1.setRouteTypeName("일반");
        mArriveInfo.put("일반", arriveView1);

        StationArriveView arriveView2 = new StationArriveView(getActivity());
        arriveView2.setRouteTypeName("광역");
        mArriveInfo.put("광역", arriveView2);

        StationArriveView arriveView3 = new StationArriveView(getActivity());
        arriveView3.setRouteTypeName("시내");
        mArriveInfo.put("시내", arriveView3);

        mRootContainer.addView(arriveView1);
        mRootContainer.addView(arriveView2);
        mRootContainer.addView(arriveView3);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station, container, false);
        ButterKnife.bind(this, view);

        test();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
