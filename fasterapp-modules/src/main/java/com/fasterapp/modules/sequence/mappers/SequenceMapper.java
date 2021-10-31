package com.fasterapp.modules.sequence.mappers;


import com.fasterapp.base.arch.mapper.BaseMapper;
import com.fasterapp.modules.sequence.models.SequenceModel;
import org.apache.ibatis.annotations.Param;

public interface SequenceMapper extends BaseMapper<Integer, SequenceModel> {
    /**
     *
     * @param seqName
     * @param seDate
     * @return
     */
    SequenceModel selectByNameAndDate(@Param("seqName") String seqName, @Param("seqDate") String seDate);
}

