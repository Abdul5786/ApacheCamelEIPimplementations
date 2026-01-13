package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LogRoute extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {
        from("direct:logRoute")
                .routeId("log-route")
                .log("log route ->>")
                .log("Logging FlightEvent data ->: ${body.flightId}, ${body.fuel}, ${body.speed}, ${body.source}, ${body.timestamp}}");
    }
}
