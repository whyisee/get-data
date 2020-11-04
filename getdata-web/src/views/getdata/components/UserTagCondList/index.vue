<template>
  <div class="dndList">
    <el-row>
      <el-col :span="8">
        <div :style="{width:width1,height:height}" class="dndList-list">
          <h3>{{ list1Title }}</h3>

          <draggable :list="list1" :options="{group:{name: 'article',pull:'clone'}}" class="dragArea" animation="30" :clone="clone">
            <transition-group>

              <div v-for="(element,index) in list1" :key="element.tagId+'list1'+index" :class="element.tagId==1?'item forbid':'item'">
                <div class="list-complete-item-handle2">
                  <span :class="isDev%2 == 0 ? 'display-none' :'submit-view' ">  {{ element.tagName +'|' }} </span>

                  {{ element.tagNameZh }}

                </div>
              </div>
            </transition-group>

          </draggable>

        </div>
      </el-col>

      <el-col :span="16">
        <div :style="{width:width3,height:height}" class="dndList-list">
          <h3>{{ list3Title }}</h3>
          <draggable :set-data="setData" :list="list3" group="article" class="dragArea" animation="30" @end="end">
            <div v-for="(element,index) in list3" :key="element.tagId+'list3'+index" :class="element.tagId==1?'item forbid':'item'">
              <el-select v-model="element.unionType" :placeholder="$t('getdata.unionType')" clearable class="filter-item" style="width: 100px">
                <el-option v-for="item in unionTypeOptions" :key="item.key" :label="item.label" :value="item.key" />
              </el-select>

              <el-input v-model="element.leftBracket" :placeholder="$t('getdata.leftBracket')" style="width: 60px;" class="filter-item" />

              {{ element.tagNameZh }}
              <el-select v-model="element.operateType" :placeholder="$t('getdata.operateType')" clearable class="filter-item" style="width: 100px">
                <el-option v-for="item in operateTypeOptions" :key="item" :label="item" :value="item" />
              </el-select>
              <el-input v-model="element.value" :placeholder="$t('getdata.conditionValues')" style="width: 100px;" class="filter-item" />
              <el-input v-model="element.rightBracket" :placeholder="$t('getdata.rightBracket')" style="width: 60px;" class="filter-item" />

              <span style="float: right ;margin-top: 5px;margin-right:5px;" @click="deleteEle(element,list3)">
                <i style="color:#ff4949" class="el-icon-delete" />
              </span>
            </div>
          </draggable>
        </div>
      </el-col>
    </el-row>
    <el-row>

      <el-col :span="8">
        <div :style="{width:width2,height:height}" class="dndList-list">
          <h3>{{ list2Title }}</h3>
          <draggable :list="list2" :options="{group:{name: 'article',pull:'clone'}}" class="dragArea" animation="30" :clone="clone">
            <transition-group>

              <div v-for="(element,index) in list2" :key="element.tagId+'list2'+index" :class="element.tagId==1?'item forbid':'item'">
                <div class="list-complete-item-handle2">
                  <span :class="isDev%2 == 0 ? 'display-none' :'submit-view' ">  {{ element.tagName +'|' }} </span>

                  {{ element.tagNameZh }}

                </div>
              </div>
            </transition-group>

          </draggable>
        </div>
      </el-col>

      <el-col :span="16">
        <div :style="{width:width4,height:height}" class="dndList-list">
          <h3>{{ list4Title }}</h3>
          <draggable :set-data="setData" :list="list4" group="article" class="dragArea" :scroll="true" animation="30" :clone="deepClone">
            <div v-for="(element,index) in list4" :key="element.id+'list4'+index" :class="element.id==1?'item forbid':'item'">
              <el-select v-model="element.unionType" :placeholder="$t('getdata.unionType')" clearable class="filter-item" style="width: 100px">
                <el-option v-for="item in unionTypeOptions" :key="item.key" :label="item.label" :value="item.key" />
              </el-select>
              <el-input v-model="element.leftBracket" :placeholder="$t('getdata.leftBracket')" style="width: 60px;" class="filter-item" />

              {{ element.tagNameZh }}
              <el-select v-model="element.operateType" :placeholder="$t('getdata.operateType')" clearable class="filter-item" style="width: 100px">
                <el-option v-for="item in operateTypeOptions" :key="item" :label="item" :value="item" />
              </el-select>
              <el-input v-model="element.value" :placeholder="$t('getdata.conditionValues')" style="width: 100px;" class="filter-item" />
              <el-input v-model="element.rightBracket" :placeholder="$t('getdata.rightBracket')" style="width: 60px;" class="filter-item" />

              <span style="float: right ;margin-top: 0px;margin-right:5px;" @click="deleteEle(element,list4)">
                <i style="color:#ff4949" class="el-icon-delete" />
              </span>
            </div>
          </draggable>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import draggable from 'vuedraggable'

export default {
  name: 'UserTagCondList',
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
      conditionValues: '',
      isDev: 0

    }
  },
  methods: {
    isNotInList1(v) {
      return this.list1.every(k => v.id !== k.id)
    },
    isNotInList2(v) {
      return this.list2.every(k => v.id !== k.id)
    }, // 非递归深拷贝
    clone(obj) {
      console.log(obj)
      // 增加默认值
      obj.unionType = 'AND'
      obj.operateType = '='
      if (typeof obj !== 'object' || obj === null) return obj
      var newObj = obj.constructor()
      var currentObj = [obj]; var copyObj = [newObj]
      var visitedCurrent = []; var visitedCopy = []
      while (currentObj.length > 0) {
        const currentElement = currentObj.shift()
        const copyElement = copyObj.shift()
        // 将对象放入已访问对象数组中
        visitedCurrent.push(currentElement)
        visitedCopy.push(copyElement)
        for (const key in currentElement) {
          // 非对象直接赋值
          if (currentElement[key] === null || typeof currentElement[key] !== 'object') {
            copyElement[key] = currentElement[key]
          } else {
            // 判断是否有环，以防死循环
            if (visitedCurrent.findIndex(item => item === currentElement[key]) >= 0) {
              copyElement[key] = currentElement[key]
            } else {
              // 将对象放入待复制目标对象数组中
              currentObj.push(currentElement[key])
              // 创建空的对象
              copyElement[key] = currentElement[key].constructor()
              // 将空对象放入待填充的目标数组中
              copyObj.push(copyElement[key])
            }
          }
        }
      }
      return newObj
    },

    deepClone(data, hash = new WeakMap()) {
      // nb
      // console.log(data)
      if (typeof data !== 'object' || data === null) {
        throw new TypeError('传入参数不是对象')
      }
      // 判断传入的待拷贝对象的引用是否存在于hash中
      if (hash.has(data)) {
        return hash.get(data)
      }
      const newData = {}
      const dataKeys = Object.keys(data)
      dataKeys.forEach(value => {
        const currentDataValue = data[value]
        // 基本数据类型的值和函数直接赋值拷贝
        if (typeof currentDataValue !== 'object' || currentDataValue === null) {
          newData[value] = currentDataValue
        } else if (Array.isArray(currentDataValue)) {
          // 实现数组的深拷贝
          newData[value] = [...currentDataValue]
        } else if (currentDataValue instanceof Set) {
          // 实现set数据的深拷贝
          newData[value] = new Set([...currentDataValue])
        } else if (currentDataValue instanceof Map) {
          // 实现map数据的深拷贝
          newData[value] = new Map([...currentDataValue])
        } else {
          // 将这个待拷贝对象的引用存于hash中
          hash.set(data, data)
          // 普通对象则递归赋值
          newData[value] = this.deepClone(currentDataValue, hash)
        }
      })
      return newData
    }, end() {
      console.log(321)
    },
    deleteEle(ele, list) {
      const index = list.findIndex(i => i === ele)
      list.splice(index, 1)
    },
    pushEle(ele) {
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
