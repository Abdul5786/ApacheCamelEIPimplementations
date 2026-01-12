package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class AggregatorRoutes extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {


        //  flightId / body missing
        onException(NullPointerException.class, IllegalArgumentException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "Aggregation failed: missing body or flightId")
                .to("file:data/aggregation-bad-data");


        // mandatory routes ka data nahi aaya (partial aggregation)

        onException(IllegalStateException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "Incomplete aggregation (mandatory sources missing)")
                .to("file:data/aggregation-incomplete");


        from("direct:aggregate")
                .routeId("aggregation-route")
                .log("recived data succesfully from sources with flight id :  ${body.flightId}")
                .aggregate(simple("${body.flightId}"), new GroupedExchangeAggregationStrategy())
                .completionSize(2)
                .completionTimeout(500000)
                .log("last step calling http call ")
                .process("performanceAggregatorProcessor")
                .to("direct:httpCall");
    }
}
