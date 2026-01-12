package com.Abdul.AircraftPerformanceApplicationSystem.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestConfiguration  extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {
        restConfiguration()
                .component("servlet")
                .contextPath("/api")
                .bindingMode(RestBindingMode.json);
    }
}
