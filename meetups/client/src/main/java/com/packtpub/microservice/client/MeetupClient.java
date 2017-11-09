package com.packtpub.microservice.client;

import rx.Observable;

import java.util.Set;

/**
 * @author khaled
 */
public interface MeetupClient {
    Observable<Boolean> create(String name,String typez);

    Observable<Set<String>> listByType(String typez);
}
