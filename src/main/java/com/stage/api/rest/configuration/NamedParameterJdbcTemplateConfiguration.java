package com.stage.api.rest.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.stage.api.rest.properties.NamedParameterJdbcTemplateProperties;

@Configuration
@EnableConfigurationProperties(NamedParameterJdbcTemplateProperties.class)
public class NamedParameterJdbcTemplateConfiguration {

}
