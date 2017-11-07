package com.packtpub.microservice.service;

import rx.Observable;

import java.util.Set;

/**
 * @author khaled
 */
public interface MeetupService {
    Observable<Boolean> create(Meetup m);

    Observable<Set<String>> listByType(String typez);
}
