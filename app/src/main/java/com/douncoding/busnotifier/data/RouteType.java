package com.douncoding.busnotifier.data;

/**
 * 코드형 버스타입을 사용자 식별가능한 문자열 형태변환 클래스
 */
public class RouteType {
    private int routeType;

    private String type;
    private String detailType;

    public RouteType(int routeType) {
        this.routeType = routeType;
        parseToType(this.routeType);
    }

    public String getDetailType() {
        return detailType;
    }

    public String getType() {
        return type;
    }

    public void parseToType(int routeType) {
        switch (routeType) {
            case 11:
                type = "시내버스";
                detailType = "직행좌석형시내버스";
                break;
            case 12:
                type = "시내버스";
                detailType = "좌석형시내버스";
                break;
            case 13:
                type = "시내버스";
                detailType = "일반형시내버스";
                break;
            case 21:
                type = "농어촌버스";
                detailType = "직행좌석형농어촌버스 ";
                break;
            case 22:
                type = "농어촌버스";
                detailType = "좌석형농어촌버스";
                break;
            case 23:
                type = "농어촌버스";
                detailType = "일반형농어촌버스";
                break;
            case 30:
                type = "마을버스";
                detailType = "마을버스";
                break;
            case 41:
                type = "시외버스";
                detailType = "고속형시외버스";
                break;
            case 42:
                type = "시외버스";
                detailType = "좌석형시외버스";
                break;
            case 43:
                type = "시외버스";
                detailType = "직행좌석형농어촌버스";
                break;
            case 51:
                type = "공항버스";
                detailType = "리무진형공항버스";
                break;
            case 52:
                type = "공항버스";
                detailType = "좌석형공항버스";
                break;
            case 53:
                type = "공항버스";
                detailType = "일반형공항버스";
                break;
            default:
                detailType = "알수없음";
                break;
        }
    }
}
