package com.packtpub.microservice.proxy.ribbon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.guice.LifecycleInjectorBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khaled
 */
final class RibbonUtil {
    private RibbonUtil() { }

    private static final EurekaClient eurekaClient;
    static final ObjectMapper mapper = new ObjectMapper();

    static {
        LifecycleInjectorBuilder builder = LifecycleInjector.builder();
        Injector injector = builder.build().createInjector();
        eurekaClient = injector.getInstance(EurekaClient.class);
    }

    static String getServerIP(String microservice) {
        try {
            List<InstanceInfo> infos = eurekaClient.getApplication(microservice.toUpperCase()).getInstances();
            return infos.stream().map(info -> "http://" + info.getIPAddr() + ":" + info.getPort()).collect(Collectors.joining(","));
        } catch (Exception e) {
            throw new RuntimeException("Could not get Microservice IP:PORT. EX: " + e);
        }
    }
}
