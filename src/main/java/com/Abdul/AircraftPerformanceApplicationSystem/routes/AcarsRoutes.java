package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class AcarsRoutes extends RouteBuilder
{


    @Override
    public void configure() throws Exception
    {



        from("kafka:acars-data?brokers=localhost:9092&groupId=acars-group")
                .routeId("acars-route")

                // validation
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    if (body == null || body.trim().isEmpty()) {
                        throw new IllegalArgumentException("Empty ACARS message");
                    }
                })
                .log(" message recieved from kafka passes to process")
                .wireTap("direct:audit")
                .log("send to wire tap for auditing")
                .process("acarsTranslatorProcessor")
                .log("processComplted--directedto--aggregate")
                .to("direct:aggregate");
    }
}
