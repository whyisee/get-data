package com.whyisee.getdata.core;

import tk.mybatis.mapper.entity.Condition;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/21 0:59
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */
public class GDCondition extends Condition {

    public GDCondition(Class<?> entityClass) {
        super(entityClass);
    }

    public GDCondition(Class<?> entityClass, boolean exists) {
        super(entityClass, exists);
    }

    public GDCondition(Class<?> entityClass, boolean exists, boolean notNull) {
        super(entityClass, exists, notNull);
    }
}
