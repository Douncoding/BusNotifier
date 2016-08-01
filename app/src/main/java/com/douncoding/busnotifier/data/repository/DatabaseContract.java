package com.douncoding.busnotifier.data.repository;

import android.graphics.Color;
import android.provider.BaseColumns;

import java.util.Arrays;

public final class DatabaseContract {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Busnotifier";

    private static final String TEXT_TYPE = "TEXT";
    private static final String PRIMARY_KEY_TYPE = "INTEGER PRIMARY KEY";
    private static final String INTEGER_TYPE = "INTEGER";

    private DatabaseContract() {}

    /**
     * 테이블 별 버전정보
     *
     * 공공데이터 포털의 기반정보인 노선, 정거장, 노선경유정거장 정보는
     * 일단위 고정된 파일 형태임에 따라 버전을 구분할 필요성이 있으며, 버전은 업데이트 일자를 기준으로 한다.
     */
    public static abstract class Version implements BaseColumns {
        public static final String TABLE_NAME = "TABLE_VERSION";
        // 테이블 이름
        public static final String NAME = "NAME";
        // 버전 정보
        public static final String VERSION = "VERSION";
        public static final String VERSION_DEFAULT_VALUE = "LOCAL";
        public static final String CREATE_TABLE_SQL = "CREATE TABLE " +
                TABLE_NAME + " " +
                "(" +
                NAME + " " + TEXT_TYPE + " "+ "PRIMARY KEY" + ", " +
                VERSION + " " + TEXT_TYPE + " DEFAULT " + VERSION_DEFAULT_VALUE +
                ")";
    }

    public static abstract class Route implements BaseColumns {
        public static final String TABLE_NAME = "ROUTE";
        // 노선 ID
        public static final String ROUTE_ID = "ROUTE_ID";
        // 노선명
        public static final String ROUTE_NM = "ROUTE_NM";
        // 노선형태
        public static final String ROUTE_TP = "ROUTE_TP";
        // 기점정류장 ID
        public static final String ST_STA_ID = "ST_STA_ID";
        // 기점정류장 이름
        public static final String ST_STA_NM = "ST_STA_NM";
        // 기점정류장 번호
        public static final String ST_STA_NO = "ST_STA_NO";
        // 종점
        public static final String ED_STA_ID = "ED_STA_ID";
        // 종점정류장 이름
        public static final String ED_STA_NM = "ED_STA_NM";
        // 종점정류장 번호
        public static final String ED_STA_NO = "ED_STA_NO";
        // 상행첫차시간
        public static final String UP_FIRST_TIME = "UP_FIRST_TIME";
        // 상행막차시간
        public static final String UP_LAST_TIME = "UP_LAST_TIME";
        // 하행첫차시간
        public static final String DOWN_FIRST_TIME = "DOWN_FIRST_TIME";
        // 하행막차시간
        public static final String DOWN_LAST_TIME = "DOWN_LAST_TIME";
        // 출퇴근시 배차간격
        public static final String PEEK_ALLOC = "PEEK_ALLOC";
        // 평상시 배차간격
        public static final String NPEEK_ALLOC = "NPEEK_ALLOC";
        // 운수사 ID
        public static final String COMPANY_ID = "COMPANY_ID";
        // 운수사명
        public static final String COMPANY_NM = "COMPANY_NM";
        // 운수사연락처
        public static final String TEL_NO = "TEL_NO";
        // 지역명
        public static final String REGION_NAME = "REGION_NAME";
        // 지역코드
        public static final String DISTRICT_CD = "DISTRICT_CD";

        public static final String CREATE_TABLE_SQL = "CREATE TABLE " +
                TABLE_NAME + " " +
                "(" +
                ROUTE_ID + " " + PRIMARY_KEY_TYPE + ", " +
                ROUTE_NM + " " + TEXT_TYPE + ", " +
                ROUTE_TP + " " + INTEGER_TYPE + ", " +
                ST_STA_ID + " " + INTEGER_TYPE + ", " +
                ST_STA_NM + " " + TEXT_TYPE + ", " +
                ST_STA_NO + " " + TEXT_TYPE + ", " +
                ED_STA_ID + " " + INTEGER_TYPE + ", " +
                ED_STA_NM + " " + TEXT_TYPE + ", " +
                ED_STA_NO + " " + TEXT_TYPE + ", " +
                UP_FIRST_TIME + " " + TEXT_TYPE + ", " +
                UP_LAST_TIME + " " + TEXT_TYPE + ", " +
                DOWN_FIRST_TIME + " " + TEXT_TYPE + ", " +
                DOWN_LAST_TIME + " " + TEXT_TYPE + ", " +
                PEEK_ALLOC + " " + TEXT_TYPE + ", " +
                NPEEK_ALLOC + " " + TEXT_TYPE + ", " +
                COMPANY_ID + " " + INTEGER_TYPE + ", " +
                COMPANY_NM + " " + TEXT_TYPE + ", " +
                TEL_NO + " " + TEXT_TYPE + ", " +
                REGION_NAME + " " + TEXT_TYPE + ", " +
                DISTRICT_CD + " " + TEXT_TYPE +
                ")";

        public static String[] getColumnNames() {
            String[] colums = {
                    ROUTE_ID,
                    ROUTE_NM,
                    ROUTE_TP,
                    ST_STA_ID,
                    ST_STA_NM,
                    ST_STA_NO,
                    ED_STA_ID,
                    ED_STA_NM,
                    ED_STA_NO,
                    UP_FIRST_TIME,
                    UP_LAST_TIME,
                    DOWN_FIRST_TIME,
                    DOWN_LAST_TIME,
                    PEEK_ALLOC,
                    NPEEK_ALLOC,
                    COMPANY_ID,
                    COMPANY_NM,
                    TEL_NO,
                    REGION_NAME,
                    DISTRICT_CD
            };
            return colums;
        }
    }

    public static abstract class Station implements BaseColumns {
        public static final String TABLE_NAME = "STATION";

        // 정류소 ID
        public static final String STATION_ID = "STATION_ID";
        // 정류소 이름
        public static final String STATION_NM = "STATION_NM";
        // 지자체 ID
        public static final String CENTER_ID = "CENTER_ID";
        // 중앙차로 여부
        public static final String CENTER_YN = "CENTER_YN";
        // X 좌표
        public static final String X = "X";
        // Y 좌표
        public static final String Y = "Y";
        // 지역명
        public static final String REGION_NAME = "REGION_NAME";
        // 모바일번호
        public static final String MOBILE_NO = "MOBILE_NO";
        // 지역코드
        public static final String DISTRICT_CD = "DISTRICT_CD";

        public enum Columns{
            STATION_ID, STATION_NM, CENTER_ID, CENTER_YN, X, Y, REGION_NAME, MOBILE_NO, DISTRICT_CD
        }

        public static final String CREATE_TABLE_SQL = "CREATE TABLE " +
                TABLE_NAME + " " +
                "(" +
                STATION_ID + " " + PRIMARY_KEY_TYPE + ", " +
                STATION_NM + " " + TEXT_TYPE + ", " +
                CENTER_ID + " " + INTEGER_TYPE + ", " +
                CENTER_YN + " " + TEXT_TYPE + ", " +
                X + " " + TEXT_TYPE + ", " +
                Y + " " + TEXT_TYPE + ", " +
                REGION_NAME + " " + TEXT_TYPE + ", " +
                MOBILE_NO + " " + TEXT_TYPE + ", " +
                DISTRICT_CD + " " + TEXT_TYPE +
                ")";

        public static String[] getColumnNames() {
            return Arrays.toString(Columns.values()).replaceAll("^.|.$", "").split(", ");
        }
    }

    public static abstract class RouteStation implements BaseColumns {
        public static final String TABLE_NAME = "ROUTE_STATION";

        // 노선 ID
        public static final String ROUTE_ID = "ROUTE_ID";
        // 정류소 ID
        public static final String STATION_ID = "STATION_ID";
        // 상하행 구분
        public static final String UPDOWN = "UPDOWN";
        // 정류소 순번
        public static final String STA_ORDER = "STA_ORDER";
        // 노선 명
        public static final String ROUTE_NM = "ROUTE_NM";
        // 정류소 명
        public static final String STATION_NM = "STATION_NM";

        public enum Columns {
            ROUTE_ID, STATION_ID, UPDOWN, STA_ORDER, ROUTE_NM, STATION_NM
        }

        public static final String CREATE_TABLE_SQL = "CREATE TABLE " +
                TABLE_NAME + " " +
                "(" +
                ROUTE_ID + " " + INTEGER_TYPE + ", " +
                STATION_ID + " " + INTEGER_TYPE + ", " +
                UPDOWN + " " + TEXT_TYPE + ", " +
                STA_ORDER + " " + TEXT_TYPE + ", " +
                ROUTE_NM + " " + TEXT_TYPE + ", " +
                STATION_NM + " " + TEXT_TYPE +
                ")";

        public static String[] getColumnNames() {
            return Arrays.toString(Columns.values()).replaceAll("^.|.$", "").split(", ");
        }
    }
}
