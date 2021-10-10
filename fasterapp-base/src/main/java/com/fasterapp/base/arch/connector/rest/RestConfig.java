package com.fasterapp.base.arch.connector.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    @Value("${rest.connection.timeout:60}")
    private Integer connectionTimeout;

    @Value("${rest.read.timeout:60}")
    private Integer readTimeout;

    @Bean("RestConnector")
    public IRestConnector restConnector(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout*1000);
        requestFactory.setReadTimeout(readTimeout*1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return new RestConnectorImpl(restTemplate);
    }
}
