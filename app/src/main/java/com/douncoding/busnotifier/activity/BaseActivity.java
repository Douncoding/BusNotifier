package com.douncoding.busnotifier.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.douncoding.busnotifier.Navigator;
import com.douncoding.busnotifier.presenter.BasePresenter;

public class BaseActivity extends AppCompatActivity {

    public Navigator mNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigator = new Navigator();
    }

    public void showToastMessage(String message) {
        Toast.makeText(BaseActivity.this
                , message
                , Toast.LENGTH_SHORT).show();
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }
}
