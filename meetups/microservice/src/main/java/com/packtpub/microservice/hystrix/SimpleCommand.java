package com.packtpub.microservice.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

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
        return null;
    }
}
