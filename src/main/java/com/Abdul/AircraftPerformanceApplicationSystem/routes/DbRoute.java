package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class DbRoute extends RouteBuilder
{
    @Override
    public void configure() throws Exception {
        from("direct:dbSaveRoute")
                .routeId("db-save-route")
                .log("Saving to DB: ${body}")

                .setBody(simple(
                        "INSERT INTO flight_event(flight_id, fuel, speed, source, timestamp) " +
                                "VALUES ('${body.flightId}', ${body.fuel}, ${body.speed}, '${body.source}', ${body.timestamp})"
                ))

                .to("jdbc:dataSource")

                .log("DB save successful");
    }
}
