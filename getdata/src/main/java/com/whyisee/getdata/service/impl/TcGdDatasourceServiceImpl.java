package com.whyisee.getdata.service.impl;

import com.whyisee.getdata.dao.TcGdDatasourceMapper;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcGdDatasourceService;
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
public class TcGdDatasourceServiceImpl extends AbstractService<TcGdDatasource> implements TcGdDatasourceService {
    @Resource
    private TcGdDatasourceMapper tcGdDatasourceMapper;

    public List<TcGdDatasource> search(TcGdDatasource model){return tcGdDatasourceMapper.search(model);}

}
