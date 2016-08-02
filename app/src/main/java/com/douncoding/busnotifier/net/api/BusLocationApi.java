package com.douncoding.busnotifier.net.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.douncoding.busnotifier.data.BusLocation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 클라우드 저장소로 부터 데이터를 가지고 와서 {@link com.douncoding.busnotifier.data.BusLocation} 객체로
 * 전환한다.
 */
public class BusLocationApi {
    static final String API_BASE_URL = "http://openapi.gbis.go.kr/ws/rest/buslocationservice";
    static final String API_KEY = "L5PwtFiwqFt%2FyoNyAhrt%2FJr08hfwqCGT6oriNPIzVwGdZbt1oLP9HqBlJEiO2PCpTWk4hPWnBhS%2BwPnTSmxpYw%3D%3D";

    private static class BusLocationXmlMapper {
        private static final String TAG = "busLocationList";
        private static final String ROUTE_ID = "routeId";
        private static final String STATION_ID = "stationId";
        private static final String STATION_SEQ = "stationSeq";
        private static final String END_BUS = "endBus";
        private static final String LOW_PLATE = "lowPlate";
        private static final String PLATE_NO = "plateNo";
        private static final String REMAIN_SEAT_CNT = "remainSeatCnt";

        // Convert XML-To-BusLocation
        static BusLocation convertXmlToBusLocation(Element data) {
            Node idRoute = data.getElementsByTagName(ROUTE_ID).item(0);
            Node idStation = data.getElementsByTagName(STATION_ID).item(0);
            Node seqStation = data.getElementsByTagName(STATION_SEQ).item(0);
            Node endBus = data.getElementsByTagName(END_BUS).item(0);
            Node lowPlate = data.getElementsByTagName(LOW_PLATE).item(0);
            Node plateNo = data.getElementsByTagName(PLATE_NO).item(0);
            Node seatCnt = data.getElementsByTagName(REMAIN_SEAT_CNT).item(0);

            BusLocation busLocation = new BusLocation();
            busLocation.setIdRoute(Integer.parseInt(idRoute.getTextContent()));
            busLocation.setIdStation(Integer.parseInt(idStation.getTextContent()));
            busLocation.setSeqStation(Integer.parseInt(seqStation.getTextContent()));
            busLocation.setEndBus(Integer.parseInt(endBus.getTextContent()) == 1);
            busLocation.setLowPlate(Integer.parseInt(lowPlate.getTextContent()) == 1);
            busLocation.setPlateNo(plateNo.getTextContent());
            busLocation.setRemainSeatCnt(Integer.parseInt(seatCnt.getTextContent()));
            return busLocation;
        }
    }

    private BusLocationApi() {

    }

    /**
     * 버스 위치 정보 요청
     * @param idRoute 노선 ID
     * @param callback 콜백
     */
    public static void getBusLocationList(final int idRoute, final OnCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String url = API_BASE_URL + "?" + "serviceKey=" + API_KEY + "&routeId=" + idRoute;

                try {
                    return ApiConnection.createGET(url).requestSyncCall();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                try {
                    callback.onResponse(parseToBusLocation(string));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public interface OnCallback {
        void onResponse(List<BusLocation> list);
    }

    /**
     * @param xml API 결과 값이며, XML 형식
     * @return 버스 위치정보 목록
     */
    private static List<BusLocation> parseToBusLocation(String xml) throws Exception {
        List<BusLocation> busLocationList = new LinkedList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(xml));
        Document document = documentBuilder.parse(is);
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName(BusLocationXmlMapper.TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element)nodeList.item(i);
            BusLocation busLocation = BusLocationXmlMapper.convertXmlToBusLocation(element);

            busLocationList.add(busLocation);
        }

        return busLocationList;
    }
}
