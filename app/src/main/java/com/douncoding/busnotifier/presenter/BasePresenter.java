package com.douncoding.busnotifier.presenter;

/**
 * 모든 Presenter 의 공통 기능
 */
public interface BasePresenter {

    void initialize();

    void resume();

    void pause();
}
