package com.packtpub.microservice.proxy.rx;

import rx.Observable;

import java.util.Set;

/**
 * @author khaled
 */
public interface RxMeetupClient {
    Observable<Boolean> create(String name,String typez);

    Observable<Set<String>> listByType(String typez);
}
