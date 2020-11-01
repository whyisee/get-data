package com.whyisee.getdata.service.impl;

import com.whyisee.getdata.dao.TcGdTagconfigMapper;
import com.whyisee.getdata.model.TcGdTagconfig;
import com.whyisee.getdata.service.TcGdTagconfigService;
import com.whyisee.getdata.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by zoukh on 2020/11/01.
 */
@Service
@Transactional
public class TcGdTagconfigServiceImpl extends AbstractService<TcGdTagconfig> implements TcGdTagconfigService {
    @Resource
    private TcGdTagconfigMapper tcGdTagconfigMapper;
    public List<TcGdTagconfig> search(TcGdTagconfig model){return tcGdTagconfigMapper.search(model);}

}
