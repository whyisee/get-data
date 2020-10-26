package com.whyisee.getdata.service.impl;

import com.whyisee.getdata.dao.TcGdConfigmainMapper;
import com.whyisee.getdata.model.TcGdConfigmain;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcGdConfigmainService;
import com.whyisee.getdata.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by zoukh on 2020/10/25.
 */
@Service
@Transactional
public class TcGdConfigmainServiceImpl extends AbstractService<TcGdConfigmain> implements TcGdConfigmainService {
    @Resource
    private TcGdConfigmainMapper tcGdConfigmainMapper;
    public List<TcGdConfigmain> search(TcGdConfigmain model){return tcGdConfigmainMapper.search(model);}

}
