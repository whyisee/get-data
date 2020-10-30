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
            <el-form-item style="margin-bottom: 10px;" prop="taskName" error="取数任务名称为必填项!">
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
                <div v-for="element in dataSources" :key="element.sourceId">
                  <el-card class="box-card" style="margin-top:10px;">
                    <div slot="header" class="clearfix">
                      <span> {{ '数据源名称:' + element.sourceNameZh }}</span>

                      <el-button style="float: right; padding: 3px " type="text">选择
                        <el-switch
                          v-model="element.value2"
                          active-color="#13ce66"
                          inactive-color="#ff4949"
                        />
                      </el-button>
                    </div>
                    <span>  {{ '展示指标数量:' + element.showTagNum }}</span>
                    <p />
                    <span>  {{ '筛选指标数量:' + element.condTagNum }} </span>
                    <p />
                    <span>  {{ '数据更新日期:' + element.updateDate }} </span>
                    <p />
                    <span>  {{ '数据源类型:' + element.sourceType }} </span>

                  </el-card>
                </div>
              </el-col>
              <!-- <UserTroop /> -->
              <div class="components-container board">

                <el-col :span="6" :offset="1">
                  <UserTroop :key="1" :list="list1" :group="group" class="kanban todo" header-text="可使用用户群" height="470px" />
                </el-col>
                <el-col :span="6" :offset="1">

                  <UserTroop :key="2" :list="list2" :group="group" class="kanban working" header-text="筛选用户群" complax-show="1" />
                  <UserTroop :key="3" :list="list3" :group="group" class="kanban done" header-text="排除用户群" complax-show="1" />

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
              <div class="editor-container">
                <dnd-list :list1="list1" :list2="list2" :list3="list3" :list4="list4" list1-title="数据源指标列表:" list2-title="用户群指标列表" list3-title="已选择筛选条件" list4-title="已选择排除条件" />
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
                    <el-drag-select v-model="dataSourceTag" style="width:400px;" multiple placeholder="数据源指标">
                      <el-option v-for="item in dataSourceTagOptions" :key="item.value" :label="item.label" :value="item.value" />
                    </el-drag-select>
                  </el-form-item>
                </el-col>

                <el-col :span="12">
                  <el-form-item label-width="90px" label="用户群指标:" class="postInfo-container-item">
                    <el-drag-select v-model="userTroopTag" style="width:400px;" multiple placeholder="用户群指标">
                      <el-option v-for="item in userTroopTagOptions" :key="item.value" :label="item.label" :value="item.value" />
                    </el-drag-select>
                  </el-form-item>
                </el-col>
                <el-col :span="24">

                  <div style="margin-top:30px;margin-bottom:30px">
                    <el-tag v-for="item of dataSourceTag.concat(userTroopTag)" :key="item" style="margin-right:15px;">
                      {{ item }}
                    </el-tag>

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
              <el-form-item label-width="120px" label="周期执行日:" class="postInfo-container-item" style="display: none">
                <el-input v-model="postForm.cycleValue" :placeholder="$t('getdata.cycleValue')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
              </el-form-item>
            </el-col>

            <el-col :span="8 ">
              <el-form-item label-width="120px" label="周期结束时间:" class="postInfo-container-item" style="display: none">
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
                    :key="saveType"
                    :label="saveType"
                    :disabled="saveType === '下载'"
                  >
                    {{ saveType }}

                  </el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>

            <el-col :span="8 ">
              <el-form-item label-width="120px" label="用户群失效日期:" class="postInfo-container-item" style="display: none">
                <el-date-picker
                  v-model="createDate"
                  align="right"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm:ss"
                  placeholder="创建时间"
                  :picker-options="pickerOptions"
                  style="width: 200px;"
                />
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item v-show="saveTypes.length === '3'" label-width="120px" label="对接接口:" class="postInfo-container-item">
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
              <el-form-item label-width="120px" label="文件条数/大小:" class="postInfo-container-item" style="display: none">
                <el-input v-model="postForm.cycleValue" :placeholder="$t('getdata.fileSize')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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

        <div class="submit-view">
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
import DndList from '../components/DndList'
import UserTroop from '../components/UserTroop'
import { createTask, updateTask } from '@/api/getdata'

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
  zipType: 'zip' // 压缩方式,默认-zip

}
export default {
  name: 'CreateTask',
  components: { MDinput, Sticky, Warning, ElDragSelect, DndList, UserTroop },
  filters: {
  },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }},
  data() {
    const validateRequire = (rule, value, callback) => {
      console.log(rule)
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
      postForm: defaultForm,
      // postForm: Object.assign({}, defaultForm),
      loading: false,
      userListOptions: [],
      execTypeOptions: ['mysql', 'hive'],
      cycleTypeOptions: [
        { label: '一次性执行', key: 'O' },
        { label: '每天执行', key: 'D' },
        { label: '每月执行', key: 'M' },
        { label: '每周执行', key: 'W' }],
      saveTypes: ['下载'],
      saveTypeOptions: ['下载', '用户群', '接口'],
      fileTypeOptions: ['csv', 'txt'],
      fileSeparatorOptions: [',', '|'],
      fileSplitOptions: [{ label: '不拆分', key: 'N' }, { label: '按条数拆分', key: 'L' }, { label: '按大小拆分', key: 'S' }],
      zipTypeOptions: ['zip', 'rar', '7z', 'gz'],
      rules: {
        taskName: [{ validator: validateRequire, message: '取数任务名称为必填项!' }]
      },
      tempRoute: {},
      dataSources: [
        { id: '1', title: '业务数据表', href: '' },
        { id: '2', title: '登录权限表', href: '' }

      ],

      dataShowTag: [],
      dataSourceTag: ['Apple'],

      dataSourceTagOptions: [{
        value: 'Apple',
        label: 'Apple'
      }, {
        value: 'Banana1',
        label: 'Banana1'
      },

      // {
      //   value: 'Banana2',
      //   label: 'Banana2'
      // }, {
      //   value: 'Banana3',
      //   label: 'Banana3'
      // }, {
      //   value: 'Banana4',
      //   label: 'Banana4'
      // }, {
      //   value: 'Banana5',
      //   label: 'Banana5'
      // }, {
      //   value: 'Banana6',
      //   label: 'Banana6'
      // }, {
      //   value: 'Banana7',
      //   label: 'Banana7'
      // }, {
      //   value: 'Banana8',
      //   label: 'Banana8'
      // }, {
      //   value: 'Banana9',
      //   label: 'Banana9'
      // }, {
      //   value: 'Banana10',
      //   label: 'Banana10'
      // }, {
      //   value: 'Banana11',
      //   label: 'Banana11'
      // }, {
      //   value: 'Banana12',
      //   label: 'Banana12'
      // }, {
      //   value: 'Banana13',
      //   label: 'Banana13'
      // },

      {
        value: 'Orange',
        label: 'Orange'
      }],
      userTroopTag: ['Apple1'],
      userTroopTagOptions: [{
        value: 'Apple1',
        label: 'Apple1'
      }, {
        value: 'Banana1',
        label: 'Banana1'
      }, {
        value: 'Orange1',
        label: 'Orange1'
      }],
      group: 'mission',
      list1: [
        { name: '测试1', id: 1, num: 123 },
        { name: '测试2', id: 2, num: 223 },
        { name: '测试3', id: 3, num: 323 }
      ],
      list2: [
        { name: '测试5', id: 5, num: 523 },
        { name: '测试6', id: 6, num: 623 },
        { name: '测试7', id: 7, num: 723 }
      ],
      list3: [

      ], list4: [

      ],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
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
    saveTypes(valArr) {
      console.log(valArr)
      // this.formThead = this.formTheadOptions.filter(i => valArr.indexOf(i) >= 0)
      // this.key = this.key + 1// 为了保证table 每次都会重渲 In order to ensure the table will be re-rendered each time
    }
  },
  created() {
    // this.fetchData(id)
    // console.log(postForm)
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    }
    this.postForm = defaultForm
    // Why need to make a copy of this.$route here?
    // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
    // https://github.com/PanJiaChen/vue-element-admin/issues/1221
    // this.tempRoute = Object.assign({}, this.$route)
  },
  methods: {
    fetchData(id) {
      this.postForm = defaultForm
      // fetchArticle(id).then(response => {
      //   this.postForm = response.data
      //   // just for test
      //   this.postForm.title += `   Article Id:${this.postForm.id}`
      //   this.postForm.content_short += `   Article Id:${this.postForm.id}`

      //   // set tagsview title
      //   this.setTagsViewTitle()

      //   // set page title
      //   this.setPageTitle()
      // }).catch(err => {
      //   console.log(err)
      // })
    },

    submitForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
        //   createTask(this.listQuery).then(response => {
          //     setTimeout(() => {
          //       this.listLoading = false
          //     }, 1 * 1000)
          //   })

          if (this.postForm.taskId === '') {
            createTask(this.postForm).then(response => {
              this.postForm.taskId = response.data
              console.log(response)
              setTimeout(() => {
                this.listLoading = false
              }, 1 * 1000)
            })
          } else {
            updateTask(this.postForm).then(response => {
              console.log(response)
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
    },
    draftForm() {
      // createTask()
      if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        })
        return
      }
      this.$message({
        message: '保存成功',
        type: 'success',
        showClose: true,
        duration: 1000
      })
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
