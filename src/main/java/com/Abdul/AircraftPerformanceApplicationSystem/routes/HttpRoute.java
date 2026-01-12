package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class HttpRoute extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {
        from("direct:httpCall")
                .setHeader("CamelHttpMethod", constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .to("http://localhost:8081/api/performance?bridgeEndpoint=true")
                .log(LoggingLevel.INFO, "REST API CALLED SUCCESSFULLY");
    }

    }

