package com.Abdul.AircraftPerformanceApplicationSystem.process;


import com.Abdul.AircraftPerformanceApplicationSystem.models.FlightEvent;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PerformanceAggregatorProcessor  implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        List<FlightEvent> events = exchange.getIn().getBody(List.class);

        FlightEvent flightEvent = new FlightEvent();
        flightEvent.setFlightId(events.get(0).getFlightId());
        flightEvent.setSource("AGGREGATED");
        flightEvent.setTimestamp(System.currentTimeMillis());

        for (FlightEvent e : events) {

            if ("ACARS".equals(e.getSource())) {
                if (e.getFuel() != null) flightEvent.setFuel(e.getFuel());
                if (e.getSpeed() != null) flightEvent.setSpeed(e.getSpeed());
            }

            if ("WEATHER".equals(e.getSource())) {
                if (e.getWind() != null) flightEvent.setWind(e.getWind());
            }

            exchange.getIn().setBody(flightEvent);


        }

    }

}