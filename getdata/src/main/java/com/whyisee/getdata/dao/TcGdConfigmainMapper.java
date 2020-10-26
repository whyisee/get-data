package com.whyisee.getdata.dao;

import com.whyisee.getdata.core.Mapper;
import com.whyisee.getdata.model.TcGdConfigmain;
import com.whyisee.getdata.model.TcGdDatasource;

import java.util.List;

public interface TcGdConfigmainMapper extends Mapper<TcGdConfigmain> {
    List<TcGdConfigmain> search(TcGdConfigmain cond);

}