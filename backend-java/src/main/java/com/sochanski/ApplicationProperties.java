package com.sochanski;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private boolean loadTestData;
    private boolean loadSqlConstraints;
    private boolean loadSqlData;
}
