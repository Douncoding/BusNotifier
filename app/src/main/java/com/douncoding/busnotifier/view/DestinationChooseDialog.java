package com.douncoding.busnotifier.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Station;
import com.google.gson.Gson;

public class DestinationChooseDialog extends DialogFragment {

    private static final String EXTRA_PARAMS1 = "EXTRA_PARAMS1";
    private static final String EXTRA_PARAMS2 = "EXTRA_PARAMS2";
    private static final String EXTRA_PARAMS3 = "EXTRA_PARAMS3";

    /**
     * @param destStation 하차 정류장
     * @param targetStation 알람 발생위치 정류장
     * @param nearPlate 가장 가가운 버스 번호
     */
    public static DestinationChooseDialog create(Station destStation, Station targetStation, String nearPlate) {
        DestinationChooseDialog dialog = new DestinationChooseDialog();
        Bundle args = new Bundle();

        args.putString(EXTRA_PARAMS1, destStation.toSerialize());
        args.putString(EXTRA_PARAMS2, targetStation.toSerialize());
        args.putString(EXTRA_PARAMS3, nearPlate);

        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_destination_choose, null);

        final Station dest = new Gson().fromJson(getArguments().getString(EXTRA_PARAMS1), Station.class);
        final Station target = new Gson().fromJson(getArguments().getString(EXTRA_PARAMS2), Station.class);
        final String plate =  getArguments().getString(EXTRA_PARAMS3);

        ((TextView)view.findViewById(R.id.destination_station)).setText(dest.getName());
        ((TextView)view.findViewById(R.id.target_station)).setText(target.getName());
        ((TextView)view.findViewById(R.id.bus_plate_number)).setText(plate);

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("하차 알림 선택")
                .customView(view, false)
                .positiveText("선택")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onChoosedListener != null)
                            onChoosedListener.onPositive();
                    }
                })
                .build();
        return dialog;
    }

    OnChoosedListener onChoosedListener;
    public interface OnChoosedListener {
        void onPositive();
    }

    public void setOnChoosedListener(OnChoosedListener onChoosedListener) {
        this.onChoosedListener = onChoosedListener;
    }
}
