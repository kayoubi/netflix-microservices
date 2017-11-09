package com.packtpub.microservice.proxy.rx;

import com.packtpub.microservice.client.MeetupClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import rx.Observable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author khaled
 */
public class RxMeetupClientImpl implements MeetupClient {
    private final MeetupServiceFeignProxy proxy;

    public RxMeetupClientImpl() {
        proxy = Feign.builder()
            .decoder(new GsonDecoder())
            .target(MeetupServiceFeignProxy.class, "http://localhost:9090/");
    }

    @Override
    public Observable<Boolean> create(String name, String typez) {
        if (null == name || "".equals(name)) {
            throw new IllegalArgumentException("Meetup name cannot be null");
        }
        if (null == typez || "".equals(typez)) {
            throw new IllegalArgumentException("Meetup type cannot be null");
        }
        return Observable.just(proxy.create(name, typez));
    }

    @Override
    public Observable<Set<String>> listByType(String typez) {
        if(null==typez || "".equals(typez)) {
            throw new IllegalArgumentException("Meetup type cannot be null");
        }

        String[] rawResult = proxy.listByType(typez).meetups;
        Set<String> set = new HashSet<>(Arrays.asList(rawResult));
        return Observable.just(set);
    }
}
