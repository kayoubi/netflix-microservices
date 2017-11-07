package com.packtpub.microservice.dao;

import com.packtpub.microservice.service.Meetup;
import rx.Observable;

import java.util.Set;

/**
 * @author khaled
 */
public interface MeetupDAO {
    public Observable<Boolean> create(Meetup m);

    public Observable<Set<String>> listByType(String typez);
}
