package com.whyisee.getdata.dao;

import com.whyisee.getdata.core.Mapper;
import com.whyisee.getdata.model.TcGdConfigflow;
import com.whyisee.getdata.model.TcGdConfigmain;

import java.util.List;

public interface TcGdConfigflowMapper extends Mapper<TcGdConfigflow> {
    List<TcGdConfigflow> search(TcGdConfigflow cond);

}