package com.chung.board.beancycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url: "+url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 애플리케이션 시작시 호출.
    public void connect() {
        System.out.println("연결할 url = " + url);
    }

    //서비스중에 호출.
    public void call(String message) {
        System.out.println("url: "+url+"/message = " + message);
    }

    //서비스 종료시 호출.
    public void disconnect() {
        System.out.println("서비스 종료!");
    }

    @PostConstruct
    public void init() throws Exception {
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() throws Exception {
        disconnect();
    }
}
