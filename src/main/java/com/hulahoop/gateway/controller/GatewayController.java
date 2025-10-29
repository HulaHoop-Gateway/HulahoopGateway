package com.hulahoop.gateway.controller;

import com.hulahoop.gateway.service.GatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 🚦 GatewayController
 * - AI 서버(8090)로부터 intent 요청을 받고 GatewayService로 위임
 */
@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping("/dispatch")
    public ResponseEntity<Map<String, Object>> dispatchIntent(@RequestBody Map<String, Object> payload) {
        String intent = (String) payload.get("intent");
        Map<String, Object> data = (Map<String, Object>) payload.get("data");

        Map<String, Object> result = gatewayService.routeIntent(intent, data);
        return ResponseEntity.ok(result);
    }
}
