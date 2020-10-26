package com.whyisee.getdata.model;

import javax.persistence.*;

@Table(name = "tc_gd_tagconfig")
public class TcGdTagconfig {
    /**
     * 标签编码
     */
    @Id
    @Column(name = "tag_id")
    private String tagId;

    /**
     * 标签名称
     */
    @Column(name = "tag_name")
    private String tagName;

    /**
     * 标签中文名称
     */
    @Column(name = "tag_name_zh")
    private String tagNameZh;

    /**
     * 数据来源编码
     */
    @Column(name = "tag_from_id")
    private String tagFromId;

    /**
     * 标签分类编码
     */
    @Column(name = "tag_class_id")
    private String tagClassId;

    /**
     * 标签数据类型
     */
    @Column(name = "tag_data_type")
    private String tagDataType;

    /**
     * 标签展示排序
     */
    @Column(name = "tag_show_order")
    private String tagShowOrder;

    /**
     * 是否可展示
     */
    @Column(name = "is_show")
    private String isShow;

    /**
     * 是否可做条件
     */
    @Column(name = "is_cond")
    private String isCond;

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
     * 获取标签编码
     *
     * @return tag_id - 标签编码
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * 设置标签编码
     *
     * @param tagId 标签编码
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     * 获取标签名称
     *
     * @return tag_name - 标签名称
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * 设置标签名称
     *
     * @param tagName 标签名称
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * 获取标签中文名称
     *
     * @return tag_name_zh - 标签中文名称
     */
    public String getTagNameZh() {
        return tagNameZh;
    }

    /**
     * 设置标签中文名称
     *
     * @param tagNameZh 标签中文名称
     */
    public void setTagNameZh(String tagNameZh) {
        this.tagNameZh = tagNameZh;
    }

    /**
     * 获取数据来源编码
     *
     * @return tag_from_id - 数据来源编码
     */
    public String getTagFromId() {
        return tagFromId;
    }

    /**
     * 设置数据来源编码
     *
     * @param tagFromId 数据来源编码
     */
    public void setTagFromId(String tagFromId) {
        this.tagFromId = tagFromId;
    }

    /**
     * 获取标签分类编码
     *
     * @return tag_class_id - 标签分类编码
     */
    public String getTagClassId() {
        return tagClassId;
    }

    /**
     * 设置标签分类编码
     *
     * @param tagClassId 标签分类编码
     */
    public void setTagClassId(String tagClassId) {
        this.tagClassId = tagClassId;
    }

    /**
     * 获取标签数据类型
     *
     * @return tag_data_type - 标签数据类型
     */
    public String getTagDataType() {
        return tagDataType;
    }

    /**
     * 设置标签数据类型
     *
     * @param tagDataType 标签数据类型
     */
    public void setTagDataType(String tagDataType) {
        this.tagDataType = tagDataType;
    }

    /**
     * 获取标签展示排序
     *
     * @return tag_show_order - 标签展示排序
     */
    public String getTagShowOrder() {
        return tagShowOrder;
    }

    /**
     * 设置标签展示排序
     *
     * @param tagShowOrder 标签展示排序
     */
    public void setTagShowOrder(String tagShowOrder) {
        this.tagShowOrder = tagShowOrder;
    }

    /**
     * 获取是否可展示
     *
     * @return is_show - 是否可展示
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * 设置是否可展示
     *
     * @param isShow 是否可展示
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取是否可做条件
     *
     * @return is_cond - 是否可做条件
     */
    public String getIsCond() {
        return isCond;
    }

    /**
     * 设置是否可做条件
     *
     * @param isCond 是否可做条件
     */
    public void setIsCond(String isCond) {
        this.isCond = isCond;
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
}