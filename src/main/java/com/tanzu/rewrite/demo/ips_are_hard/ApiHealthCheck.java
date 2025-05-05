package com.tanzu.rewrite.demo.ips_are_hard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

public class ApiHealthCheck {
    private static final Logger logger = LoggerFactory.getLogger(ApiHealthCheck.class);

    private boolean healthy = false;
    private TimerTask timerTask;
    public boolean healthy(){
        return healthy;
    }

    public void startChecking(int msInterval){
        if(timerTask != null){
            timerTask.cancel();
        }
        var timer = new Timer();
        var task = new TimerTask(){
            public void run() {
                healthy = healthCheck();
            }
        };
        timer.scheduleAtFixedRate(task, 0, msInterval);
        timerTask = task;
    }

    public void stopChecking(){
        if(timerTask != null){
            timerTask.cancel();
        }
        timerTask = null;
    }

    @Value("${server.port}")
    int healthCheckPort;

    String healthCheckHost = "127.0.0.1";

    String getHealthCheckEndpoint(){
        return String.format("http://%s:%s/ping", healthCheckHost, healthCheckPort);
    }

    public boolean healthCheck() {
        try {
            var template = new RestTemplate();
            var endpoint = getHealthCheckEndpoint();
            var response = template.getForEntity(endpoint, String.class);
            logger.info("Healthcheck: {}; status: {}", endpoint, response.getStatusCode());
            return response.getStatusCode() == HttpStatus.OK;
        }catch(Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
}
