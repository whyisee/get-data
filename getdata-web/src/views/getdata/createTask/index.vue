<template>

  <div class="createPost-container">
    <div style="height:44px;">

      <sticky :z-index="10" :sticky-top="-20">
        <p />
        <el-steps direction="" :active="1" style="sticky-steps" simple>
          <!-- <el-step title="步骤 1 选择数据源" />
          <el-step title="步骤 2 配置筛选条件" />
          <el-step title="步骤 3 选择展示指标" />
          <el-step title="步骤 4 配置执行结果" /> -->

          <el-step title="步骤 1" />
          <el-step title="步骤 2" />
          <el-step title="步骤 3" />
          <el-step title="步骤 4" />
        </el-steps>

      </sticky>
    </div>
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <!-- <sticky :z-index="200" :class-name="'sub-navbar '+postForm.status">

         <CommentDropdown v-model="postForm.comment_disabled" />
        <PlatformDropdown v-model="postForm.platforms" />
        <SourceUrlDropdown v-model="postForm.source_uri" />

      <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
        提交
      </el-button>
      <el-button v-loading="loading" type="warning" @click="draftForm">
        保存
      </el-button>
      </sticky> -->
      <div class="createPost-main-container">
        <Warning />

        <el-row>
          <el-col :span="24">
            <el-form-item label="任务编码" style="display:none">
              <el-input v-model="postForm.taskId" />
            </el-form-item>
            <el-form-item label="数据源流程" style="display:none">
              <el-input v-model="postForm.sourceFlowId" />
            </el-form-item>
            <el-form-item label="用户群流程" style="display:none">
              <el-input v-model="postForm.troopFlowId" />
            </el-form-item>
            <el-form-item label="筛选条件流程" style="display:none">
              <el-input v-model="postForm.condFlowId" />
            </el-form-item>
            <el-form-item label="展示指标流程" style="display:none">
              <el-input v-model="postForm.showFlowId" />
            </el-form-item>
            <el-form-item label="执行配置流程" style="display:none">
              <el-input v-model="postForm.execFlowId" />
            </el-form-item>
            <el-form-item label="结果配置流程" style="display:none">
              <el-input v-model="postForm.dataFlowId" />
            </el-form-item>
            <el-form-item style="margin-bottom: 10px;" prop="taskName">
              <MDinput v-model="postForm.taskName" :maxlength="100" name="name" required>
                取数任务名称
              </MDinput>
            </el-form-item>
            <el-form-item style="margin-bottom: 10px;" prop="remark">
              <MDinput v-model="postForm.remark" :maxlength="100" name="name" required>
                说明
              </MDinput>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row style="margin-top:10px;" :gutter="20">
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>数据源与用户群</span>
              </div>

              <el-col :span="10">
                <div class="data-source-list">
                  <div v-for="element in dataSources" :key="element.sourceId">
                    <el-card class="box-card" style="margin-top:10px;">
                      <div slot="header" class="clearfix">
                        <span> {{ '数据源名称:' + element.sourceNameZh }}</span>

                        <el-switch
                          v-model="element.isSelected"
                          style="float: right; "
                          active-color="#13ce66"
                          active-value="true"
                          active-text="选择"
                          inactive-value="false"
                          inactive-color="#ff4949"
                        />
                      </div>
                      <span>  {{ '指标数量:' + element.showTagNum }}</span>
                      <p />
                      <span>  {{ '主键:' + element.sourceKeyNameZh }} </span>
                      <p />
                      <span>  {{ '数据更新日期:' + element.updateDate }} </span>
                      <p />
                      <span>  {{ '数据源类型:' + element.sourceType }} </span>
                      <p />
                      <span :class="isDev%2 == 0 ? 'display-none' :'submit-view' ">  {{ '表名:' + element.sourceName }} </span>

                    </el-card>
                  </div>
                </div>
              </el-col>
              <!-- <UserTroop /> -->
              <div class="components-container board">

                <el-col :span="6" :offset="1">
                  <UserTroop ref="UserTroops" :key="1" :list="userTroops" :group="group" class="kanban todo" header-text="可使用用户群" height="470px" />
                </el-col>
                <el-col :span="6" :offset="1">

                  <UserTroop ref="UserTroopsSelect" :key="2" :list="userTroopsSelect" :group="group" class="kanban working" header-text="筛选用户群" complax-show="1" />
                  <UserTroop ref="UserTroopsDel" :key="3" :list="userTroopsDel" :group="group" class="kanban done" header-text="排除用户群" complax-show="1" />

                </el-col>
              </div>
            </el-card>

          </el-col>
        </el-row>

        <el-row style="margin-top:20px;">
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>筛选条件</span>
              </div>
              <div>
                <el-button type="warning" round @click="isDev=isDev+1;$refs.UserTroops.isDev=isDev;$refs.CondTags.isDev=isDev">开发模式</el-button>
                <el-button type="success" round :class="isDev%2 == 0 ? 'display-none' :'submit-view' " @click="formatSql()">格式化SQL</el-button>
                <el-input
                  v-model="postForm.execSql"
                  type="textarea"
                  :rows="8"
                  :class="isDev%2 == 0 ? 'display-none' :'submit-view' "
                  placeholder="请输入SQL"
                />
              </div>
              <div class="editor-container">
                <dnd-list ref="CondTags" :list1="dataSourceCondTags" :list2="userTroopCondTags" :list3="condTagsSelect" :list4="condTagsDel" list1-title="数据源指标列表:" list2-title="用户群指标列表" list3-title="已选择筛选条件" list4-title="已选择排除条件" />
              </div>

            </el-card>
          </el-col>
        </el-row>

        <el-row style="margin-top:20px;">
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>展示指标</span>
              </div>
              <div class="component-item">
                <el-col :span="12">
                  <el-form-item label-width="90px" label="数据源指标:" class="postInfo-container-item">
                    <el-drag-select v-model="dataSourceTag" value-key="tagName" style="width:400px;" multiple placeholder="数据源指标" filterable default-first-option>
                      <el-option v-for="item in dataSourceCondTags" :key="'dataSource.'+item.tagName" :label="item.tagNameZh" :value="item" />
                    </el-drag-select>
                  </el-form-item>
                </el-col>

                <el-col :span="12">
                  <el-form-item label-width="90px" label="用户群指标:" class="postInfo-container-item">
                    <el-drag-select v-model="userTroopTag" value-key="tagName" style="width:400px;" multiple placeholder="用户群指标" filterable default-first-option>
                      <el-option v-for="item in userTroopCondTags" :key="'userTroop.'+item.tagName" :label="item.tagNameZh" :value="item" />
                    </el-drag-select>
                  </el-form-item>
                </el-col>
                <el-col :span="24">

                  <div style="margin-top:30px;margin-bottom:30px">
                    <el-tag
                      v-for="(tag,indxe) of dataSourceTag.concat(userTroopTag).concat(userOtherTag)"
                      :key="indxe+'.'+tag.tagName"
                      closable
                      :disable-transitions="false"
                      style="margin-right:15px;"
                      @close="handleClose(tag)"
                    >

                      {{ tag.tagNameZh }}
                    </el-tag>
                    <el-input
                      v-if="inputVisible"
                      ref="saveTagInput"
                      v-model="inputValue"
                      class="input-new-tag"
                      size="small"
                      @keyup.enter.native="handleInputConfirm"
                      @blur="handleInputConfirm"
                    />
                    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 自定义</el-button>

                  </div>
                </el-col>

              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-card class="box-card" style="margin-top:20px;">

          <el-row>
            <div slot="header" class="clearfix">
              <span>执行控制与结果获取</span>
            </div>
            <el-col :span="24">
              <el-form-item label-width="120px" label="执行数据库:" class="postInfo-container-item">
                <el-select v-model="postForm.execType" :placeholder="$t('getdata.execType')" clearable class="filter-item" style="width: 200px">
                  <el-option v-for="item in execTypeOptions" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>

            </el-col>

            <el-col :span="8">
              <el-form-item label-width="120px" label="执行周期:" class="postInfo-container-item">
                <el-select v-model="postForm.cycleType" :placeholder="$t('getdata.cycleType')" clearable class="filter-item" style="width: 200px">
                  <el-option v-for="item in cycleTypeOptions" :key="item.key" :label="item.label" :value="item.key" />
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label-width="120px" label="周期执行日:" :class="(postForm.cycleType == 'O'||postForm.cycleType == 'D') ? 'display-none' :'postInfo-container-item'">
                <el-input v-model="postForm.cycleValue" :placeholder="$t('getdata.cycleValue')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
              </el-form-item>
            </el-col>

            <el-col :span="8 ">
              <el-form-item label-width="120px" label="周期结束时间:" :class="postForm.cycleType == 'O' ? 'display-none' :'postInfo-container-item' ">
                <el-date-picker
                  v-model="postForm.cycleEndDate"
                  align="right"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm:ss"
                  placeholder="周期结束时间"
                  :picker-options="pickerOptions"
                  style="width: 200px"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label-width="120px" label="结果保存方式:" class="postInfo-container-item">
                <el-checkbox-group
                  v-model="saveTypes"
                  :min="1"
                >
                  <el-checkbox
                    v-for="saveType in saveTypeOptions"
                    :key="saveType.label"
                    :label="saveType.key"
                    :disabled="saveType.key === 'D'"
                  >
                    {{ saveType.label }}

                  </el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>

            <el-col :span="8 ">
              <el-form-item label-width="120px" label="用户群失效日期:" :class="saveTypes.indexOf('U')==-1 ? 'display-none' :'postInfo-container-item' ">
                <el-date-picker
                  v-model="postForm.troopEndDate"
                  align="right"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm:ss"
                  placeholder="用户群失效时间"
                  :picker-options="pickerOptions"
                  style="width: 200px;"
                />
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label-width="120px" label="对接接口:" :class="saveTypes.indexOf('I')==-1 ? 'display-none' :'postInfo-container-item'">
                <el-select v-model="postForm.interType" :placeholder="$t('getdata.interType')" clearable class="filter-item" style="width: 200px">
                  <el-option v-for="item in fileTypeOptions" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
            </el-col>

          </el-row>

          <el-row>

            <el-col :span="8">
              <el-form-item label-width="120px" label="文件拆分方式:" class="postInfo-container-item">
                <el-select v-model="postForm.fileSplit" :placeholder="$t('getdata.fileSplit')" clearable class="filter-item" style="width: 200px">
                  <el-option v-for="item in fileSplitOptions" :key="item.key" :label="item.label" :value="item.key" />
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label-width="120px" label="文件条数/大小:" :class="postForm.fileSplit === 'N' ? 'display-none' :'postInfo-container-item' ">
                <el-input v-model="postForm.fileSplitValue" :placeholder="$t('getdata.fileSize')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label-width="120px" label="下载文件类型:" class="postInfo-container-item">
                <el-select v-model="postForm.fileType" :placeholder="$t('getdata.fileType')" clearable class="filter-item" style="width: 200px">
                  <el-option v-for="item in fileTypeOptions" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" label="文件分隔符:" class="postInfo-container-item">
                <el-select v-model="postForm.fileSeparator" :placeholder="$t('getdata.fileSeparator')" clearable class="filter-item" style="width: 200px">
                  <el-option v-for="item in fileSeparatorOptions" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label-width="120px" label="压缩方式:" class="postInfo-container-item">
                <el-select v-model="postForm.zipType" :placeholder="$t('getdata.zipType')" clearable class="filter-item" style="width: 200px">
                  <el-option v-for="item in zipTypeOptions" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label-width="120px" label="解压密码:" class="postInfo-container-item">
                <el-input v-model="postForm.zipEncryption" :placeholder="$t('getdata.zipEncryption')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
              </el-form-item>
            </el-col>

          </el-row>
        </el-card>

        <div :class="isEdit == '0' ? 'display-none' :'submit-view' ">
          <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
            提交
          </el-button>
          <el-button v-loading="loading" type="warning" @click="submitForm">
            保存
          </el-button>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>

import Sticky from '@/components/Sticky' // 粘性header组件
import Warning from '../components/Warning'
import MDinput from '@/components/MDinput'
import ElDragSelect from '@/components/DragSelect'
import DndList from '../components/UserTagCondList'
import UserTroop from '../components/UserTroop'
import { createTask, updateTask, getFlowId, getTask, getConfigFlow } from '@/api/getdata'
import { createTaskInit } from '@/api/data-source-troop'
import { getSourceTagList, getTroopTagList } from '@/api/user-tag'
import sqlFormatter from 'sql-formatter'

// const saveTypeOptions = ['下载', '用户群', '接口']

const defaultForm = {
  taskId: '', // 任务编码
  taskName: '', // 任务名称
  taskStatus: '0', // 任务状态 默认-草稿
  remark: '', // 说明备注
  execType: 'mysql', // 执行数据库-开发时默认mysql
  cycleType: 'O', // 周期类型-默认-一次性执行
  fileType: 'csv', // 下载文件类型,默认-csv
  fileSeparator: ',', // 文件分隔符,默认-逗号
  fileSplit: 'N', // 文件拆分方式,默认-不拆分
  zipType: 'zip', // 压缩方式,默认-zip
  dataSourcesSelect: [] // 选择的数据源

}
export default {
  name: 'CreateTask',
  components: { MDinput, Sticky, Warning, ElDragSelect, DndList, UserTroop },
  filters: {
  },
  props: {},
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.message,
          type: 'error'
        })
        callback(new Error(rule.message))
      } else {
        callback()
      }
    }
    return {
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1
      },
      isDev: 0,
      postForm: defaultForm,
      // this.postForm.dataConfig:{},
      userTroopsSelect: [], // 筛选用户群
      userTroopsDel: [], // 排除用户群
      // postForm: Object.assign({}, defaultForm),
      loading: false,
      userListOptions: [],
      execTypeOptions: ['mysql', 'hive'],
      saveTypes: ['D'],

      cycleTypeOptions: [
        { label: '一次性执行', key: 'O' },
        { label: '每天执行', key: 'D' },
        { label: '每月执行', key: 'M' },
        { label: '每周执行', key: 'W' }],
      saveTypeOptions: [{ key: 'D', label: '下载' }, { key: 'U', label: '用户群' }, { key: 'I', label: '接口' }],
      fileTypeOptions: ['csv', 'txt'],
      fileSeparatorOptions: [',', '|'],
      fileSplitOptions: [{ label: '不拆分', key: 'N' }, { label: '按条数拆分', key: 'L' }, { label: '按大小拆分', key: 'S' }],
      zipTypeOptions: ['zip', 'rar', '7z', 'gz'],
      isEdit: '1',
      rules: {
        taskName: [{ validator: validateRequire, message: '取数任务名称为必填项!' }]
      },
      tempRoute: {},
      dataSources: [],
      userTroops: [],
      dataSourceCondTags: [],
      userTroopCondTags: [],
      condTagsSelect: [],
      condTagsDel: [],
      dataSourceTag: [],
      dataShowTag: [],

      dataSourceTagOptions: [],
      userTroopTag: [],
      userTroopTagOptions: [],
      userOtherTag: [],
      group: 'mission',
      inputVisible: false,
      inputValue: '',
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now()
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      createDate: ''
    }
  },
  watch: {
    dataSources: {
      handler: function(val, oldVal) {
        this.postForm.dataSourcesSelect = []
        for (const item of this.dataSources) {
          if (item.isSelected === 'true') {
            this.postForm.dataSourcesSelect = this.postForm.dataSourcesSelect.concat(item)
          }
        }

        this.dataSourceCondTagList()
      },
      deep: true
    },
    userTroopsSelect: {
      handler: function(val, oldVal) {
        this.userTroopCondTags = []
        this.troopCondTagList()
      }

    }
  },
  created() {
    this.postForm = defaultForm
    this.init()

    if (JSON.stringify(this.$route.query) !== '{}') {
      const taskId = this.$route.query.taskId
      this.getTask(taskId)
      this.isEdit = this.$route.query.isEdit
    }
  },
  methods: {
    beforeRouteEnter(to, from, next) {
      next((vm) => {
        vm.list = JSON.parse(localStorage.getItem('params')).detail
        vm.projectStatus = JSON.parse(localStorage.getItem('params')).projectStatus
      })
    },
    getTask(id) {
      getTask(id).then(response => {
        this.postForm = response.data
        // 反显数据源
        let data = { 'flowId': this.postForm.sourceFlowId, 'parentFlowId': id, 'flow_key': 'dataSourceConfig' }
        getConfigFlow(data).then(response => {
          const dataSourcesSelect = response.data.list[0].flowValue1
          for (const item of this.dataSources) {
            if (dataSourcesSelect.indexOf(item.sourceId) !== -1) {
              item.isSelected = 'true'
            }
          }
        })

        // 用户群
        data = { 'flowId': this.postForm.troopFlowId, 'parentFlowId': id, 'flow_key': 'userTroopConfig' }
        getConfigFlow(data).then(response => {
          const userTroopsSelect = response.data.list[0].flowValue1
          const userTroopsDel = response.data.list[0].flowValue3

          this.userTroopsSelect = JSON.parse(response.data.list[0].flowValue5).userTroopsSelect
          this.userTroopsDel = JSON.parse(response.data.list[0].flowValue5).userTroopsDel

          this.userTroops = this.userTroops.filter(t => userTroopsSelect.indexOf(t.troopId) === -1 && userTroopsDel.indexOf(t.troopId) === -1)
        })

        // 筛选条件
        data = { 'flowId': this.postForm.condFlowId, 'parentFlowId': id, 'flow_key': 'condTagConfig' }
        getConfigFlow(data).then(response => {
          this.condTagsSelect = JSON.parse(response.data.list[0].flowValue5).condTagsSelect
          this.condTagsDel = JSON.parse(response.data.list[0].flowValue5).condTagsDel
        })

        // 展示指标
        data = { 'flowId': this.postForm.showFlowId, 'parentFlowId': id, 'flow_key': 'showTagConfig' }
        getConfigFlow(data).then(response => {
          this.dataSourceTag = JSON.parse(response.data.list[0].flowValue5).dataSourceTag
          this.userTroopTag = JSON.parse(response.data.list[0].flowValue5).userTroopTag
          this.userOtherTag = JSON.parse(response.data.list[0].flowValue5).userOtherTag
        })

        // 执行配置

        // 结果配置
        data = { 'flowId': this.postForm.dataFlowId, 'parentFlowId': id, 'flow_key': 'dataFlowConfig' }
        getConfigFlow(data).then(response => {
          var dataConfig = JSON.parse(response.data.list[0].flowValue5).dataConfig
          console.log(dataConfig.saveTypes)

          this.saveTypes = dataConfig.saveTypes
          // this.postForm.troopEndDate = dataConfig.troopEndDate
          this.$set(this.postForm, 'troopEndDate', dataConfig.troopEndDate)
          this.$set(this.postForm, 'interType', dataConfig.interType)
          this.$set(this.postForm, 'fileSplit', dataConfig.fileSplit)
          this.$set(this.postForm, 'fileSplitValue', dataConfig.fileSplitValue)
          this.$set(this.postForm, 'fileType', dataConfig.fileType)
          this.$set(this.postForm, 'fileSeparator', dataConfig.fileSeparator)
          this.$set(this.postForm, 'zipType', dataConfig.zipType)
          this.$set(this.postForm, 'zipEncryption', dataConfig.zipEncryption)

          // this.postForm.interType = dataConfig.interType
          // this.postForm.fileSplit = dataConfig.fileSplit
          // this.postForm.fileSplitValue = dataConfig.fileSplitValue
          // this.postForm.fileType = dataConfig.fileType
          // this.postForm.fileSeparator = dataConfig.fileSeparator
          // this.postForm.zipType = dataConfig.zipType
          // this.postForm.zipEncryption = dataConfig.zipEncryption
          console.log(this.postForm)
        })
      }).catch(err => {
        console.log(err)
      })
    },
    init() {
      this.listLoading = true
      createTaskInit().then(response => {
        this.dataSources = response[0].data.list
        this.userTroops = response[1].data.list
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },
    getFlowId() {
      getFlowId().then(response => {
        const { data } = response
        console.log(data)
        return data
      })
    },

    dataSourceCondTagList() {
      this.listLoading = true
      var tagFromId = '1'
      for (const item of this.postForm.dataSourcesSelect) {
        tagFromId = tagFromId + ',' + item.sourceId
      }
      getSourceTagList({ 'tagFromId': tagFromId, 'limit': 10000 }).then(response => {
        this.dataSourceCondTags = response.data.list
        this.dataSourceTagOptions = response.data.list
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },
    troopCondTagList() {
      this.listLoading = true
      var tagFromId = '1'
      for (const item of this.userTroopsSelect) {
        tagFromId = tagFromId + ',' + item.troopId
      }
      getTroopTagList({ 'tagFromId': tagFromId }).then(response => {
        this.userTroopCondTags = response.data.list
        // this.userTroopCondTags = response.data.list
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },
    submitForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
          // 保存用户群信息
          this.postForm.userTroopsSelect = this.userTroopsSelect
          this.postForm.userTroopsSelectCP = this.$refs.UserTroopsSelect.complaxType
          this.postForm.userTroopsDel = this.userTroopsDel
          this.postForm.userTroopsDelCP = this.$refs.UserTroopsDel.complaxType

          // 筛选条件
          this.postForm.condTagsSelect = this.condTagsSelect
          this.postForm.condTagsDel = this.condTagsDel

          // 展示指标
          this.postForm.dataSourceTag = this.dataSourceTag
          this.postForm.userTroopTag = this.userTroopTag
          this.postForm.userOtherTag = this.userOtherTag

          // 执行配置
          this.postForm.execConfig = {
            'execType': this.postForm.execType,
            'cycleType': this.postForm.cycleType,
            'cycleValue': this.postForm.cycleValue,
            'cycleEndDate': this.postForm.cycleEndDate
          }

          // 结果配置
          this.postForm.dataConfig = {}
          console.log(this.postForm.dataConfig)

          this.postForm.dataConfig.saveTypes = this.saveTypes
          this.postForm.dataConfig.troopEndDate = this.postForm.troopEndDate
          this.postForm.dataConfig.interType = this.postForm.interType
          this.postForm.dataConfig.fileSplit = this.postForm.fileSplit

          this.postForm.dataConfig.fileSplitValue = this.postForm.fileSplitValue
          this.postForm.dataConfig.fileType = this.postForm.fileType
          this.postForm.dataConfig.fileSeparator = this.postForm.fileSeparator
          this.postForm.dataConfig.zipType = this.postForm.zipType
          this.postForm.dataConfig.zipEncryption = this.postForm.zipEncryption

          if (this.postForm.taskId === '') {
            createTask(this.postForm).then(response => {
              this.postForm.taskId = response.data.taskId
              this.postForm.sourceFlowId = response.data.sourceFlowId
              this.postForm.troopFlowId = response.data.troopFlowId
              this.postForm.condFlowId = response.data.condFlowId
              this.postForm.showFlowId = response.data.showFlowId
              this.postForm.execFlowId = response.data.execFlowId
              this.postForm.dataFlowId = response.data.dataFlowId

              setTimeout(() => {
                this.listLoading = false
              }, 1 * 1000)
            })
          } else {
            updateTask(this.postForm).then(response => {
              this.postForm.taskId = response.data.taskId
              this.postForm.sourceFlowId = response.data.sourceFlowId
              this.postForm.troopFlowId = response.data.troopFlowId
              this.postForm.condFlowId = response.data.condFlowId
              this.postForm.showFlowId = response.data.showFlowId
              this.postForm.execFlowId = response.data.execFlowId
              this.postForm.dataFlowId = response.data.dataFlowId
              setTimeout(() => {
                this.listLoading = false
              }, 1 * 1000)
            })
          }
          this.loading = true
          this.$notify({
            title: '成功',
            message: '保存取数任务成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }, formatSql() {
      /* 获取文本编辑器内容*/
      let sqlContent = ''
      sqlContent = this.postForm.execSql
      /* 将sql内容进行格式后放入编辑器中*/
      this.postForm.execSql = sqlFormatter.format(sqlContent)
    }, handleClose(tag) {
      if (this.userOtherTag.indexOf(tag) !== -1) {
        this.userOtherTag.splice(this.userOtherTag.indexOf(tag), 1)
      }
      if (this.userTroopTag.indexOf(tag) !== -1) {
        this.userTroopTag.splice(this.userTroopTag.indexOf(tag), 1)
      }
      if (this.dataSourceTag.indexOf(tag) !== -1) {
        this.dataSourceTag.splice(this.dataSourceTag.indexOf(tag), 1)
      }
    },
    showInput() {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm() {
      const inputValue = this.inputValue
      if (inputValue) {
        const item = { 'tagNameZh': inputValue, 'tagName': inputValue }

        this.userOtherTag.push(item)
      }
      this.inputVisible = false
      this.inputValue = ''
    },

    getRemoteUserList(query) {
      createTask(query).then(response => {
        if (!response.data.items) return
        this.userListOptions = response.data.items.map(v => v.name)
      })
    }
  }
}

</script>
<style lang="scss" >
@import "~@/styles/mixin.scss";

.createPost-container {
  position: relative;
  .createPost-main-container {
    padding: 0px 45px 20px 50px;
    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;

      .postInfo-container-item {
        float: left;
      }
    }
  }
  .sticky-steps{
    background: white;

  }
  .word-counter {
    width: 40px;
    position: absolute;
    right: 10px;
    top: 0px;
  }
}
.submit-view{
  align-items: flex-start;
  text-align: center;
    margin-top: 20px;

}
.data-source-list {
  max-height :520px;
  overflow:auto;
  margin-top:10px
}
.article-textarea ::v-deep {
  textarea {
    padding-right: 40px;
    resize: none;
    border: none;
    border-radius: 0px;
    border-bottom: 1px solid #bfcbd9;
  }
.board {
  width: 1000px;
  margin-left: 20px;
  display: flex;
  justify-content: space-around;
  flex-direction: row;
  align-items: flex-start;
}
.kanban {
 &.todo {
    .board-column-header {
      background: #4A9FF9;
    }
  }
  &.working {
    .board-column-header {
      background: #f9944a;
    }
  }
  &.done {
    .board-column-header {
      background: #2ac06d;
    }
  }
}
}
</style>
