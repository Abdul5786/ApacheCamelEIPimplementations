package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class SplitterRoutes extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {
        from("kafka:splitter-data?brokers=localhost:9092&groupId=splitter-group").
                routeId("splitter-route")
                .log("original Message before using spliiter : &{body}")
                .split(body().tokenize(","))
                .log("after split -> ${body}")
                .end();
    }
}
