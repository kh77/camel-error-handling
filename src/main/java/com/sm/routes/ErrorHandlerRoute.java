package com.sm.routes;

import com.sm.service.CheckService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.apache.camel.LoggingLevel.INFO;

@Component
public class ErrorHandlerRoute extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(

                deadLetterChannel("direct:exceptionHandler") // is same like log
                //.useOriginalMessage()
                .maximumRedeliveries(2) // retry 2 times
                        .redeliveryDelay(3000)
        );

        from("timer:errorHandlerTimer?period=10000")
                .routeId("errorHandlerRoute")
                .process(exchange -> exchange.getIn().setBody(new Date()))
                .choice()
                   .when(e -> CommonExceptionHandlerRoute.COUNTER.incrementAndGet() % 2 == 0)
                        .bean(CheckService.class, "pass")
                    .otherwise()
                        .bean(CheckService.class, "fail")
                    .end()
                .log(INFO, ">> ${header.firedTime} >> ${body}")
                .to("log:reply");
    }
}
