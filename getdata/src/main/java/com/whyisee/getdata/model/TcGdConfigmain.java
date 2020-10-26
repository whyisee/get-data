package com.whyisee.getdata.model;

import javax.persistence.*;

@Table(name = "tc_gd_configmain")
public class TcGdConfigmain {
    /**
     * 任务编码
     */
    @Id
    @Column(name = "task_id")
    private String taskId;

    /**
     * 任务名称
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 任务流程编码
     */
    @Column(name = "flow_id")
    private String flowId;

    /**
     * 执行引擎
     */
    @Column(name = "exec_type")
    private String execType;

    /**
     * 执行语句
     */
    @Column(name = "exec_sql")
    private String execSql;

    /**
     * 数据源配置编码
     */
    @Column(name = "source_flow_id")
    private String sourceFlowId;

    /**
     * 用户群配置编码
     */
    @Column(name = "troop_flow_id")
    private String troopFlowId;

    /**
     * 筛选条件配置编码
     */
    @Column(name = "cond_flow_id")
    private String condFlowId;

    /**
     * 展示配置编码
     */
    @Column(name = "show_flow_id")
    private String showFlowId;

    /**
     * 执行配置编码
     */
    @Column(name = "exec_flow_id")
    private String execFlowId;

    /**
     * 结果配置编码
     */
    @Column(name = "data_flow_id")
    private String dataFlowId;

    /**
     * 周期类型
     */
    @Column(name = "cycle_type")
    private String cycleType;

    /**
     * 周期执行项
     */
    @Column(name = "cycle_value")
    private String cycleValue;

    /**
     * 最新结果文件
     */
    @Column(name = "data_file")
    private String dataFile;

    /**
     * 最新结果数量
     */
    @Column(name = "data_num")
    private String dataNum;

    /**
     * 最新任务执行状态
     */
    @Column(name = "task_status")
    private String taskStatus;

    /**
     * 最新执行开始时间
     */
    @Column(name = "begin_time")
    private String beginTime;

    /**
     * 最新执行结束时间
     */
    @Column(name = "end_time")
    private String endTime;

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
     * 获取任务编码
     *
     * @return task_id - 任务编码
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 设置任务编码
     *
     * @param taskId 任务编码
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取任务名称
     *
     * @return task_name - 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务流程编码
     *
     * @return flow_id - 任务流程编码
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * 设置任务流程编码
     *
     * @param flowId 任务流程编码
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * 获取执行引擎
     *
     * @return exec_type - 执行引擎
     */
    public String getExecType() {
        return execType;
    }

    /**
     * 设置执行引擎
     *
     * @param execType 执行引擎
     */
    public void setExecType(String execType) {
        this.execType = execType;
    }

    /**
     * 获取执行语句
     *
     * @return exec_sql - 执行语句
     */
    public String getExecSql() {
        return execSql;
    }

    /**
     * 设置执行语句
     *
     * @param execSql 执行语句
     */
    public void setExecSql(String execSql) {
        this.execSql = execSql;
    }

    /**
     * 获取数据源配置编码
     *
     * @return source_flow_id - 数据源配置编码
     */
    public String getSourceFlowId() {
        return sourceFlowId;
    }

    /**
     * 设置数据源配置编码
     *
     * @param sourceFlowId 数据源配置编码
     */
    public void setSourceFlowId(String sourceFlowId) {
        this.sourceFlowId = sourceFlowId;
    }

    /**
     * 获取用户群配置编码
     *
     * @return troop_flow_id - 用户群配置编码
     */
    public String getTroopFlowId() {
        return troopFlowId;
    }

    /**
     * 设置用户群配置编码
     *
     * @param troopFlowId 用户群配置编码
     */
    public void setTroopFlowId(String troopFlowId) {
        this.troopFlowId = troopFlowId;
    }

    /**
     * 获取筛选条件配置编码
     *
     * @return cond_flow_id - 筛选条件配置编码
     */
    public String getCondFlowId() {
        return condFlowId;
    }

    /**
     * 设置筛选条件配置编码
     *
     * @param condFlowId 筛选条件配置编码
     */
    public void setCondFlowId(String condFlowId) {
        this.condFlowId = condFlowId;
    }

    /**
     * 获取展示配置编码
     *
     * @return show_flow_id - 展示配置编码
     */
    public String getShowFlowId() {
        return showFlowId;
    }

    /**
     * 设置展示配置编码
     *
     * @param showFlowId 展示配置编码
     */
    public void setShowFlowId(String showFlowId) {
        this.showFlowId = showFlowId;
    }

    /**
     * 获取执行配置编码
     *
     * @return exec_flow_id - 执行配置编码
     */
    public String getExecFlowId() {
        return execFlowId;
    }

    /**
     * 设置执行配置编码
     *
     * @param execFlowId 执行配置编码
     */
    public void setExecFlowId(String execFlowId) {
        this.execFlowId = execFlowId;
    }

    /**
     * 获取结果配置编码
     *
     * @return data_flow_id - 结果配置编码
     */
    public String getDataFlowId() {
        return dataFlowId;
    }

    /**
     * 设置结果配置编码
     *
     * @param dataFlowId 结果配置编码
     */
    public void setDataFlowId(String dataFlowId) {
        this.dataFlowId = dataFlowId;
    }

    /**
     * 获取周期类型
     *
     * @return cycle_type - 周期类型
     */
    public String getCycleType() {
        return cycleType;
    }

    /**
     * 设置周期类型
     *
     * @param cycleType 周期类型
     */
    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    /**
     * 获取周期执行项
     *
     * @return cycle_value - 周期执行项
     */
    public String getCycleValue() {
        return cycleValue;
    }

    /**
     * 设置周期执行项
     *
     * @param cycleValue 周期执行项
     */
    public void setCycleValue(String cycleValue) {
        this.cycleValue = cycleValue;
    }

    /**
     * 获取最新结果文件
     *
     * @return data_file - 最新结果文件
     */
    public String getDataFile() {
        return dataFile;
    }

    /**
     * 设置最新结果文件
     *
     * @param dataFile 最新结果文件
     */
    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * 获取最新结果数量
     *
     * @return data_num - 最新结果数量
     */
    public String getDataNum() {
        return dataNum;
    }

    /**
     * 设置最新结果数量
     *
     * @param dataNum 最新结果数量
     */
    public void setDataNum(String dataNum) {
        this.dataNum = dataNum;
    }

    /**
     * 获取最新任务执行状态
     *
     * @return task_status - 最新任务执行状态
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * 设置最新任务执行状态
     *
     * @param taskStatus 最新任务执行状态
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 获取最新执行开始时间
     *
     * @return begin_time - 最新执行开始时间
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * 设置最新执行开始时间
     *
     * @param beginTime 最新执行开始时间
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取最新执行结束时间
     *
     * @return end_time - 最新执行结束时间
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置最新执行结束时间
     *
     * @param endTime 最新执行结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
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