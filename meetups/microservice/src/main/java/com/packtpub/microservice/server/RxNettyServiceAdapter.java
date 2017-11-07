package com.packtpub.microservice.server;

import com.google.inject.Injector;
import com.packtpub.microservice.rest.MeetupResource;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import netflix.karyon.health.HealthCheckHandler;
import netflix.karyon.jersey.blocking.JerseyBasedRouter;
import org.apache.log4j.Logger;
import rx.Observable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khaled
 */
public class RxNettyServiceAdapter extends JerseyBasedRouter implements RequestAdapter {
    private final static Logger logger = Logger.getLogger(RxNettyServiceAdapter.class);
    private Injector injector;


    @Override
    public Observable<Void> handle(HttpServerRequest request, HttpServerResponse response) {
        return response.writeAndFlush(routeRequest(request, response).toBlocking().first().toString());
    }

    private Observable routeRequest(HttpServerRequest req, HttpServerResponse resp) {
        Observable ob;

        if (req.getUri().startsWith("/favicon.ico"))
            return Observable.just("");

        if(req.getUri().startsWith("/healthcheck")){
            HealthCheckHandler healthChecker = injector.getInstance(HealthCheckHandler.class);
            ob = Observable.just( healthChecker.getStatus() );
            logger.info("Healthcehcker called");
            return ob;
        }
//
//        if (req.getUri().startsWith("/ops")) {
//            ob = new SimpleCommand("[ \"GET /meetup\", \"PUT /meetup\"]").toObservable();
//            logger.info("Ops called");
//            return ob;
//        }
//
        if (req.getUri().startsWith("/meetup") && HttpMethod.PUT.equals(req.getHttpMethod())) {
            try {
                MeetupResource resource = injector.getInstance(MeetupResource.class);
                ob = resource.create(extractQueryParameter(req, "name"), extractQueryParameter(req, "type"));
                logger.info("Meetup called");
            } catch (IllegalArgumentException e) {
                ob = Observable.just(e.getMessage());
                resp.setStatus(HttpResponseStatus.BAD_REQUEST);
            } catch (Exception e) {
                ob = Observable.just(e.getMessage());
                resp.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            }
            return ob;
        }

        if (req.getUri().startsWith("/meetup") && HttpMethod.GET.equals(req.getHttpMethod())) {
            try {
                MeetupResource resource = injector.getInstance(MeetupResource.class);
                Observable<Set<String>> meetups = resource.listByType(extractQueryParameter(req, "type"));
                ob = transformSetToString(meetups);
                logger.info("Meetup called");
            } catch (IllegalArgumentException e) {
                ob = Observable.just(e.getMessage());
                resp.setStatus(HttpResponseStatus.BAD_REQUEST);
            } catch (Exception e) {
                ob = Observable.just(e.getMessage());
                resp.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            }
            return ob;
        }

        String msg = "Unhandled method called: " + req.getPath();
        ob = Observable.just(msg);
        logger.info(msg);
        return ob;
    }

    private Observable transformSetToString(Observable<Set<String>> ob) {
        return ob.map(t ->
             "{ \"meetups\": [" + t.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")) + "] }"
        );
    }

    private String extractQueryParameter(HttpServerRequest req, String name){
        List<String> result = ((List<String>)req.getQueryParameters().get(name));
        return (result == null) ? null : result.get(0);
    }

    @Override
    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
