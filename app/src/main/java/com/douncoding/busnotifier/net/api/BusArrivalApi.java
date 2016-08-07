package com.douncoding.busnotifier.net.api;

import android.os.AsyncTask;
import android.util.Log;

import com.douncoding.busnotifier.data.BusArrival;
import com.douncoding.busnotifier.data.BusLocation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 클라우드 저장소로 부터 데이터를 가지고 와서 {@link BusLocation} 객체로
 * 전환한다.
 */
public class BusArrivalApi {
    static final String API_BASE_URL = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station";
    static final String API_KEY = "L5PwtFiwqFt%2FyoNyAhrt%2FJr08hfwqCGT6oriNPIzVwGdZbt1oLP9HqBlJEiO2PCpTWk4hPWnBhS%2BwPnTSmxpYw%3D%3D";

    private static class BusArrivalXmlMapper {
        private static final String TAG = "busArrivalList";
        private static final String ROUTE_ID = "routeId";
        private static final String LOCATION_NO = "locationNo";//1/2
        private static final String PREDICT_TIME = "predictTime";//1/2
        private static final String FLAG = "flag";

        // Convert XML-To-BusLocation
        static BusArrival convertXmlToBusArrival(Element data) {
            BusArrival busArrival = new BusArrival();

            Node flag = data.getElementsByTagName(FLAG).item(0);
            Node idRoute = data.getElementsByTagName(ROUTE_ID).item(0);

            busArrival.setIdRoute(Integer.parseInt(idRoute.getTextContent()));
            busArrival.setFlag(flag.getTextContent());

            int[] locationNo = new int[2];
            int[] predictTime = new int[2];
            for (int i = 0; i < 2; i++) {
                String location = data.getElementsByTagName(LOCATION_NO+String.valueOf(i+1))
                        .item(0)
                        .getTextContent();
                String time  = data.getElementsByTagName(PREDICT_TIME+String.valueOf(i+1))
                        .item(0)
                        .getTextContent();
                locationNo[i] = location.equals("")?0:Integer.parseInt(location);
                predictTime[i] = time.equals("")?0:Integer.parseInt(time);
            }

            busArrival.setLocationNo(locationNo);
            busArrival.setPredictTime(predictTime);
            return busArrival;
        }
    }

    /**
     * 버스 도착 정보 요청
     * @param idStation 정류소 ID
     * @param callback 콜백
     */
    public static void getBusArrivalList(final int idStation, final OnCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String url = API_BASE_URL + "?" + "serviceKey=" + API_KEY + "&stationId=" + idStation;

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
                    callback.onResponse(parseToBusArrival(string));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public interface OnCallback {
        void onResponse(List<BusArrival> list);
    }

    /**
     * @param xml API 결과 값이며, XML 형식
     * @return 버스 위치정보 목록
     */
    private static List<BusArrival> parseToBusArrival(String xml) throws Exception {
        List<BusArrival> busArrivalList = new LinkedList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(xml));
        Document document = documentBuilder.parse(is);
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName(BusArrivalXmlMapper.TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element)nodeList.item(i);
            BusArrival busLocation = BusArrivalXmlMapper.convertXmlToBusArrival(element);
            busArrivalList.add(busLocation);
        }

        return busArrivalList;
    }
}
