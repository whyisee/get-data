package com.whyisee.getdata.service.impl;

import com.whyisee.getdata.dao.TcGdTagconfigMapper;
import com.whyisee.getdata.model.TcGdTagconfig;
import com.whyisee.getdata.service.TcGdTagconfigService;
import com.whyisee.getdata.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/10/20.
 */
@Service
@Transactional
public class TcGdTagconfigServiceImpl extends AbstractService<TcGdTagconfig> implements TcGdTagconfigService {
    @Resource
    private TcGdTagconfigMapper tcGdTagconfigMapper;

}
