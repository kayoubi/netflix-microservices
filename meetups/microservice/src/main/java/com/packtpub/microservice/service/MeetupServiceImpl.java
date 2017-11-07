package com.packtpub.microservice.service;

import com.packtpub.microservice.dao.MeetupDAO;
import rx.Observable;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author khaled
 */
public class MeetupServiceImpl implements MeetupService {
    private final MeetupDAO dao;

    @Inject
    public MeetupServiceImpl(MeetupDAO dao) {
        this.dao = dao;
    }

    @Override
    public Observable<Boolean> create(Meetup m) {
        validate(m);
        return dao.create(m);
    }

    @Override
    public Observable<Set<String>> listByType(String typez) {
        if (null == typez || "".equals(typez)) {
            throw new IllegalArgumentException("Meetup type cannot be null");
        }
        return dao.listByType(typez);
    }

    private void validate(Meetup m) {
        if (m == null) {
            throw new IllegalArgumentException("Meetup cannot be null");
        }
        if (null == m.getName() || "".equals(m.getName())) {
            throw new IllegalArgumentException("Meetup name cannot be null");
        }
        if (null == m.getTypez() || "".equals(m.getTypez())) {
            throw new IllegalArgumentException("Meetup type cannot be null");
        }
    }
}
