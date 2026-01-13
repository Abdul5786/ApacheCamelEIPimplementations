package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class MulticastRoute extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {

        from("kafka:multicast-data?brokers=localhost:9092&groupId=multicast-group")
                .routeId("multiCast")
                .log("kafka received: ${body}")
                .process("acarsTranslatorProcessor")

                // multicast
                .multicast().parallelProcessing()
                .log("processing data for multicast stage : &{body}")
                .to(
                        "direct:logRoute",
                        "direct:dbSaveRoute"
                ).end();
    }
}
