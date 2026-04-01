<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="原料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入原料名称"
          clearable
          size="small"
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作类型" prop="operationType">
        <el-select v-model="queryParams.operationType" placeholder="请选择操作类型" clearable size="small" style="width: 120px">
          <el-option label="进货" value="1" />
          <el-option label="盘点" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="handleAddStock"
          v-hasPermi="['system:inventory:operation:add']"
        >进货录入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="small"
          @click="handleStockTake"
          v-hasPermi="['system:inventory:operation:stocktake']"
        >盘点录入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:inventory:operation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" width="100" align="center" prop="recordId" />
      <el-table-column label="原料名称" align="center" prop="materialName" />
      <el-table-column label="操作类型" align="center" prop="operationType">
        <template slot-scope="scope">
          <el-tag :type="scope.row.operationType === '1' ? 'success' : 'warning'">
            {{ scope.row.operationType === '1' ? '进货' : '盘点' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作数量" align="center" prop="quantity" />
      <el-table-column label="操作前库存" align="center" prop="beforeStock" />
      <el-table-column label="操作后库存" align="center" prop="afterStock" />
      <el-table-column label="操作人" align="center" prop="operator" />
      <el-table-column label="操作时间" align="center" prop="operationTime" width="180">
        <template slot-scope="scope">
          {{ parseTime(scope.row.operationTime) }}
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 进货录入对话框 -->
    <el-dialog :title="stockTitle" :visible.sync="stockOpen" width="500px">
      <el-form ref="stockForm" :model="stockForm" :rules="stockRules" label-width="80px">
        <el-form-item label="选择原料" prop="materialId">
          <el-select v-model="stockForm.materialId" placeholder="请选择原料" style="width: 100%">
            <el-option
              v-for="material in materialList"
              :key="material.materialId"
              :label="material.materialName"
              :value="material.materialId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="操作数量" prop="quantity">
          <el-input-number v-model="stockForm.quantity" :min="0.01" :step="0.01" placeholder="请输入数量" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="stockForm.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStockForm">确定</el-button>
        <el-button @click="stockCancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInventoryRecord, addStock, stockTake, listMaterial } from '@/api/system/inventory'

export default {
  name: 'Operation',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库存操作记录表格数据
      recordList: [],
      // 原料列表
      materialList: [],
      // 选中数组
      multipleSelection: [],
      // 弹出层标题
      stockTitle: '',
      // 是否显示弹出层
      stockOpen: false,
      // 操作类型：1进货 2盘点
      operationType: 1,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialName: '',
        operationType: ''
      },
      // 表单参数
      stockForm: {
        materialId: '',
        quantity: 0,
        remark: ''
      },
      // 表单校验
      stockRules: {
        materialId: [
          { required: true, message: '请选择原料', trigger: 'blur' }
        ],
        quantity: [
          { required: true, message: '请输入操作数量', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getMaterialList()
  },
  methods: {
    /** 查询库存操作记录列表 */
    getList() {
      this.loading = true
      listInventoryRecord(this.queryParams).then(response => {
        this.recordList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询原料列表 */
    getMaterialList() {
      listMaterial({ pageSize: 1000 }).then(response => {
        this.materialList = response.rows
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },
    /** 进货录入按钮操作 */
    handleAddStock() {
      this.resetForm('stockForm')
      this.stockOpen = true
      this.stockTitle = '原料进货'
      this.operationType = 1
    },
    /** 盘点录入按钮操作 */
    handleStockTake() {
      this.resetForm('stockForm')
      this.stockOpen = true
      this.stockTitle = '库存盘点'
      this.operationType = 2
    },
    /** 提交按钮 */
    submitStockForm() {
      this.$refs['stockForm'].validate(valid => {
        if (valid) {
          if (this.operationType === 1) {
            addStock(this.stockForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess('进货成功')
                this.stockOpen = false
                this.getList()
              }
            })
          } else {
            stockTake(this.stockForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess('盘点成功')
                this.stockOpen = false
                this.getList()
              }
            })
          }
        }
      })
    },
    /** 取消按钮 */
    stockCancel() {
      this.stockOpen = false
      this.resetForm('stockForm')
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/inventory/operation/export', {
        ...this.queryParams
      }, `inventory_record_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>