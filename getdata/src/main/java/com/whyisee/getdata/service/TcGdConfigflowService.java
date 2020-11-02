package com.whyisee.getdata.service;
import com.whyisee.getdata.model.TcGdConfigflow;
import com.whyisee.getdata.core.Service;
import com.whyisee.getdata.model.TcGdConfigmain;

import java.util.List;


/**
 * Created by CodeGenerator on 2020/10/20.
 */
public interface TcGdConfigflowService extends Service<TcGdConfigflow> {
    List<TcGdConfigflow> search(TcGdConfigflow model);

}
