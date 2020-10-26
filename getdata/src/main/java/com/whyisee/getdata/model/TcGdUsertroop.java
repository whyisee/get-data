package com.whyisee.getdata.model;

import javax.persistence.*;

@Table(name = "tc_gd_usertroop")
public class TcGdUsertroop {
    /**
     * 用户群编码
     */
    @Id
    @Column(name = "troop_id")
    private String troopId;

    /**
     * 用户群名称
     */
    @Column(name = "troop_name")
    private String troopName;

    /**
     * 用户群来源
     */
    @Column(name = "troop_from")
    private String troopFrom;

    /**
     * 用户群类别
     */
    @Column(name = "troop_type")
    private String troopType;

    /**
     * 用户群数量
     */
    @Column(name = "troop_num")
    private String troopNum;

    /**
     * 用户群状态
     */
    @Column(name = "troop_status")
    private String troopStatus;

    /**
     * 用户生效开始时间
     */
    @Column(name = "troop_begin_date")
    private String troopBeginDate;

    /**
     * 用户生效结束时间
     */
    @Column(name = "troop_end_date")
    private String troopEndDate;

    /**
     * 原始文件名
     */
    @Column(name = "source_file_name")
    private String sourceFileName;

    /**
     * 主键
     */
    @Column(name = "troop_key")
    private String troopKey;

    /**
     * 是否使用其中标签
     */
    @Column(name = "is_user_tag")
    private String isUserTag;

    /**
     * 标签配置编码
     */
    @Column(name = "tag_flow_id")
    private String tagFlowId;

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
     * 修改人
     */
    @Column(name = "update_persion")
    private String updatePersion;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private String updateDate;

    /**
     * 获取用户群编码
     *
     * @return troop_id - 用户群编码
     */
    public String getTroopId() {
        return troopId;
    }

    /**
     * 设置用户群编码
     *
     * @param troopId 用户群编码
     */
    public void setTroopId(String troopId) {
        this.troopId = troopId;
    }

    /**
     * 获取用户群名称
     *
     * @return troop_name - 用户群名称
     */
    public String getTroopName() {
        return troopName;
    }

    /**
     * 设置用户群名称
     *
     * @param troopName 用户群名称
     */
    public void setTroopName(String troopName) {
        this.troopName = troopName;
    }

    /**
     * 获取用户群来源
     *
     * @return troop_from - 用户群来源
     */
    public String getTroopFrom() {
        return troopFrom;
    }

    /**
     * 设置用户群来源
     *
     * @param troopFrom 用户群来源
     */
    public void setTroopFrom(String troopFrom) {
        this.troopFrom = troopFrom;
    }

    /**
     * 获取用户群类别
     *
     * @return troop_type - 用户群类别
     */
    public String getTroopType() {
        return troopType;
    }

    /**
     * 设置用户群类别
     *
     * @param troopType 用户群类别
     */
    public void setTroopType(String troopType) {
        this.troopType = troopType;
    }

    /**
     * 获取用户群数量
     *
     * @return troop_num - 用户群数量
     */
    public String getTroopNum() {
        return troopNum;
    }

    /**
     * 设置用户群数量
     *
     * @param troopNum 用户群数量
     */
    public void setTroopNum(String troopNum) {
        this.troopNum = troopNum;
    }

    /**
     * 获取用户群状态
     *
     * @return troop_status - 用户群状态
     */
    public String getTroopStatus() {
        return troopStatus;
    }

    /**
     * 设置用户群状态
     *
     * @param troopStatus 用户群状态
     */
    public void setTroopStatus(String troopStatus) {
        this.troopStatus = troopStatus;
    }

    /**
     * 获取用户生效开始时间
     *
     * @return troop_begin_date - 用户生效开始时间
     */
    public String getTroopBeginDate() {
        return troopBeginDate;
    }

    /**
     * 设置用户生效开始时间
     *
     * @param troopBeginDate 用户生效开始时间
     */
    public void setTroopBeginDate(String troopBeginDate) {
        this.troopBeginDate = troopBeginDate;
    }

    /**
     * 获取用户生效结束时间
     *
     * @return troop_end_date - 用户生效结束时间
     */
    public String getTroopEndDate() {
        return troopEndDate;
    }

    /**
     * 设置用户生效结束时间
     *
     * @param troopEndDate 用户生效结束时间
     */
    public void setTroopEndDate(String troopEndDate) {
        this.troopEndDate = troopEndDate;
    }

    /**
     * 获取原始文件名
     *
     * @return source_file_name - 原始文件名
     */
    public String getSourceFileName() {
        return sourceFileName;
    }

    /**
     * 设置原始文件名
     *
     * @param sourceFileName 原始文件名
     */
    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    /**
     * 获取主键
     *
     * @return troop_key - 主键
     */
    public String getTroopKey() {
        return troopKey;
    }

    /**
     * 设置主键
     *
     * @param troopKey 主键
     */
    public void setTroopKey(String troopKey) {
        this.troopKey = troopKey;
    }

    /**
     * 获取是否使用其中标签
     *
     * @return is_user_tag - 是否使用其中标签
     */
    public String getIsUserTag() {
        return isUserTag;
    }

    /**
     * 设置是否使用其中标签
     *
     * @param isUserTag 是否使用其中标签
     */
    public void setIsUserTag(String isUserTag) {
        this.isUserTag = isUserTag;
    }

    /**
     * 获取标签配置编码
     *
     * @return tag_flow_id - 标签配置编码
     */
    public String getTagFlowId() {
        return tagFlowId;
    }

    /**
     * 设置标签配置编码
     *
     * @param tagFlowId 标签配置编码
     */
    public void setTagFlowId(String tagFlowId) {
        this.tagFlowId = tagFlowId;
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

    /**
     * 获取修改人
     *
     * @return update_persion - 修改人
     */
    public String getUpdatePersion() {
        return updatePersion;
    }

    /**
     * 设置修改人
     *
     * @param updatePersion 修改人
     */
    public void setUpdatePersion(String updatePersion) {
        this.updatePersion = updatePersion;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}