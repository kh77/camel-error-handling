package com.sm.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.camel.LoggingLevel.ERROR;

@Component
public class CommonExceptionHandlerRoute extends RouteBuilder {

    public final static AtomicInteger COUNTER = new AtomicInteger(1);

    @Override
    public void configure() throws Exception {
        from("direct:exceptionHandler")
                .routeId("commonExceptionHandler")
                .log(ERROR, "----------Start Handling Exception----------------")
                .log(ERROR, "${body}")
                .log(ERROR, "----------End Handling Exception----------------");
    }
}
