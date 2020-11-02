package com.whyisee.getdata.service.impl;

import com.whyisee.getdata.dao.TcGdConfigflowMapper;
import com.whyisee.getdata.model.TcGdConfigflow;
import com.whyisee.getdata.model.TcGdConfigmain;
import com.whyisee.getdata.service.TcGdConfigflowService;
import com.whyisee.getdata.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2020/10/20.
 */
@Service
@Transactional
public class TcGdConfigflowServiceImpl extends AbstractService<TcGdConfigflow> implements TcGdConfigflowService {
    @Resource
    private TcGdConfigflowMapper tcGdConfigflowMapper;
    public List<TcGdConfigflow> search(TcGdConfigflow model){return tcGdConfigflowMapper.search(model);}

}
