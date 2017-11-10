package com.packtpub.microservice;

import com.packtpub.microservice.proxy.ribbon.RibbonMeetupClientImpl;
import com.packtpub.microservice.client.MeetupClient;
import com.packtpub.microservice.proxy.rx.RxMeetupClientImpl;

/**
 * @author khaled
 */
public class ClientRunner {
    public static void main(String[] args) {
        MeetupClient ribbon = new RibbonMeetupClientImpl();
        ribbon.create("foo", "bar");
        ribbon.listByType("bar").subscribe(System.out::println);

        MeetupClient proxy = new RxMeetupClientImpl();
        proxy.create("hello", "javabk");
        proxy.create("fuck", "javabk");
        proxy.listByType("javabk").forEach(System.out::println);
    }
}
