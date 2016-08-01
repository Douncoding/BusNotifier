package com.douncoding.busnotifier.data;

import com.douncoding.busnotifier.data.repository.DatabaseContract;
import com.google.gson.Gson;

import java.util.StringTokenizer;

/**
 * 버스 노선 클래스
 * 아래항목 지원할 수 있는 모든 정보
 *
 * 필드정보 {@link DatabaseContract.Route} 참조
 *
 * 보다 올바른 설계는 데이터베이스에서 사용하는 모델과 비즈니스 로직에서 사용하는 모델을
 * 분리해야 하지만, 구현의 편의상 하나의 모델로 정의한다.
 */
public class Route {

    // 데이터베이스 호환형 변수
    int idRoute;
    int routeType;
    String routeName;
    int startStationId;
    String startStationName;
    String startStationCode;
    int endStationId;
    String endStationName;
    String endStationCode;
    String upFirstTime;
    String upLastTime;
    String downFirstTime;
    String downLastTime;
    String peekAlloc;
    String nonPeekAlloc;
    String regionName;

    // 변환형 변수
    RouteType convertRouteType = new RouteType(this.routeType);

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
        convertRouteType.parseToType(this.routeType);
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(int startStationId) {
        this.startStationId = startStationId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getStartStationCode() {
        return startStationCode;
    }

    public void setStartStationCode(String startStationCode) {
        this.startStationCode = startStationCode;
    }

    public int getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(int endStationId) {
        this.endStationId = endStationId;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public String getEndStationCode() {
        return endStationCode;
    }

    public void setEndStationCode(String endStationCode) {
        this.endStationCode = endStationCode;
    }

    public String getUpFirstTime() {
        return upFirstTime;
    }

    public void setUpFirstTime(String upFirstTime) {
        this.upFirstTime = upFirstTime;
    }

    public String getUpLastTime() {
        return upLastTime;
    }

    public void setUpLastTime(String upLastTime) {
        this.upLastTime = upLastTime;
    }

    public String getDownFirstTime() {
        return downFirstTime;
    }

    public void setDownFirstTime(String downFirstTime) {
        this.downFirstTime = downFirstTime;
    }

    public String getDownLastTime() {
        return downLastTime;
    }

    public void setDownLastTime(String downLastTime) {
        this.downLastTime = downLastTime;
    }

    public String getPeekAlloc() {
        return peekAlloc;
    }

    public void setPeekAlloc(String peekAlloc) {
        this.peekAlloc = peekAlloc;
    }

    public String getNonPeekAlloc() {
        return nonPeekAlloc;
    }

    public void setNonPeekAlloc(String nonPeekAlloc) {
        this.nonPeekAlloc = nonPeekAlloc;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder();

        if (this.routeType != 0 && this.regionName != null) {
            StringTokenizer tokenizer = new StringTokenizer(this.regionName, ",");
            builder.append(tokenizer.nextToken()).append(" ").append(convertRouteType.getType());
        }

        return builder.toString();
    }

    public String toSerialize() {
        return new Gson().toJson(this);
    }
}
