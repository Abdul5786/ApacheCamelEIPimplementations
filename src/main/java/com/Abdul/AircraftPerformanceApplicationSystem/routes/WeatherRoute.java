package com.Abdul.AircraftPerformanceApplicationSystem.routes;


import com.Abdul.AircraftPerformanceApplicationSystem.models.FlightEvent;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class WeatherRoute  extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {
        rest("/weather")
                .post()
                .to("direct:weather");

        from("direct:weather")
                .routeId("weather-route")

                .log("recivedWeather--data")


                // json to pojo
                .unmarshal().json(JsonLibrary.Jackson,FlightEvent.class)

                .process(exchange -> {
                    FlightEvent event = exchange.getIn().getBody(FlightEvent.class);

                    if(event.getFlightId()==null)
                    {
                        throw new IllegalArgumentException(" flight id missing in weather data");
                    }

                    event.setSource("weather");
                    event.setTimestamp(System.currentTimeMillis());
                })

                .log("directing to aggregate from http call")



                .to("direct:aggregate");


    }
}
