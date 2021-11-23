package com.fasterapp.base.arch.rest;

import com.fasterapp.base.utils.JsonUtil;
import com.fasterapp.base.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j(topic = "RestLogger")
public class RestServiceImpl implements IRestService {
    private RestTemplate restTemplate;

    public RestServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T get(String url, Map<String, String> headers, Map<String, Object> values, Class<T> clazz) throws Exception {
        long startTime = System.currentTimeMillis();
        String responseTxt = null;
        HttpEntity<String> requestEntity = null;
        try {
            StringBuffer fullUrl = new StringBuffer(url);
            String queryParameters = getQueryParameters(values);
            if(!StringUtil.isNullOrBlank(queryParameters)){
                fullUrl.append(queryParameters);
            }

            HttpHeaders httpHeaders = this.getHeaders(headers, MediaType.APPLICATION_FORM_URLENCODED);
            requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(fullUrl.toString(), HttpMethod.GET, requestEntity, String.class);
            responseTxt = responseEntity.getBody();
            return JsonUtil.toObject(responseTxt, clazz);
        } finally {
            log.info("url={}, request={}, response={}, time={}", url, JsonUtil.toString(requestEntity), responseTxt, (System.currentTimeMillis() - startTime)/1000);
        }
    }

    @Override
    public <T> T post(String url, Map<String, String> headers, Object value, Class<T> clazz) throws Exception {
        long startTime = System.currentTimeMillis();

        String responseTxt = null;
        HttpEntity<Object> requestEntity = null;

        boolean isRequestLoggable = true, isResponseLoggable = true;
        if(headers != null) {
            isRequestLoggable = headers.containsKey("requestLoggable") ? headers.get("requestLoggable").equals("true") : true;
            isResponseLoggable = headers.containsKey("responseLoggable") ? headers.get("responseLoggable").equals("true") : true;
        }
        try {
            HttpHeaders httpHeaders = this.getHeaders(headers, MediaType.APPLICATION_JSON_UTF8);
            if(value == null){
                requestEntity = new HttpEntity<>(new HashMap<>(), httpHeaders);
            }else{
                requestEntity = new HttpEntity<>(value, httpHeaders);
            }

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            responseTxt = responseEntity.getBody();
            return JsonUtil.toObject(responseTxt, clazz);
        } finally {
            log.info("url={}, request={}, response={}, time={}", url, isRequestLoggable?JsonUtil.toString(JsonUtil.toString(requestEntity)):null, isResponseLoggable?responseTxt:null, (System.currentTimeMillis() - startTime)/1000);
        }
    }

    @Override
    public <T> T postAsForm(String url, Map<String, String> headers, Map<String, Object> values, Class<T> clazz) throws Exception {
        long startTime = System.currentTimeMillis();

        String responseTxt = null;
        HttpEntity<MultiValueMap<String, Object>> requestEntity = null;

        boolean isRequestLoggable = true, isResponseLoggable = true;
        if(headers != null) {
            isRequestLoggable = headers.containsKey("requestLoggable") ? headers.get("requestLoggable").equals("true") : true;
            isResponseLoggable = headers.containsKey("responseLoggable") ? headers.get("responseLoggable").equals("true") : true;
        }

        try {
            MultiValueMap<String, Object> paramMap = this.getMultiValueMap(values);
            HttpHeaders httpHeaders = this.getHeaders(headers, MediaType.APPLICATION_FORM_URLENCODED);

            requestEntity = new HttpEntity(paramMap, httpHeaders);

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            responseTxt = responseEntity.getBody();
            return JsonUtil.toObject(responseTxt, clazz);
        } finally {
            log.info("url={}, request={}, response={}, time={}", url, isRequestLoggable?JsonUtil.toString(JsonUtil.toString(requestEntity)):null, isResponseLoggable?responseTxt:null, (System.currentTimeMillis() - startTime)/1000);
        }
    }

    @Override
    public <T> T post(String url, Map<String, String> headers, Map<String, Object> values, FileSystemResource file,  Class<T> clazz) throws Exception {
        long startTime = System.currentTimeMillis();

        String responseTxt = null;
        boolean isRequestLoggable = true, isResponseLoggable = true;
        if(headers != null) {
            isRequestLoggable = headers.containsKey("requestLoggable") ? headers.get("requestLoggable").equals("true") : true;
            isResponseLoggable = headers.containsKey("responseLoggable") ? headers.get("responseLoggable").equals("true") : true;
        }

        try {
            HttpHeaders httpHeaders = this.getHeaders(headers, MediaType.parseMediaType("multipart/form-data"));

            MultiValueMap<String, Object> paramMap = this.getMultiValueMap(values);
            paramMap.add("file", file);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(paramMap, httpHeaders);

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            responseTxt = responseEntity.getBody();
            return JsonUtil.toObject(responseTxt, clazz);
        } finally {
            log.info("url={}, request={}, response={}, time={}",url, isRequestLoggable?JsonUtil.toString(values):null, isResponseLoggable?responseTxt:null, (System.currentTimeMillis() - startTime)/1000);
        }
    }

    /**
     * 获取MultiValueMap
     * @param parameters
     * @return
     * @throws Exception
     */
    private MultiValueMap<String, Object> getMultiValueMap(Map<String, Object> parameters) throws Exception{
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap();

        if(parameters != null) {
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                paramMap.add(key, parameters.get(key));
            }
        }

        return paramMap;
    }

    /**
     * 获取Http Get方式参数
     * @param parameters
     * @return
     */
    private String getQueryParameters(Map<String, Object> parameters)  throws Exception{
        if(parameters == null || parameters.isEmpty()) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        Set<String> fields = parameters.keySet();
        for(String field : fields){
            if(sb.length() == 0){
                sb.append("?");
            }else{
                sb.append("&");
            }
            sb.append(field).append("=").append(parameters.get(field));
        }
        return sb.toString();
    }

    /**
     * 设置HTTP请求头
     * @param headers
     * @return
     */
    private HttpHeaders getHeaders(Map<String, String> headers, MediaType mediaType) throws Exception{
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);
        requestHeaders.add("mdc_trace_id", MDC.get("UUID"));
        if(headers != null) {
            Set<String> fields = headers.keySet();
            for (String field : fields) {
                requestHeaders.add(field, headers.get(field));
            }
        }
        return requestHeaders;
    }
}
