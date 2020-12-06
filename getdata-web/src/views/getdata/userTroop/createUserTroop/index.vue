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
            </el-form-itemlabel="执行周期:"></el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>
<script>
import MDinput from '@/components/MDinput'

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
  dataSourcesSelect: [] // 选择的数据源

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

</style>
