package com.whyisee.getdata.service;
import com.whyisee.getdata.model.TcGdTagconfig;
import com.whyisee.getdata.core.Service;
import java.util.List;


/**
 * Created by zoukh on 2020/11/01.
 */
public interface TcGdTagconfigService extends Service<TcGdTagconfig> {
    List<TcGdTagconfig> search(TcGdTagconfig model);

}
