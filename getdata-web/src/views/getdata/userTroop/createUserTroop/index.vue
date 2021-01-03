<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <div class="createPost-main-container">

        <el-row>
          <el-col :span="24">
            <el-form-item style="margin-bottom: 10px;" prop="troopNameZh">
              <MDinput v-model="postForm.troopNameZh" :maxlength="100" name="name" required>
                用户群名称
              </MDinput>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="数据来源类型:" class="postInfo-container-item">
              <el-select v-model="postForm.createType" :placeholder="$t('getdata.createType')" clearable class="filter-item" style="width: 200px">
                <el-option v-for="item in createTypeOptions" :key="item.key" :label="item.label" :value="item.key" />
              </el-select>
            </el-form-item>

          </el-col>
        </el-row>
        <el-row v-if="postForm.createType=='file'" style="margin-top:10px;" :gutter="20">
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>文件导入</span>
              </div>
              <el-upload
                ref="uploadExcel"
                class="upload-demo"
                drag
                action="#"
                :multiple="false"
                :show-file-list="true"
                :http-request="uploadHttpRequest"
                :file-list="fileList"
                :on-change="handleUploadChange"
                :before-upload="beforeUpload"
              >
                <i class="el-icon-upload" />
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              </el-upload>

              <el-button size="small" type="success" @click="readExcel">确认上传</el-button>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>
<script>
import MDinput from '@/components/MDinput'
import XLSX from 'xlsx'

const defaultForm = {
  taskId: '', // 任务编码
  troopNameZh: '', // 任务名称
  taskStatus: '0', // 任务状态 默认-草稿
  remark: '', // 说明备注
  execType: 'mysql', // 执行数据库-开发时默认mysql
  cycleType: 'O', // 周期类型-默认-一次性执行
  fileType: 'csv', // 下载文件类型,默认-csv
  fileSeparator: ',', // 文件分隔符,默认-逗号
  fileSplit: 'N', // 文件拆分方式,默认-不拆分
  zipType: 'zip', // 压缩方式,默认-zip
  dataSourcesSelect: [], // 选择的数据源
  createType: 'file'

}

export default {
  name: 'CreateUserTroop',
  components: { MDinput },

  filters: {

  }, data() {
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
      fileList: [],
      postForm: defaultForm,
      rules: {
        troopNameZh: [{ validator: validateRequire, message: '用户群名称为必填项!' }]
      },
      createTypeOptions: [
        { label: '文件导入', key: 'file' },
        { label: '自助取数', key: 'getdata' },
        { label: '文件接口', key: 'interface' },
        { label: '关联数据源', key: 'datasouce' }]
    }
  }, methods: {
    beforeUpload(file) {
      const fileType = file.name.substring(file.name.lastIndexOf('.'))
      if (fileType.toLowerCase() !== '.txt' && fileType.toLowerCase() !== '.csv' && fileType.toLowerCase() !== '.xls' && fileType.toLowerCase() !== '.xlsx') {
        this.$message.error('文件必须为.txt或.csv')
        this.fileList = []
        return false
      }
    },
    // 覆盖element的默认上传文件
    uploadHttpRequest(data) {
      const reader = new FileReader()
      const that = this
      reader.readAsText(data.file)
      reader.onload = function() {
        that.postForm.mmiapXml = this.result
      }
    },
    // 限制文件上传的个数只有一个，获取上传列表的最后一个
    handleUploadChange(file, fileList) {
      this.fileList = [fileList]
      console.log(this.fileList)

      // if (fileList.length > 0) {
      //   this.fileList = [fileList[fileList.length - 1]] // 这一步，是 展示最后一次选择的文件
      // }
    }, readExcel(e) {
      // 读取表格文件
      const that = this
      const files = this.fileList
      console.log(files)

      if (files.length <= 0) {
        return false
      } else {
        // 更新获取文件名
        that.upload_file = files[0].name
      }
      console.log(files)

      const fileReader = new FileReader()
      fileReader.onload = ev => {
        try {
          const data = ev.target.result
          const workbook = XLSX.read(data, {
            type: 'binary'
          })
          const wsname = workbook.SheetNames[0] // 取第一张表
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]) // 生成json表格内容
          that.lists = []
          // 从解析出来的数据中提取相应的数据
          ws.forEach(item => {
            that.lists.push({
              username: item['用户名'],
              phone_number: item['手机号']
            })
          })
          // 给后端发请求
          this.submit_form()
        } catch (e) {
          return false
        }
      }
      fileReader.readAsBinaryString(files[0])
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
  .el-list-enter-active, .el-list-leave-active {
    -webkit-transition: all 1s;
    -o-transition: all 1s;
    transition: none;
}

}

</style>
