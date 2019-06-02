package com.smarthome.SmartHome.service.impl.fcm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.SmartHome.controller.model.FcmPush;
import com.smarthome.SmartHome.error.FcmError;
import com.smarthome.SmartHome.interceptor.HeaderRequestInterceptor;
import com.smarthome.SmartHome.service.FcmService;
import com.smarthome.SmartHome.service.FcmTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FcmServiceImpl implements FcmService {
    private final Logger log = LoggerFactory.getLogger("FcmService");

    @Value("${fcm.server.key}")
    private String FIREBASE_SERVER_KEY;
    @Value("${fcm.server.url}")
    private String FIREBASE_API_URL;

    @Autowired
    protected FcmTokenService fcmTokenService;

    @Async("threadPoolTaskExecutor")
    @Override
    public void sendPushNotificationsToUsers(FcmPush push) {
        List<CompletableFuture<String>> futureList = new ArrayList<>();

        for (String fcmToken : fcmTokenService.getAllTokens()) {
            push.setTo(fcmToken);
            HttpEntity<FcmPush> entity = new HttpEntity<>(push);
            futureList.add(sendRequest(entity));
        }

        for (CompletableFuture<String> result : futureList) {
            CompletableFuture.allOf(result).join();
            checkResponse(result);
        }
    }

    private void checkResponse(CompletableFuture<String> pushNotification) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String firebaseResponse = pushNotification.get();
            JsonNode responseObj = objectMapper.readTree(firebaseResponse);
            int success = responseObj.get("success").asInt();

            if (success == 0) {
                JsonNode resultNode = responseObj.get("results").get(0);
                log.error("FCMPush result error: " + resultNode.get("error").asText());
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            log.error(FcmError.CANT_SEND_MESSAGE_NOTIFICATION.getErrorMessage());
        }
    }


    private CompletableFuture<String> sendRequest(HttpEntity<FcmPush> entity) {
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String fcmResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
        return CompletableFuture.completedFuture(fcmResponse);
    }
}
