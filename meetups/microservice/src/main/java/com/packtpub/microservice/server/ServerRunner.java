package com.packtpub.microservice.server;

import com.google.inject.Module;
import com.netflix.config.ConfigurationManager;
import com.packtpub.microservice.guice.GuiceBindings;
import netflix.karyon.eureka.KaryonEurekaModule;

import java.util.ArrayList;
import java.util.List;


/**
 * @author khaled
 */
public class ServerRunner {
    public static void main(String[] args) throws Exception {
        List<Module> modules = new ArrayList<>();
        modules.add(new GuiceBindings());

        if (ConfigurationManager.getConfigInstance().getBoolean("eureka_registry",true)) {
//            modules.add(new KaryonEurekaModule());
        }

        new RxNettyServer().withPort(9090).withModules(modules.toArray(new Module[modules.size()])).start();
    }
}
