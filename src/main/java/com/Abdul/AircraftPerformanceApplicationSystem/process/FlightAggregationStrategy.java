package com.Abdul.AircraftPerformanceApplicationSystem.process;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class FlightAggregationStrategy  implements AggregationStrategy
{
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange)
    {
        if(oldExchange==null)
        {
            return  newExchange;  // first message
        }

        //
        oldExchange.getIn().setBody(newExchange.getIn().getBody());
        return oldExchange;
    }
}
