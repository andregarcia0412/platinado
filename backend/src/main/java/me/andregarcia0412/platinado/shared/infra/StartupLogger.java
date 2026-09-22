package me.andregarcia0412.platinado.shared.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {
    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    @Value("${server.port}")
    private String port;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        logger.info("\uD83D\uDE80 Server running on port {}", port);
        logger.info("\uD83D\uDCDD Swagger UI docs on: http://localhost:{}/swagger-ui/index.html", port);
    }


}
