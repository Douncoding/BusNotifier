package com.douncoding.busnotifier.data.repository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 공공데티터 포털의 데이터 파서
 * 새로운 줄 = ^
 * 컬럼간 구분 = |
 */
public abstract class RawDataReader {

    private InputStream inputStream;
    private boolean isColumnsHeader = true;

    public RawDataReader(InputStream inputStream) {
        this.inputStream = inputStream;
        parse();
    }

    private void parse() {
        try {
            InputStreamReader reader = new InputStreamReader(inputStream, "euc-kr");

            int charAt;
            StringBuilder builder = new StringBuilder();
            while ((charAt = reader.read()) != -1) {
                if (charAt == '^') {

                    // 처음 행은 컬럼이름이 들어가 있기 때문에 파싱대상에서 제외한다.
                    if (isColumnsHeader) {
                        isColumnsHeader = false;
                    } else {
                        parseToRecord(builder.toString());
                    }

                    builder.setLength(0);
                } else {
                    builder.append((char)charAt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onFinished();
    }

    private void parseToRecord(String line) {
        List<String> values = new LinkedList<>();

        StringTokenizer tokenizer = new StringTokenizer(line, "|");
        while (tokenizer.hasMoreElements()) {
            values.add(tokenizer.nextToken());
        }
        onPostLineParse(values);
    }

    abstract void onPostLineParse(List<String> values);

    abstract void onFinished();
}
