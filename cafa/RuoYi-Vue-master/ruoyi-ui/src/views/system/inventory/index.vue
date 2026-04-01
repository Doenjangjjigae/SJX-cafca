<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane label="原料信息" name="material">
        <material-component />
      </el-tab-pane>
      <el-tab-pane label="库存操作" name="operation">
        <operation-component />
      </el-tab-pane>
      <el-tab-pane label="库存预警" name="warning">
        <warning-component />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import MaterialComponent from './material/index.vue'
import OperationComponent from './operation/index.vue'
import WarningComponent from './warning/index.vue'

export default {
  name: 'Inventory',
  components: {
    MaterialComponent,
    OperationComponent,
    WarningComponent
  },
  data() {
    return {
      activeTab: 'material'
    }
  }
}
</script>