package com.douncoding.busnotifier.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 모든 {@link Fragment} 의 부모 Fragment
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void showMessage(String message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
}
