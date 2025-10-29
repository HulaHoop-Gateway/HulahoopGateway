package com.hulahoop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {

    /**
     * 내부 마이크로서비스 호출을 위한 RestTemplate Bean을 등록합니다.
     * 게이트웨이 컨트롤러에서 이 RestTemplate을 주입받아 LLM 서버, 자전거 서버 등과 통신합니다.
     */
    @Bean
    public RestTemplate restTemplate() {
        // 실제 운영 환경에서는 WebClient나 Connection Pool이 적용된 RestTemplate Builder를 사용하는 것이 좋습니다.
        return new RestTemplate();
    }
}

