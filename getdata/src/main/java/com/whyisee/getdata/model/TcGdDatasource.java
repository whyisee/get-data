package com.whyisee.getdata.model;

import javax.persistence.*;

@Table(name = "tc_gd_datasource")
public class TcGdDatasource {
    @Id
    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "source_name_zh")
    private String sourceNameZh;

    @Column(name = "source_type")
    private String sourceType;

    @Column(name = "source_key")
    private String sourceKey;

    private String status;

    private String remark;

    @Column(name = "create_persion")
    private String createPersion;

    @Column(name = "create_date")
    private String createDate;

    /**
     * @return source_id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * @param sourceId
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * @return source_name
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * @return source_name_zh
     */
    public String getSourceNameZh() {
        return sourceNameZh;
    }

    /**
     * @param sourceNameZh
     */
    public void setSourceNameZh(String sourceNameZh) {
        this.sourceNameZh = sourceNameZh;
    }

    /**
     * @return source_type
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * @param sourceType
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @return source_key
     */
    public String getSourceKey() {
        return sourceKey;
    }

    /**
     * @param sourceKey
     */
    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return create_persion
     */
    public String getCreatePersion() {
        return createPersion;
    }

    /**
     * @param createPersion
     */
    public void setCreatePersion(String createPersion) {
        this.createPersion = createPersion;
    }

    /**
     * @return create_date
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}