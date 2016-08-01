package com.douncoding.busnotifier.data;

import com.douncoding.busnotifier.data.repository.DatabaseContract;

/**
 * 버스 노선 클래스
 * 아래항목 지원할 수 있는 모든 정보
 *
 * 필드정보 {@link DatabaseContract.Route} 참조
 */
public class Route {
    int idRoute;
    String routeType;
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
    String nonPeekAllooc;

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
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

    public String getNonPeekAllooc() {
        return nonPeekAllooc;
    }

    public void setNonPeekAllooc(String nonPeekAllooc) {
        this.nonPeekAllooc = nonPeekAllooc;
    }
}
