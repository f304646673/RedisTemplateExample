package org.example.redistemplateexample.config;

import lombok.Data;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Data
@Configuration
@ConfigurationProperties(prefix = "redis-pool")
@Component
public class RedisPoolConfig {

    private List<Config> configs;

    @Data
    public static class Config {
        private String hostAndPort;
        private int type;
        private String username;
        private String password;
        private String database;
        private String sentinelMasterHostAndPort; // for Sentine
        private String clusterMaxRedirects; // for Cluster
        private String timeout;
        private String maxActive;
        private String maxWait;
        private String maxIdle;
        private String minIdle;
    }
}