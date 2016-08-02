package com.douncoding.busnotifier.data;

/**
 * 버스 위치 클래스
 */
public class BusLocation {
    int idRoute;
    int idStation;
    int seqStation; // 정류소 순서

    boolean endBus; // 막차 여부
    boolean lowPlate; // 저상버스 여부
    String plateNo; // 차량번호
    int remainSeatCnt; // 차량빈자리수

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

    public int getSeqStation() {
        return seqStation;
    }

    public void setSeqStation(int seqStation) {
        this.seqStation = seqStation;
    }

    public boolean isEndBus() {
        return endBus;
    }

    public void setEndBus(boolean endBus) {
        this.endBus = endBus;
    }

    public boolean isLowPlate() {
        return lowPlate;
    }

    public void setLowPlate(boolean lowPlate) {
        this.lowPlate = lowPlate;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public int getRemainSeatCnt() {
        return remainSeatCnt;
    }

    public void setRemainSeatCnt(int remainSeatCnt) {
        this.remainSeatCnt = remainSeatCnt;
    }
}
