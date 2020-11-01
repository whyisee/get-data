package com.whyisee.getdata.service;
import com.whyisee.getdata.model.TcGdUsertroop;
import com.whyisee.getdata.core.Service;
import java.util.List;


/**
 * Created by zoukh on 2020/11/01.
 */
public interface TcGdUsertroopService extends Service<TcGdUsertroop> {
    List<TcGdUsertroop> search(TcGdUsertroop model);

}
