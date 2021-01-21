package com.whyisee.getdata.service;
import com.whyisee.getdata.model.TcGdTagconfigFlow;
import com.whyisee.getdata.core.Service;
import java.util.List;


/**
 * Created by zoukh on 2020/11/01.
 */
public interface TcGdTagconfigFlowService extends Service<TcGdTagconfigFlow> {
    List<TcGdTagconfigFlow> search(TcGdTagconfigFlow model);
    void deleteAllCon(TcGdTagconfigFlow model);

}
