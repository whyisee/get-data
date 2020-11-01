package com.whyisee.getdata.dao;

import com.whyisee.getdata.core.Mapper;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.model.TcGdUsertroop;

import java.util.List;

public interface TcGdUsertroopMapper extends Mapper<TcGdUsertroop> {
    List<TcGdUsertroop> search(TcGdUsertroop cond);

}