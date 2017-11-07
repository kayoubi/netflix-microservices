package com.packtpub.microservice;

import com.packtpub.microservice.proxy.rx.RxMeetupClient;
import com.packtpub.microservice.proxy.rx.RxMeetupClientImpl;

/**
 * @author khaled
 */
public class ClientRunner {
    public static void main(String[] args) {
        RxMeetupClient proxy = new RxMeetupClientImpl();
        proxy.create("hello", "javabk");
        proxy.create("fuck", "javabk");
        proxy.listByType("javabk").forEach(System.out::println);
    }
}
