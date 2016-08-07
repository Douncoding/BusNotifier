package com.douncoding.busnotifier.data;

import com.google.gson.Gson;

/**
 * 정류소 클래스
 * 멤버변수 설명 {@link com.douncoding.busnotifier.data.repository.DatabaseContract.Station}
 */
public class Station {
    int idStation;

    String name;
    int idCenter;
    String isCenter;
    float x;
    float y;
    String regionName;
    String mobileCode;
    String districtCode;

    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public String getIsCenter() {
        return isCenter;
    }

    public void setIsCenter(String isCenter) {
        this.isCenter = isCenter;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getMobileCode() {

        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String toSerialize() {
        return new Gson().toJson(this);
    }
}
