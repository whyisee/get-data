package com.whyisee.getdata.dao;

import com.whyisee.getdata.core.Mapper;
import com.whyisee.getdata.model.TcGdTagconfig;
import com.whyisee.getdata.model.TcGdTagconfigFlow;

import java.util.List;

public interface TcGdTagconfigFlowMapper extends Mapper<TcGdTagconfigFlow> {
    List<TcGdTagconfigFlow> search(TcGdTagconfigFlow cond);

}