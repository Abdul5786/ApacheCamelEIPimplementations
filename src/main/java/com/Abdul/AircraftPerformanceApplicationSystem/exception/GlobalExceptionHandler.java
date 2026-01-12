package com.Abdul.AircraftPerformanceApplicationSystem.exception;


import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.stereotype.Component;

@Component
public class GlobalExceptionHandler extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {

      // FOR UNKNOWN ERRORS
        errorHandler(
                deadLetterChannel("file:data/error")
                        .maximumRedeliveries(3)
                        .redeliveryDelay(2000)
                        .retryAttemptedLogLevel(LoggingLevel.WARN)
        );


         // REST / HTTP failures
        onException(HttpOperationFailedException.class)
                .handled(true)
                .log(LoggingLevel.ERROR,
                        "REST API failed | Status=${exception.statusCode}")
                .to("file:data/rest-failure");

          // Aggregation / illegal state
        onException(IllegalStateException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "Aggregation failed")
                .to("file:data/aggregation-error");
    }
}
