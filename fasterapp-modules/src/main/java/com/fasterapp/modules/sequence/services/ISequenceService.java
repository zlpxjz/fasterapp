package com.fasterapp.modules.sequence.services;

public interface ISequenceService {
    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    Integer getNextValue(String name) throws Exception;

    /**
     *
     * @param name
     * @param date
     * @return
     * @throws Exception
     */
    Integer getNextValue(String name, String date) throws Exception;
}
