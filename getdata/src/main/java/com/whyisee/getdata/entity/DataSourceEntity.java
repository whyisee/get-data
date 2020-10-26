package com.whyisee.getdata.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/19 17:42
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */
@Data
@Entity(name="tc_gd_datasource")
public class DataSourceEntity {
    @Id
    @Column(name="source_id")
    private String SourceId;

    //private String

}
