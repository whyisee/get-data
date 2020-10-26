package com.whyisee.getdata.service;
import com.whyisee.getdata.model.TcGdConfigmain;
import com.whyisee.getdata.core.Service;
import com.whyisee.getdata.model.TcGdDatasource;

import java.util.List;


/**
 * Created by zoukh on 2020/10/25.
 */
public interface TcGdConfigmainService extends Service<TcGdConfigmain> {
    List<TcGdConfigmain> search(TcGdConfigmain model);

}
