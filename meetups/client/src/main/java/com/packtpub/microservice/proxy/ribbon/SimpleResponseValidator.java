package com.packtpub.microservice.proxy.ribbon;

import com.netflix.ribbon.ResponseValidator;
import com.netflix.ribbon.ServerError;
import com.netflix.ribbon.UnsuccessfulResponseException;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;

/**
 * @author khaled
 *
 * Ribbon always requires ResponseValidator
 */
public class SimpleResponseValidator implements ResponseValidator<HttpClientResponse<ByteBuf>> {
    @Override
    public void validate(HttpClientResponse response) throws UnsuccessfulResponseException, ServerError {

    }
}
