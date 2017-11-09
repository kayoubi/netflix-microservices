package com.packtpub.microservice.proxy.ribbon;

import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.RibbonResponse;
import com.netflix.ribbon.http.HttpRequestTemplate;
import com.netflix.ribbon.http.HttpResourceGroup;
import com.packtpub.microservice.client.MeetupsByType;
import com.packtpub.microservice.client.MeetupClient;
import io.netty.buffer.ByteBuf;
import rx.Observable;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.packtpub.microservice.proxy.ribbon.RibbonUtil.getServerIP;
import static com.packtpub.microservice.proxy.ribbon.RibbonUtil.mapper;

/**
 * @author khaled
 */
public class RibbonMeetupClientImpl implements MeetupClient {

    @Override
    public Observable<Boolean> create(String name, String type) {
        HttpRequestTemplate<ByteBuf> apiTemplate =
        resourceGroup()
            .newTemplateBuilder("apiCall")
            .withMethod("PUT")
            .withUriTemplate("/meetup?type=" + type + "&name=" + name)
            .withFallbackProvider(new DefaultFallback())
            .withResponseValidator(new SimpleResponseValidator())
            .build();

        return execute(Boolean.class, apiTemplate);
    }

    @Override
    public Observable<Set<String>> listByType(String typez) {
        HttpRequestTemplate<ByteBuf> apiTemplate =
            resourceGroup()
                .newTemplateBuilder("apiCall")
                .withMethod("GET")
                .withUriTemplate("/meetup?type=" + typez)
                .withFallbackProvider(new DefaultFallback())
                .withResponseValidator(new SimpleResponseValidator())
                .build();

        Observable<MeetupsByType> result = execute(MeetupsByType.class, apiTemplate);
        return Observable.just(new HashSet<>(Arrays.asList(result.toBlocking().single().meetups)));
    }

    private HttpResourceGroup resourceGroup() {
        return Ribbon.createHttpResourceGroup(
            "apiGroup",
            ClientOptions.create()
                .withMaxAutoRetriesNextServer(1)
                .withConfigurationBasedServerList(getServerIP("microservice"))
        );
    }

    private <T> Observable<T> execute(Class<T> clazz,HttpRequestTemplate<ByteBuf> apiTemplate){
        RibbonResponse<ByteBuf> result = apiTemplate
            .requestBuilder()
            .withHeader("client", "client-microservice")
            .build()
            .withMetadata()
            .execute();

        ByteBuf buf = result.content();
        String json = buf.toString(Charset.forName("UTF-8"));

        try {
            return Observable.just(mapper.readValue(json, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
