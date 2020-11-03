package com.whyisee.getdata.model;

import javax.persistence.*;

@Table(name = "tc_gd_datasource")
public class TcGdDatasource {
    /**
     * 数据源编码
     */
    @Id
    @Column(name = "source_id")
    private String sourceId;

    /**
     * 数据源名称
     */
    @Column(name = "source_name")
    private String sourceName;

    /**
     * 数据源中文名称
     */
    @Column(name = "source_name_zh")
    private String sourceNameZh;

    /**
     * 分类
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 主键
     */
    @Column(name = "source_key")
    private String sourceKey;



    /**
     * 主键中文名称
     */
    @Column(name = "source_key_name_zh")
    private String sourceKeyNameZh;

    /**
     * 别名
     */
    @Column(name = "source_alias")
    private String sourceAlias;

    /**
     * 展示指标数量
     */
    @Column(name = "show_tag_num")
    private String showTagNum;

    /**
     * 筛选指标数量
     */
    @Column(name = "cond_tag_num")
    private String condTagNum;

    /**
     * 数据更新时间
     */
    @Column(name = "update_date")
    private String updateDate;

    /**
     * 有效状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    @Column(name = "create_persion")
    private String createPersion;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private String createDate;

    /**
     * 获取数据源编码
     *
     * @return source_id - 数据源编码
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * 设置数据源编码
     *
     * @param sourceId 数据源编码
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 获取数据源名称
     *
     * @return source_name - 数据源名称
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 设置数据源名称
     *
     * @param sourceName 数据源名称
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * 获取数据源中文名称
     *
     * @return source_name_zh - 数据源中文名称
     */
    public String getSourceNameZh() {
        return sourceNameZh;
    }

    /**
     * 设置数据源中文名称
     *
     * @param sourceNameZh 数据源中文名称
     */
    public void setSourceNameZh(String sourceNameZh) {
        this.sourceNameZh = sourceNameZh;
    }

    /**
     * 获取分类
     *
     * @return source_type - 分类
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 设置分类
     *
     * @param sourceType 分类
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取主键
     *
     * @return source_key - 主键
     */
    public String getSourceKey() {
        return sourceKey;
    }

    /**
     * 设置主键
     *
     * @param sourceKey 主键
     */
    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    /**
     * 获取别名
     *
     * @return source_alias - 别名
     */
    public String getSourceAlias() {
        return sourceAlias;
    }

    /**
     * 设置别名
     *
     * @param sourceAlias 别名
     */
    public void setSourceAlias(String sourceAlias) {
        this.sourceAlias = sourceAlias;
    }

    /**
     * 获取展示指标数量
     *
     * @return show_tag_num - 展示指标数量
     */
    public String getShowTagNum() {
        return showTagNum;
    }

    /**
     * 设置展示指标数量
     *
     * @param showTagNum 展示指标数量
     */
    public void setShowTagNum(String showTagNum) {
        this.showTagNum = showTagNum;
    }

    /**
     * 获取筛选指标数量
     *
     * @return cond_tag_num - 筛选指标数量
     */
    public String getCondTagNum() {
        return condTagNum;
    }

    /**
     * 设置筛选指标数量
     *
     * @param condTagNum 筛选指标数量
     */
    public void setCondTagNum(String condTagNum) {
        this.condTagNum = condTagNum;
    }

    /**
     * 获取数据更新时间
     *
     * @return update_date - 数据更新时间
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置数据更新时间
     *
     * @param updateDate 数据更新时间
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取有效状态
     *
     * @return status - 有效状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置有效状态
     *
     * @param status 有效状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建人
     *
     * @return create_persion - 创建人
     */
    public String getCreatePersion() {
        return createPersion;
    }

    /**
     * 设置创建人
     *
     * @param createPersion 创建人
     */
    public void setCreatePersion(String createPersion) {
        this.createPersion = createPersion;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getSourceKeyNameZh() {
        return sourceKeyNameZh;
    }

    public void setSourceKeyNameZh(String sourceKeyNameZh) {
        this.sourceKeyNameZh = sourceKeyNameZh;
    }
}