<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-warning"
          size="small"
          @click="handleSetThreshold"
          v-hasPermi="['system:inventory:warning:set']"
          :disabled="multipleSelection.length === 0"
        >设置阈值</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warningList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="原料ID" width="100" align="center" prop="materialId" />
      <el-table-column label="原料名称" align="center" prop="materialName" />
      <el-table-column label="原料规格" align="center" prop="specification" />
      <el-table-column label="原料单位" align="center" prop="unit" />
      <el-table-column label="当前库存" align="center" prop="currentStock" />
      <el-table-column label="预警阈值" align="center" prop="warningThreshold" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
            {{ scope.row.status === '0' ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 设置预警阈值对话框 -->
    <el-dialog :title="'设置预警阈值'" :visible.sync="thresholdOpen" width="400px">
      <el-form ref="thresholdForm" :model="thresholdForm" :rules="thresholdRules" label-width="100px">
        <el-form-item label="预警阈值" prop="warningThreshold">
          <el-input-number v-model="thresholdForm.warningThreshold" :min="0" step="0.01" placeholder="请输入预警阈值" style="width: 100%" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitThresholdForm">确定</el-button>
        <el-button @click="thresholdCancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLowStockMaterials, setWarningThreshold } from '@/api/system/inventory'

export default {
  name: 'Warning',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 低库存原料列表
      warningList: [],
      // 选中数组
      multipleSelection: [],
      // 是否显示阈值设置对话框
      thresholdOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 阈值设置表单
      thresholdForm: {
        warningThreshold: 0
      },
      // 表单校验
      thresholdRules: {
        warningThreshold: [
          { required: true, message: '请输入预警阈值', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询低库存原料列表 */
    getList() {
      this.loading = true
      listLowStockMaterials().then(response => {
        this.warningList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },
    /** 设置阈值按钮操作 */
    handleSetThreshold() {
      this.resetForm('thresholdForm')
      this.thresholdOpen = true
    },
    /** 提交阈值设置 */
    submitThresholdForm() {
      this.$refs['thresholdForm'].validate(valid => {
        if (valid) {
          const materialIds = this.multipleSelection.map(item => item.materialId)
          const promises = materialIds.map(materialId => {
            return setWarningThreshold(materialId, this.thresholdForm.warningThreshold)
          })
          Promise.all(promises).then(() => {
            this.$modal.msgSuccess('阈值设置成功')
            this.thresholdOpen = false
            this.getList()
          })
        }
      })
    },
    /** 取消按钮 */
    thresholdCancel() {
      this.thresholdOpen = false
      this.resetForm('thresholdForm')
    }
  }
}
</script>