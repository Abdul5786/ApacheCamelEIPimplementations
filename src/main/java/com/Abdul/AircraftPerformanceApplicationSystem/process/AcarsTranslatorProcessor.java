package com.Abdul.AircraftPerformanceApplicationSystem.process;
import com.Abdul.AircraftPerformanceApplicationSystem.models.FlightEvent;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("acarsTranslatorProcessor")
public class AcarsTranslatorProcessor  implements Processor
{

    @Override
    public void process(Exchange exchange) throws Exception
    {
        String message  = exchange.getIn().getBody(String.class);

        System.out.println("----------------AcarsTranslatorProcessor---Started---------------");
        FlightEvent flightEvent = new FlightEvent();
        flightEvent.setFlightId(message.split(" ")[0].split("=")[1]);
        flightEvent.setFuel(Integer.parseInt(message.split(" ")[1].split("=")[1]));
        flightEvent.setSpeed(Integer.parseInt(message.split(" ")[2].split("=")[1]));
        flightEvent.setSource("ACARS");
        flightEvent.setTimestamp(System.currentTimeMillis());
        exchange.getIn().setBody(flightEvent);

        System.out.println(flightEvent.toString());
    }
}
