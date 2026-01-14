package com.Abdul.AircraftPerformanceApplicationSystem.routes;

import com.Abdul.AircraftPerformanceApplicationSystem.models.FlightEvent;
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
                .filter(exchange -> {
                    FlightEvent event = exchange.getIn().getBody(FlightEvent.class);
                    return event.getFuel()>100 &&
                            event.getSpeed()>200 &&
                            event.getFlightId()!=null;
                })

                // multicast
                .multicast().parallelProcessing()
                .log("processing data for multicast stage : &{body}")
                .to(
                        "direct:logRoute",
                        "direct:dbSaveRoute"
                ).end();
    }
}
