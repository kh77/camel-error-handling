package com.sm.routes;

import com.sm.service.CheckService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.camel.LoggingLevel.INFO;

//@Component
public class TryCatchRoute extends RouteBuilder {

    @Override
    public void configure() {
         from("timer:tryCatchTimer?period=1000")
                 .routeId("tryCatchRoute")
                .process(exchange -> exchange.getIn().setBody(new Date()))
                    .doTry()
                        .bean(CheckService.class, "fail")
                    .doCatch(Exception.class)
                        .to("direct:exceptionHandler") // CommonExceptionHandlerRoute
                    .end()
                .log(INFO, "Fired Time = ${header.firedTime} Body = ${body}")
                .to("log:reply");
    }
}
