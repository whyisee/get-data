<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.taskId" :placeholder="$t('getdata.taskId')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.taskName" :placeholder="$t('getdata.taskName')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-select v-model="listQuery.taskStatus" :placeholder="$t('getdata.taskStatus')" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in taskStatusOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>

      <el-date-picker
        v-model="listQuery.createDate"
        align="right"
        type="datetime"
        format="yyyy-MM-dd HH:mm:ss"
        placeholder="创建时间"
        :picker-options="pickerOptions"
        style="width: 180px"
      />

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        {{ $t('getdata.add') }}
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        {{ $t('table.export') }}
      </el-button>
      <!-- <el-checkbox v-model="showReviewer" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">
        {{ $t('table.reviewer') }}
      </el-checkbox> -->
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column :label="$t('getdata.taskId')" prop="taskId" sortable="custom" align="center" width="200" :class-name="getSortClass('taskId')">
        <template slot-scope="{row}">
          <span>{{ row.taskId }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('getdata.taskName')" width="200" align="center">
        <template slot-scope="{row}">
          <span>{{ row.taskName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('getdata.taskStatus')" width="130">
        <template slot-scope="{row}">
          <span>{{ row.taskStatus | typeFilter }}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="showReviewer" :label="$t('getdata.createPersion')" width="110px" align="center">
        <template slot-scope="{row}">
          <span style="color:red;">{{ row.createPersion }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('getdata.createDate')" class-name="status-col" width="180">
        <template slot-scope="{row}">
          <span style="color:red;">{{ row.createDate }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('getdata.endTime')" class-name="status-col" width="180">
        <template slot-scope="{row}">
          <span style="color:red;">{{ row.endTime }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('getdata.dataNum')" width="99" align="center">
        <template slot-scope="{row}">
          <span>{{ row.dataNum }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('getdata.actions')" align="left" width="280" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button size="mini" type="success">
            <router-link :to="{path:'/dataManager/getdata/createTask',query: {taskId: row.taskId,isEdit:0}} ">
              {{ $t('getdata.view') }}
            </router-link>

          </el-button>
          <el-button v-if="row.taskStatus=='0' || row.taskStatus=='4'" type="primary" size="mini">
            <router-link :to="{path:'/dataManager/getdata/createTask',query: {taskId: row.taskId,isEdit:1}} ">
              {{ $t('table.edit') }}

            </router-link>
          </el-button>
          <el-button v-if="row.taskStatus=='0'" size="mini" type="success" @click="handleModifyStatus(row,'published')">
            {{ $t('getdata.submit') }}
          </el-button>
          <el-button v-if="row.taskStatus=='1' || row.taskStatus=='2'" size="mini" @click="handleModifyStatus(row,'draft')">
            {{ $t('getdata.cancel') }}
          </el-button>
          <el-button v-if="row.taskStatus=='0' || row.taskStatus=='3'|| row.taskStatus=='4'" size="mini" type="danger" @click="handleDelete(row,$index)">
            {{ $t('table.delete') }}
          </el-button>

          <el-button v-if="row.taskStatus=='20'" size="mini" type="danger" @click="handleDelete(row,$index)">
            {{ $t('table.download') }}
          </el-button>
          <!-- <el-button size="mini" type="primary" @click="handleDelete(row,$index)">
            {{ $t('getdata.copy') }}
          </el-button> -->

        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    <!--
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('table.type')" prop="type">
          <el-select v-model="temp.type" class="filter-item" placeholder="Please select">
            <el-option v-for="item in calendarTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('table.date')" prop="timestamp">
          <el-date-picker v-model="temp.timestamp" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item :label="$t('table.title')" prop="title">
          <el-input v-model="temp.title" />
        </el-form-item>
        <el-form-item :label="$t('table.status')">
          <el-select v-model="temp.status" class="filter-item" placeholder="Please select">
            <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('table.importance')">
          <el-rate v-model="temp.importance" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" :max="3" style="margin-top:8px;" />
        </el-form-item>
        <el-form-item :label="$t('table.remark')">
          <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </el-dialog> -->

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{ $t('table.confirm') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getTaskList, deleteTask } from '@/api/getdata'

import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const taskStatusOptions = [
  { key: '0', display_name: '草稿' },
  { key: '1', display_name: '已提交' },
  { key: '2', display_name: '执行中' },
  { key: '3', display_name: '已完成' },
  { key: '4', display_name: '异常' },
  { key: '10', display_name: '审批中' },
  { key: '11', display_name: '未通过' },
  { key: '20', display_name: '可下载' }

]

// arr to obj, such as { CN : "China", US : "USA" }
const taskStatusKeyValue = taskStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'TaskList',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return taskStatusKeyValue[type]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+taskId'
      },
      taskStatusOptions,
      sortOptions: [{ label: 'ID Ascending', key: '+taskId' }, { label: 'ID Descending', key: '-taskId' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false,
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
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      getTaskList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      // console.log(this.listQuery)
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'taskId') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+taskId'
      } else {
        this.listQuery.sort = '-taskId'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },

    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },

    handleDelete(row, index) {
      this.$notify({
        title: '成功',
        message: '删除成功',
        type: 'success',
        duration: 2000
      })
      this.list.splice(index, 1)
      this.listLoading = true
      console.log(row)
      // http://10.0.2.52:8089/getdata/file/download?fileName=test
      deleteTask(row).then(response => {
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },

    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['任务编码', '任务名称', '任务状态', '创建时间', '完成时间']
        const filterVal = ['taskId', 'taskName', 'taskStatus', 'createDate', 'endTime']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '任务列表'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
