package com.fasterapp.base.arch.connector.rest;

import org.springframework.core.io.FileSystemResource;

import java.util.Map;

/**
 * 系统集成接口
 */
public interface IRestConnector {
    /**
     *
     * @param url
     * @param headers
     * @param values
     * @throws Exception
     */
    <T> T get(String url, Map<String, String> headers, Map<String, Object> values, Class<T> clazz) throws Exception;

    /**
     *
     * @param url
     * @param headers
     * @param values
     * @throws Exception
     */
    <T> T post(String url, Map<String, String> headers, Object values, Class<T> clazz) throws Exception;

    /**
     *
     * @param url
     * @param headers
     * @param values
     * @throws Exception
     */
    <T> T postAsForm(String url, Map<String, String> headers, Map<String, Object> values, Class<T> clazz) throws Exception;

    /**
     *
     * @param url
     * @param headers
     * @param values
     * @throws Exception
     */
    <T> T post(String url, Map<String, String> headers, Map<String, Object> values, FileSystemResource file, Class<T> clazz) throws Exception;
}
