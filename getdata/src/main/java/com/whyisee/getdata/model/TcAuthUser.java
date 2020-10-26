package com.whyisee.getdata.model;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "tc_auth_user")
public class TcAuthUser   {
    /**
     * 用户编码
     */
    @Id
    @Column(name = "persion_id")
    private String persionId;

    /**
     * 用户登录名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 用户昵称
     */
    @Column(name = "show_name")
    private String showName;

    /**
     * 用户真实姓名
     */
    @Column(name = "true_name")
    private String trueName;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户生日
     */
    private String birthday;

    /**
     * 用户联系方式
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态
     */
    @Column(name = "user_status")
    private String userStatus;

    /**
     * 用户上次登录时间
     */
    @Column(name = "last_login_date")
    private String lastLoginDate;

    /**
     * 用户角色编码
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 用户部门编码
     */
    @Column(name = "depart_id")
    private String departId;

    /**
     * 用户分组编码
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * 账号有效开始时间
     */
    @Column(name = "begin_date")
    private String beginDate;

    /**
     * 账号有效结束时间
     */
    @Column(name = "end_date")
    private String endDate;

    /**
     * 用户创建方式
     */
    @Column(name = "create_type")
    private String createType;

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
     * 获取用户编码
     *
     * @return persion_id - 用户编码
     */
    public String getPersionId() {
        return persionId;
    }

    /**
     * 设置用户编码
     *
     * @param persionId 用户编码
     */
    public void setPersionId(String persionId) {
        this.persionId = persionId;
    }

    /**
     * 获取用户登录名
     *
     * @return login_name - 用户登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置用户登录名
     *
     * @param loginName 用户登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取用户昵称
     *
     * @return show_name - 用户昵称
     */
    public String getShowName() {
        return showName;
    }

    /**
     * 设置用户昵称
     *
     * @param showName 用户昵称
     */
    public void setShowName(String showName) {
        this.showName = showName;
    }

    /**
     * 获取用户真实姓名
     *
     * @return true_name - 用户真实姓名
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * 设置用户真实姓名
     *
     * @param trueName 用户真实姓名
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    /**
     * 获取用户性别
     *
     * @return sex - 用户性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置用户性别
     *
     * @param sex 用户性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取用户生日
     *
     * @return birthday - 用户生日
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置用户生日
     *
     * @param birthday 用户生日
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取用户联系方式
     *
     * @return phone_number - 用户联系方式
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置用户联系方式
     *
     * @param phoneNumber 用户联系方式
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取用户邮箱
     *
     * @return email - 用户邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置用户邮箱
     *
     * @param email 用户邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }



    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }




    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户状态
     *
     * @return user_status - 用户状态
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * 设置用户状态
     *
     * @param userStatus 用户状态
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取用户上次登录时间
     *
     * @return last_login_date - 用户上次登录时间
     */
    public String getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * 设置用户上次登录时间
     *
     * @param lastLoginDate 用户上次登录时间
     */
    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * 获取用户角色编码
     *
     * @return role_id - 用户角色编码
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置用户角色编码
     *
     * @param roleId 用户角色编码
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户部门编码
     *
     * @return depart_id - 用户部门编码
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * 设置用户部门编码
     *
     * @param departId 用户部门编码
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    /**
     * 获取用户分组编码
     *
     * @return group_id - 用户分组编码
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置用户分组编码
     *
     * @param groupId 用户分组编码
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取账号有效开始时间
     *
     * @return begin_date - 账号有效开始时间
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * 设置账号有效开始时间
     *
     * @param beginDate 账号有效开始时间
     */
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 获取账号有效结束时间
     *
     * @return end_date - 账号有效结束时间
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置账号有效结束时间
     *
     * @param endDate 账号有效结束时间
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取用户创建方式
     *
     * @return create_type - 用户创建方式
     */
    public String getCreateType() {
        return createType;
    }

    /**
     * 设置用户创建方式
     *
     * @param createType 用户创建方式
     */
    public void setCreateType(String createType) {
        this.createType = createType;
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

    @Override
    public String toString() {
        return "TcAuthUser{" +
                "persionId='" + persionId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", showName='" + showName + '\'' +
                ", trueName='" + trueName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", roleId='" + roleId + '\'' +
                ", departId='" + departId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", createType='" + createType + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", createPersion='" + createPersion + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updatePersion='" + updatePersion + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}