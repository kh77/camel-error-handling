package com.sm.routes;

import com.sm.service.CheckService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

//@Component
public class OnExceptionRoute extends RouteBuilder {

    final static AtomicInteger counter = new AtomicInteger(1);

    @Override
    public void configure() {
        // best way to use onException
        onException(Exception.class)
                .routeId("OnExceptionError")
                .log(ERROR, "Same class catch Exception: ${exception}")
                .handled(true)
                .to("direct:exceptionHandler");

        from("timer:onExceptionTimer?period=1000")
                .routeId("onExceptionRoute")
                .process(exchange -> exchange.getIn().setBody(new Date()))
                .choice()
                    .when(e -> counter.incrementAndGet() % 2 == 0)
                        .bean(CheckService.class, "pass")
                    .otherwise()
                        .bean(CheckService.class, "fail")
                    .end()
                .log(INFO, ">> ${header.firedTime} >> ${body}")
                .to("log:reply");
    }
}
