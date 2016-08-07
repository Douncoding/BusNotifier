package com.douncoding.busnotifier.data;

/**
 * 버스 도착시간 정보
 */
public class BusArrival {
    int idStation;
    int idRoute;

    int[] locationNo = new int[2];  // 몇정류소전
    int[] predictTime = new int[2]; // 도착시간
    String flag; // 상태 PASS:운행중 STOP:운행종료 WAIT:회차지대기

    // 불필요한 정보는 처리하지 않는다.
    // lowPlate, PlateNo, remainSeatCnt 제외

    public int[] getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(int[] predictTime) {
        this.predictTime = predictTime;
    }

    public int[] getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(int[] locationNo) {
        this.locationNo = locationNo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }
}
