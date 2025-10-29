package com.hulahoop.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ⚙️ application.yml의 service.*.url 구조를 그대로 매핑
 * service:
 *   llm:
 *     url: http://localhost:8090
 *   bike:
 *     url: http://localhost:8081
 *   movie:
 *     url: http://localhost:8082
 */
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceUrlConfig {

    private UrlConfig llm;
    private UrlConfig bike;
    private UrlConfig movie;

    public UrlConfig getLlm() {
        return llm;
    }

    public void setLlm(UrlConfig llm) {
        this.llm = llm;
    }

    public UrlConfig getBike() {
        return bike;
    }

    public void setBike(UrlConfig bike) {
        this.bike = bike;
    }

    public UrlConfig getMovie() {
        return movie;
    }

    public void setMovie(UrlConfig movie) {
        this.movie = movie;
    }

    public static class UrlConfig {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
