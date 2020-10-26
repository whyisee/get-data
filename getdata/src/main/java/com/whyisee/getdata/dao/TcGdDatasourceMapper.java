package com.whyisee.getdata.dao;

import com.whyisee.getdata.core.Mapper;
import com.whyisee.getdata.model.TcGdDatasource;

import java.util.List;

public interface TcGdDatasourceMapper extends Mapper<TcGdDatasource> {
    List<TcGdDatasource> search(TcGdDatasource cond);

}