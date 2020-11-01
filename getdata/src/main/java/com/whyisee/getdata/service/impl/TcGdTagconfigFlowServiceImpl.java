package com.whyisee.getdata.service.impl;

import com.whyisee.getdata.dao.TcGdTagconfigFlowMapper;
import com.whyisee.getdata.model.TcGdTagconfigFlow;
import com.whyisee.getdata.service.TcGdTagconfigFlowService;
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
public class TcGdTagconfigFlowServiceImpl extends AbstractService<TcGdTagconfigFlow> implements TcGdTagconfigFlowService {
    @Resource
    private TcGdTagconfigFlowMapper tcGdTagconfigFlowMapper;
    public List<TcGdTagconfigFlow> search(TcGdTagconfigFlow model){return tcGdTagconfigFlowMapper.search(model);}

}
