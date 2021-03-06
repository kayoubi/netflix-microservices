package com.packtpub.microservice.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

/**
 * @author khaled
 */
public class SimpleCommand extends HystrixObservableCommand<String> {
    private final String ops;

    public SimpleCommand(String ops) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.ops = ops;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(subscriber -> {
            try {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("{\"operation\" : " + ops + "}");
                    subscriber.onCompleted();
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
