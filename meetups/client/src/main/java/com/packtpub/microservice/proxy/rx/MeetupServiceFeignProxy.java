package com.packtpub.microservice.proxy.rx;

import com.packtpub.microservice.client.MeetupsByType;
import feign.Param;
import feign.RequestLine;

/**
 * @author khaled
 */
public interface MeetupServiceFeignProxy {
    @RequestLine("PUT /meetup?name={name}&type={type}")
    boolean create(@Param("name")String name, @Param("type")String type);

    @RequestLine("GET /meetup?type={type}")
    MeetupsByType listByType(@Param("type")String typez);
}
