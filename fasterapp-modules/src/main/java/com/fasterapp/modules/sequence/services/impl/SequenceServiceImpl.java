package com.fasterapp.modules.sequence.services.impl;

import com.fasterapp.modules.sequence.mappers.SequenceMapper;
import com.fasterapp.modules.sequence.models.SequenceModel;
import com.fasterapp.modules.sequence.services.ISequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Slf4j
@Service("SequenceService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class SequenceServiceImpl implements ISequenceService {
    @Autowired
    SequenceMapper sequenceMapper;

    @Override
    public Integer getNextValue(String name) throws Exception {
        return this.getNextValue(name, "19700101");
    }

    @Override
    public Integer getNextValue(String name, String date) throws Exception {
        BigInteger value = BigInteger.ONE;
        SequenceModel sequenceModel = sequenceMapper.selectByNameAndPermitAndDate(name,  date);
        if(sequenceModel != null){
            value = sequenceModel.getSeqValue().add(BigInteger.ONE);
            sequenceModel.setSeqValue(value);
            sequenceMapper.updateByPrimaryKey(sequenceModel);
        }else{
            sequenceModel = new SequenceModel();
            sequenceModel.setSeqName(name);
            sequenceModel.setSeqDate(date);
            sequenceModel.setSeqValue(value);
            sequenceMapper.insert(sequenceModel);
        }

        return value.intValue();
    }
}
