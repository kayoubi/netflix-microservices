package com.packtpub.microservice.dao;


import com.packtpub.microservice.service.Meetup;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisDataException;
import rx.Observable;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * @author khaled
 */
public class RedisMeetupDAO implements MeetupDAO {
    private final JedisPool pool;

    @Inject
    public RedisMeetupDAO(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    public Observable<Boolean> create(Meetup m) {
        try (Jedis jedis = pool.getResource()) {
            HashMap<String, String> meetups = new HashMap<>();
            meetups.put(m.getName(), m.getTypez());
            jedis.hmset(m.getTypez(), meetups);
        }
        return Observable.just(true);
    }

    @Override
    public Observable<Set<String>> listByType(String typez) {
        try (Jedis jedis = pool.getResource()) {
            try {
                Set<String> result = jedis.hkeys(typez);
                return Observable.just(result);
            } catch (JedisDataException jde) {
                return Observable.just(Collections.emptySet());
            }
        }
    }
}
