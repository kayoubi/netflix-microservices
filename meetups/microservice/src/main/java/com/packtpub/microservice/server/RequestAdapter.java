package com.packtpub.microservice.server;

import com.google.inject.Injector;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;

/**
 * @author khaled
 */
public interface RequestAdapter {
    Observable<Void> handle(HttpServerRequest request, HttpServerResponse response);

    void setInjector(Injector injector);
}
