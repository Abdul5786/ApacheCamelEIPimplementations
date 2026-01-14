package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class WireTapRoute extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {
           from("direct:audit")
                   .routeId("audit-route")
                   .log("wire tap started ")
                   .to("file:data/audit");
    }
}
