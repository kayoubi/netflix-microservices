package com.packtpub.microservice.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;

/**
 * @author khaled
 */
class RxNettyServer {
    private Integer port = 9090;
    private Module[] modules;
    private RequestAdapter adapter;


    RxNettyServer withPort(Integer port){
        this.port = port;
        return this;
    }

    RxNettyServer withModules(Module[] modules){
        this.modules = modules;
        return this;
    }

    void start(){
        Injector injector = Guice.createInjector(modules);
        this.adapter = injector.getInstance(RequestAdapter.class);
        this.adapter.setInjector(injector);

        HttpServer server = RxNetty.createHttpServer(
            port,
            (req, resp) -> adapter.handle(req, resp)
        );
        server.startAndWait();
    }
}
