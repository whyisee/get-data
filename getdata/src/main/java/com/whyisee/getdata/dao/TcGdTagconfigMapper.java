package com.whyisee.getdata.dao;

import com.whyisee.getdata.core.Mapper;
import com.whyisee.getdata.model.TcGdTagconfig;
import com.whyisee.getdata.model.TcGdUsertroop;

import java.util.List;

public interface TcGdTagconfigMapper extends Mapper<TcGdTagconfig> {
    List<TcGdTagconfig> search(TcGdTagconfig cond);

}