package com.whyisee.getdata.model;

import javax.persistence.*;

@Table(name = "tc_gd_configflow")
public class TcGdConfigflow {
    /**
     * 流程编码
     */
    @Id
    @Column(name = "flow_id")
    private String flowId;

    /**
     * 流程名称
     */
    @Column(name = "flow_name")
    private String flowName;

    /**
     * 流程类型
     */
    @Column(name = "flow_type")
    private String flowType;

    /**
     * 父流程编码
     */
    @Column(name = "parent_flow_id")
    private String parentFlowId;

    /**
     * 流程配置键
     */
    @Column(name = "flow_key")
    private String flowKey;

    /**
     * 流程配置值1
     */
    @Column(name = "flow_value1")
    private String flowValue1;

    /**
     * 流程配置值2
     */
    @Column(name = "flow_value2")
    private String flowValue2;

    /**
     * 流程配置值3
     */
    @Column(name = "flow_value3")
    private String flowValue3;

    /**
     * 流程配置值4
     */
    @Column(name = "flow_value4")
    private String flowValue4;

    /**
     * 流程配置值5
     */
    @Column(name = "flow_value5")
    private String flowValue5;

    /**
     * 流程序号
     */
    @Column(name = "flow_sort")
    private String flowSort;

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
     * 获取流程编码
     *
     * @return flow_id - 流程编码
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * 设置流程编码
     *
     * @param flowId 流程编码
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * 获取流程名称
     *
     * @return flow_name - 流程名称
     */
    public String getFlowName() {
        return flowName;
    }

    /**
     * 设置流程名称
     *
     * @param flowName 流程名称
     */
    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    /**
     * 获取流程类型
     *
     * @return flow_type - 流程类型
     */
    public String getFlowType() {
        return flowType;
    }

    /**
     * 设置流程类型
     *
     * @param flowType 流程类型
     */
    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    /**
     * 获取父流程编码
     *
     * @return parent_flow_id - 父流程编码
     */
    public String getParentFlowId() {
        return parentFlowId;
    }

    /**
     * 设置父流程编码
     *
     * @param parentFlowId 父流程编码
     */
    public void setParentFlowId(String parentFlowId) {
        this.parentFlowId = parentFlowId;
    }

    /**
     * 获取流程配置键
     *
     * @return flow_key - 流程配置键
     */
    public String getFlowKey() {
        return flowKey;
    }

    /**
     * 设置流程配置键
     *
     * @param flowKey 流程配置键
     */
    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    /**
     * 获取流程配置值1
     *
     * @return flow_value1 - 流程配置值1
     */
    public String getFlowValue1() {
        return flowValue1;
    }

    /**
     * 设置流程配置值1
     *
     * @param flowValue1 流程配置值1
     */
    public void setFlowValue1(String flowValue1) {
        this.flowValue1 = flowValue1;
    }

    /**
     * 获取流程配置值2
     *
     * @return flow_value2 - 流程配置值2
     */
    public String getFlowValue2() {
        return flowValue2;
    }

    /**
     * 设置流程配置值2
     *
     * @param flowValue2 流程配置值2
     */
    public void setFlowValue2(String flowValue2) {
        this.flowValue2 = flowValue2;
    }

    /**
     * 获取流程配置值3
     *
     * @return flow_value3 - 流程配置值3
     */
    public String getFlowValue3() {
        return flowValue3;
    }

    /**
     * 设置流程配置值3
     *
     * @param flowValue3 流程配置值3
     */
    public void setFlowValue3(String flowValue3) {
        this.flowValue3 = flowValue3;
    }

    /**
     * 获取流程配置值4
     *
     * @return flow_value4 - 流程配置值4
     */
    public String getFlowValue4() {
        return flowValue4;
    }

    /**
     * 设置流程配置值4
     *
     * @param flowValue4 流程配置值4
     */
    public void setFlowValue4(String flowValue4) {
        this.flowValue4 = flowValue4;
    }

    /**
     * 获取流程配置值5
     *
     * @return flow_value5 - 流程配置值5
     */
    public String getFlowValue5() {
        return flowValue5;
    }

    /**
     * 设置流程配置值5
     *
     * @param flowValue5 流程配置值5
     */
    public void setFlowValue5(String flowValue5) {
        this.flowValue5 = flowValue5;
    }

    /**
     * 获取流程序号
     *
     * @return flow_sort - 流程序号
     */
    public String getFlowSort() {
        return flowSort;
    }

    /**
     * 设置流程序号
     *
     * @param flowSort 流程序号
     */
    public void setFlowSort(String flowSort) {
        this.flowSort = flowSort;
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