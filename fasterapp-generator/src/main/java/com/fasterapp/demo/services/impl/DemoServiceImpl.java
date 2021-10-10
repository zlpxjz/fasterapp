package com.fasterapp.demo.services.impl;

import com.fasterapp.demo.models.DemoModel;
import com.fasterapp.demo.mappers.DemoMapper;
import com.fasterapp.demo.services.IDemoService;
import com.fasterapp.base.arch.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("DemoService")
@Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl extends BaseServiceImpl<Integer, DemoModel, DemoMapper> implements IDemoService {

}
