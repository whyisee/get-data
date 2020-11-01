package com.whyisee.getdata.service.impl;

import com.whyisee.getdata.dao.TcGdUsertroopMapper;
import com.whyisee.getdata.model.TcGdUsertroop;
import com.whyisee.getdata.service.TcGdUsertroopService;
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
public class TcGdUsertroopServiceImpl extends AbstractService<TcGdUsertroop> implements TcGdUsertroopService {
    @Resource
    private TcGdUsertroopMapper tcGdUsertroopMapper;
    public List<TcGdUsertroop> search(TcGdUsertroop model){return tcGdUsertroopMapper.search(model);}

}
