package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.douncoding.busnotifier.Constant;
import com.douncoding.busnotifier.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 지도 관련 기능 처리
 *
 * TODO 1. 마지막 위치 기억 후 재실행 시 해당위치부터 검색시작
 * TODO 2. 현재위치를 찾는 중 이라는 다이얼로그
 * TODO 3. 현재위치를 찾는 중 사용자 입력제한
 */
public class MapsActivity extends BaseActivity implements MapView.CurrentLocationEventListener
        ,MapReverseGeoCoder.ReverseGeoCodingResultListener
        ,MapView.MapViewEventListener {
    public static final String TAG = MapsActivity.class.getSimpleName();

    @BindView(R.id.map_view)
    MapView mMapView;

    MapReverseGeoCoder mReverseGeoCoder;
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        MapView.setMapTilePersistentCacheEnabled(true);

        mMapView.setDaumMapApiKey(Constant.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setHDMapTileEnabled(true);
        mMapView.setCurrentLocationEventListener(this);
        mMapView.setMapViewEventListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    /**
     * 위치 업데이트 이벤트 수신
     *
     * @param mapView
     * @param currentLocation 현재위치
     * @param v 정확도
     */
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float v) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        showToastMessage(String.format(Locale.KOREA, "MapView 현재위치 (%f,%f) 정확도 (%f)",
                mapPointGeo.latitude, mapPointGeo.longitude, v));

        mMapView.setZoomLevel(-1, true);

        // 현재 위치의 행정동 요청
        mReverseGeoCoder = new MapReverseGeoCoder(Constant.DAUM_MAPS_ANDROID_APP_API_KEY,
                mMapView.getMapCenterPoint(), MapsActivity.this, MapsActivity.this);
        mReverseGeoCoder.startFindingAddress();
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) { }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) { }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) { }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        // 현재위치의 법정동 문자열 출력
        showToastMessage(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    /**
     * {@link MapView} 가 사용가능한 상태가 된 경우 발생
     *
     * @param mapView 대상
     */
    @Override
    public void onMapViewInitialized(MapView mapView) {
        // Ref: http://apis.map.daum.net/android/documentation/#MapView_CurrentLocationTrackingMode
        // 현재위치 트랙킹 + 나침반 모드 활성화
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
