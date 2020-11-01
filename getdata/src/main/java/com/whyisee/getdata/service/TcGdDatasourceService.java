package com.whyisee.getdata.service;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.core.Service;

import java.util.List;


/**
 * Created by zoukh on 2020/11/01.
 */
public interface TcGdDatasourceService extends Service<TcGdDatasource> {
    List<TcGdDatasource> search(TcGdDatasource model);

}
