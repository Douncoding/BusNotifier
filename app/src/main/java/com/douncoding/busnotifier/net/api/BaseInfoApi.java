package com.douncoding.busnotifier.net.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.douncoding.busnotifier.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.nio.CharBuffer;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by douncoding on 16. 7. 29..
 */
public class BaseInfoApi {
    private static final String TAG = BaseInfoApi.class.getSimpleName();

    final String API_BASE_URL = "http://openapi.gbis.go.kr/ws/rest/baseinfoservice";
    final String API_KEY = "L5PwtFiwqFt%2FyoNyAhrt%2FJr08hfwqCGT6oriNPIzVwGdZbt1oLP9HqBlJEiO2PCpTWk4hPWnBhS%2BwPnTSmxpYw%3D%3D";

    final String AREA_VERSION = "areaVersion";
    final String AREA_DOWNLOAD_URL = "areaDownloadUrl";

    final String ROUTE_VERSION = "routeVersion";
    final String ROUTE_DOWNLOAD_URL = "routeDownloadUrl";

    final String STATION_VERSION = "stationVersion";
    final String STATION_DOWNLOAD_URL = "stationDownloadUrl";

    final String ROUTE_STATION_VERSION = "routeStationVersion";
    final String ROUTE_STATION_DOWNLOAD_URL = "routeStationDownloadUrl";

    HashMap<String, String> mBaseInfoStorage = new HashMap<>();

    Context context;

    public BaseInfoApi(Context context) {
        this.context = context;
        test();
    }

    private void test() {

    }

    private void getBaseInfoStorageURL() {
        if (!isThereInternetConnection())
            throw new RuntimeException("네트워크 연결 실패:");

        try {
            String response = getBaseInfoItems();
            if (response == null) {
                throw new RuntimeException("공공데이터 포털 API 요청 실패: 인증확인 필요");
            }

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(response));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBaseInfoItems() throws MalformedURLException {
        String url = API_BASE_URL + "?" + "serviceKey=" + API_KEY;
        return ApiConnection.createGET(url).requestSyncCall();
    }

    /**
     * 네트워크 연결상태 확인
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager)this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
