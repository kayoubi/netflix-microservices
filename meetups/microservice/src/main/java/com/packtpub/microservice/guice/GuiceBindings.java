package com.packtpub.microservice.guice;

import com.google.inject.AbstractModule;
import com.netflix.config.ConfigurationManager;
import com.packtpub.microservice.healthchecker.HealthcheckResource;
import com.packtpub.microservice.dao.MeetupDAO;
import com.packtpub.microservice.dao.RedisMeetupDAO;
import com.packtpub.microservice.rest.MeetupResource;
import com.packtpub.microservice.server.RequestAdapter;
import com.packtpub.microservice.server.RxNettyServiceAdapter;
import com.packtpub.microservice.service.MeetupService;
import com.packtpub.microservice.service.MeetupServiceImpl;
import netflix.karyon.health.HealthCheckHandler;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author khaled
 */
public class GuiceBindings extends AbstractModule {
    @Override
    protected void configure() {
        bind(HealthCheckHandler.class).toInstance(new HealthcheckResource());
        bind(RequestAdapter.class).to(RxNettyServiceAdapter.class);

        bind(MeetupResource.class).asEagerSingleton();
        bind(MeetupService.class).to(MeetupServiceImpl.class);
        bind(MeetupDAO.class).to(RedisMeetupDAO.class);

        bind(JedisPool.class).toInstance(
            new JedisPool(
                new JedisPoolConfig(),
                ConfigurationManager.getConfigInstance().getString("redis_ip","localhost")
                //this read the property of Archaius from "environment" in docker-compose
            )
        );

    }
}
