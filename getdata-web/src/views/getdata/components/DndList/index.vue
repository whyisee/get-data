<template>
  <div class="dndList">
    <el-row>
      <el-col :span="8">
        <div :style="{width:width1,height:height}" class="dndList-list">
          <h3>{{ list1Title }}</h3>
          <div class="">
            <div class="col">

              <draggable :list="list1" :options="{group:{name: 'article',pull:'clone'}}" class="dragArea" animation="30">
                <transition-group>

                  <div v-for="(element,index) in list1" :key="element.id+'list1'+index" :class="element.id==1?'item forbid':'item'">
                    <div class="list-complete-item-handle2">
                      {{ element.id }} {{ element.name }} {{ element.title }}
                    </div>
                  </div>
                </transition-group>

              </draggable>

            </div>
          </div>
        </div>
      </el-col>

      <el-col :span="16">
        <div :style="{width:width3,height:height}" class="dndList-list">
          <h3>{{ list3Title }}</h3>
          <div class="">
            <div class="col">
              <draggable :set-data="setData" :list="list3" group="article" class="dragArea" animation="30">

                <div v-for="(element,index) in list3" :key="element.id+'list3'+index" :class="element.id==1?'item forbid':'item'">
                  <el-select v-model="element.unionType" :placeholder="$t('getdata.unionType')" clearable class="filter-item" style="width: 100px">
                    <el-option v-for="item in unionTypeOptions" :key="item.key" :label="item.label" :value="item.key" />
                  </el-select>
                  {{ element.name }}
                  <el-select v-model="element.operateType" :placeholder="$t('getdata.operateType')" clearable class="filter-item" style="width: 100px">
                    <el-option v-for="item in operateTypeOptions" :key="item" :label="item" :value="item" />
                  </el-select>
                  {{ element.title }}
                  <el-input v-model="element.value" :placeholder="$t('getdata.conditionValues')" style="width: 100px;" class="filter-item" />
                  <span style="float: right ;margin-top: 0px;margin-right:5px;" @click="deleteEle(element,list3)">
                    <i style="color:#ff4949" class="el-icon-delete" />
                  </span>
                </div>

              </draggable>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>

      <el-col :span="8">
        <div :style="{width:width2,height:height}" class="dndList-list">
          <h3>{{ list2Title }}</h3>
          <div class="itxst">
            <div class="col">
              <draggable :list="list2" :options="{group:{name: 'article',pull:'clone'}}" class="dragArea" :scroll="true" animation="30">
                <transition-group>
                  <div v-for="(element,index) in list2" :key="element.id+'list2'+index" :class="element.id==1?'item forbid':'item'">
                    <div class="list-complete-item-handle2">
                      {{ element.id }} {{ element.name }} {{ element.title }}
                    </div>
                  </div>
                </transition-group>
              </draggable>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :span="16">
        <div :style="{width:width4,height:height}" class="dndList-list">
          <h3>{{ list4Title }}</h3>
          <div class="itxst">
            <div class="col">
              <draggable :set-data="setData" :list="list4" group="article" class="dragArea" :scroll="true" animation="30">
                <div v-for="(element,index) in list4" :key="element.id+'list4'+index" :class="element.id==1?'item forbid':'item'">
                  <el-select v-model="element.unionType" :placeholder="$t('getdata.unionType')" clearable class="filter-item" style="width: 100px">
                    <el-option v-for="item in unionTypeOptions" :key="item.key" :label="item.label" :value="item.key" />
                  </el-select>
                  {{ element.name }}
                  <el-select v-model="element.operateType" :placeholder="$t('getdata.operateType')" clearable class="filter-item" style="width: 100px">
                    <el-option v-for="item in operateTypeOptions" :key="item" :label="item" :value="item" />
                  </el-select>
                  {{ element.title }}
                  <el-input v-model="element.value" :placeholder="$t('getdata.conditionValues')" style="width: 100px;" class="filter-item" />
                  <span style="float: right ;margin-top: 0px;margin-right:5px;" @click="deleteEle(element,list4)">
                    <i style="color:#ff4949" class="el-icon-delete" />
                  </span>
                </div>
              </draggable>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import draggable from 'vuedraggable'

export default {
  name: 'DndList',
  components: { draggable },
  props: {
    list1: {
      type: Array,
      default() {
        return []
      }
    },
    list2: {
      type: Array,
      default() {
        return []
      }
    },
    list3: {
      type: Array,
      default() {
        return []
      }
    },
    list4: {
      type: Array,
      default() {
        return []
      }
    },
    list1Title: {
      type: String,
      default: 'list1'
    },
    list2Title: {
      type: String,
      default: 'list2'
    },
    list3Title: {
      type: String,
      default: 'list2'
    },
    list4Title: {
      type: String,
      default: 'list2'
    },
    width1: {
      type: String,
      default: '90%'
    },
    width2: {
      type: String,
      default: '90%'
    },
    width3: {
      type: String,
      default: '100%'
    },
    width4: {
      type: String,
      default: '100%'
    }, height: {
      type: String,
      default: '48%'
    }
  },
  data() {
    return {
      operateTypeOptions: ['=', '>', '<', '>=', '<=', 'in', 'like', 'not in', 'not like', 'is'],
      unionTypeOptions: [{ label: '并且', key: 'AND' }, { label: '或者', key: 'OR' }],
      operateType: '',
      conditionValues: ''

    }
  },
  methods: {
    isNotInList1(v) {
      return this.list1.every(k => v.id !== k.id)
    },
    isNotInList2(v) {
      return this.list2.every(k => v.id !== k.id)
    },
    deleteEle(ele, list) {
      console.log(this)
      for (const item of list) {
        if (item.id === ele.id) {
          const index = list.indexOf(item)
          list.splice(index, 1)
          break
        }
      }
      // if (this.isNotInList2(ele)) {
      //   this.list2.unshift(ele)
      // }
    },
    pushEle(ele) {
      console.log(123)
      for (const item of this.list2) {
        if (item.id === ele.id) {
          const index = this.list2.indexOf(item)
          this.list2.splice(index, 1)
          break
        }
      }
      if (this.isNotInList1(ele)) {
        this.list1.push(ele)
      }
    },
    setData(dataTransfer) {
      console.log(1233)
      // to avoid Firefox bug
      // Detail see : https://github.com/RubaXa/Sortable/issues/1012
      dataTransfer.setData('Text', '')
    }

  }
}
</script>

<style lang="scss" scoped>
.dndList {
  background: #fff;
  padding-bottom: 40px;
  &:after {
    content: "";
    display: table;
    clear: both;
  }
  .dndList-list {
    float: left;
    padding-bottom: 0;
    &:first-of-type {
      margin-right: 2%;
    }
    .dragArea {
      margin-top: 0;
      min-height: 50px;
      max-height: 300px;
      padding-bottom: 0;
      overflow: auto
    }
  }
}

.list-complete-item {
  cursor: pointer;
  position: relative;
  font-size: 14px;
  padding: 5px 12px;
  margin-top: 4px;
  border: 1px solid #bfcbd9;
  transition: all 1s;
}

.list-complete-item-handle {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-right: 50px;
}

.list-complete-item-handle2 {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-right: 20px;
}

.list-complete-item.sortable-chosen {
  background: #4AB7BD;
}

.list-complete-item.sortable-ghost {
  background: #30B08F;
}

.list-complete-enter,
.list-complete-leave-active {
  opacity: 0;
}

//

      .itxst {
          // width: 60%;
           margin: 10px;
           text-align :left;
           height: 200px;
           overflow: auto;
      }
      .col {
          width: 100%;
          flex: 1;
          padding: 5px;
          border: solid 1px #eee;
          border-radius: 5px;
          float: left;
      }
      .col + .col {
          margin-left: 10px;
      }
      .item {
          padding: 5px 12px;
          margin: 0px 10px 0px 10px;
          border: solid 1px #eee;
          background-color: #f1f1f1;
          text-align: left;
      }
      .item + .item {
          border-top: none;
          margin-top: 6px;
      }
      .item:hover {
          background-color: #fdfdfd;
          // cursor: move;
      }

.flip-list-move {
  transition: transform 0.5s;
}
</style>
