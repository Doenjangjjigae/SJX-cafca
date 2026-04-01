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
      <el-form-item label="原料规格" prop="specification">
        <el-input
          v-model="queryParams.specification"
          placeholder="请输入原料规格"
          clearable
          size="small"
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="原料单位" prop="unit">
        <el-select
          v-model="queryParams.unit"
          placeholder="请选择原料单位"
          clearable
          size="small"
          style="width: 120px"
          @change="handleQuery"
        >
          <el-option v-for="dict in dict.type.mate_unit" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          @click="handleAdd"
          v-hasPermi="['system:inventory:material:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="small"
          @click="handleDelete"
          v-hasPermi="['system:inventory:material:remove']"
          :disabled="multipleSelection.length === 0"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:inventory:material:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
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
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
            v-hasPermi="['system:inventory:material:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row.materialId)"
            v-hasPermi="['system:inventory:material:remove']"
          >删除</el-button>
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

    <!-- 添加或修改原料信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原料名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入原料名称" />
        </el-form-item>
        <el-form-item label="原料规格" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入原料规格" />
        </el-form-item>
        <el-form-item label="原料单位" prop="unit">
          <el-select v-model="form.unit" placeholder="请选择原料单位">
            <el-option v-for="dict in dict.type.mate_unit" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="预警阈值" prop="warningThreshold">
          <el-input-number v-model="form.warningThreshold" :min="0" :step="0.01" placeholder="请输入预警阈值" />
        </el-form-item>
        <el-form-item label="当前库存" prop="currentStock">
          <el-input-number v-model="form.currentStock" :min="0" :step="0.01" placeholder="请输入当前库存" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaterial, getMaterial, addMaterial, updateMaterial, delMaterial } from '@/api/system/inventory'

export default {
  name: 'Material',
  dicts: ['mate_unit'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 原料信息表格数据
      materialList: [],
      // 选中数组
      multipleSelection: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialName: '',
        specification: '',
        unit: ''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialName: [
          { required: true, message: '原料名称不能为空', trigger: 'blur' }
        ],
        unit: [
          { required: true, message: '原料单位不能为空', trigger: 'blur' }
        ],
        currentStock: [
          { required: true, message: '当前库存不能为空', trigger: 'blur' },
          { type: 'number', min: 0, message: '当前库存必须大于等于0', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询原料信息列表 */
    getList() {
      this.loading = true
      listMaterial(this.queryParams).then(response => {
        this.materialList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(error => {
        this.msgError(error.msg || '获取数据失败，请稍后重试')
        this.loading = false
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
    /** 新增按钮操作 */
    handleAdd() {
      this.form = {
        materialName: '',
        specification: '',
        unit: '',
        warningThreshold: 0,
        currentStock: 0,
        status: '0',
        remark: ''
      }
      this.open = true
      this.title = '新增原料信息'
    },
    /** 修改按钮操作 */
    handleEdit(row) {
      this.resetForm('form')
      getMaterial(row.materialId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改原料信息'
      }).catch(error => {
        this.msgError(error.msg || '获取数据失败，请稍后重试')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.materialId != null) {
            updateMaterial(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess('修改成功')
                this.open = false
                this.getList()
              } else {
                this.msgError(response.msg || '修改失败')
              }
            }).catch(error => {
              this.msgError(error.msg || '网络错误，请稍后重试')
            })
          } else {
            addMaterial(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess('新增成功')
                this.open = false
                this.getList()
              } else {
                this.msgError(response.msg || '新增失败')
              }
            }).catch(error => {
              this.msgError(error.msg || '网络错误，请稍后重试')
            })
          }
        }
      })
    },
    /** 取消按钮 */
    cancel() {
      this.open = false
      this.resetForm('form')
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const materialIds = row.materialId || this.multipleSelection.map(item => item.materialId)
      this.$confirm(`是否确认删除原料信息?`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return delMaterial(materialIds)
      }).then(response => {
        if (response.code === 200) {
          this.msgSuccess('删除成功')
          this.getList()
        } else {
          this.msgError(response.msg || '删除失败')
        }
      }).catch(error => {
        if (error !== 'cancel') {
          this.msgError(error.msg || '网络错误，请稍后重试')
        }
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/inventory/material/export', {
        ...this.queryParams
      }, `material_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>