package com.hulahoop.gateway.service;

import com.hulahoop.gateway.config.ServiceUrlConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 🎯 GatewayService
 * - intent 에 따라 Bike / Movie / LLM 서비스 중 하나로 분기하여 호출
 * - ServiceUrlConfig 의 하위 url 속성을 기반으로 대상 서비스 주소 결정
 */
@Service
public class GatewayService {

    private final RestTemplate restTemplate;
    private final ServiceUrlConfig serviceUrlConfig;

    public GatewayService(RestTemplate restTemplate, ServiceUrlConfig serviceUrlConfig) {
        this.restTemplate = restTemplate;
        this.serviceUrlConfig = serviceUrlConfig;

        // ✅ 확인용 로그
        System.out.println("[GatewayService] Loaded URLs:");
        if (serviceUrlConfig.getBike() != null)
            System.out.println(" - Bike  : " + serviceUrlConfig.getBike().getUrl());
        if (serviceUrlConfig.getMovie() != null)
            System.out.println(" - Movie : " + serviceUrlConfig.getMovie().getUrl());
        if (serviceUrlConfig.getLlm() != null)
            System.out.println(" - LLM   : " + serviceUrlConfig.getLlm().getUrl());
    }

    /**
     * 🎯 intent 기반으로 서비스 분기 처리
     */
    public Map<String, Object> routeIntent(String intent, Map<String, Object> data) {
        String targetUrl;

        // intent 기반 라우팅
        if (intent.startsWith("bike_")) {
            targetUrl = serviceUrlConfig.getBike().getUrl() + "/api/bikes/dispatch";
        } else if (intent.startsWith("movie_")) {
            targetUrl = serviceUrlConfig.getMovie().getUrl() + "/api/movies/dispatch";
        } else if (intent.startsWith("llm_")) {
            targetUrl = serviceUrlConfig.getLlm().getUrl() + "/api/llm/dispatch";
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Unknown intent: " + intent);
            return error;
        }

        // 내부 서비스로 전달할 payload 구성
        Map<String, Object> request = new HashMap<>();
        request.put("intent", intent);
        request.put("data", data);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(targetUrl, request, Map.class);
            return response.getBody();
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to call " + targetUrl + ": " + e.getMessage());
            return error;
        }
    }
}
