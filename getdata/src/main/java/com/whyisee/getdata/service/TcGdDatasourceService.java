package com.whyisee.getdata.service;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2020/10/20.
 */
public interface TcGdDatasourceService extends Service<TcGdDatasource> {
    List<TcGdDatasource> search(TcGdDatasource model);

}
