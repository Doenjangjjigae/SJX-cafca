<template>
  <div class="app-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>AI模型配置</span>
          <el-button type="primary" size="small" @click="handleAdd" icon="el-icon-plus">添加模型</el-button>
        </div>
      </template>
      
      <el-table :data="modelList" style="width: 100%">
        <el-table-column prop="modelId" label="模型ID" width="80" />
        <el-table-column prop="modelName" label="模型名称" />
        <el-table-column prop="modelCode" label="模型编码" />
        <el-table-column prop="apiUrl" label="API地址" />
        <el-table-column prop="isEnabled" label="状态" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.isEnabled"
              :active-value="1"
              :inactive-value="0"
              @change="handleEnable(scope.row)"
              :disabled="true"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)" type="primary" icon="el-icon-edit">编辑</el-button>
            <el-button size="mini" @click="handleDelete(scope.row)" type="danger" icon="el-icon-delete">删除</el-button>
            <el-button 
              v-if="scope.row.isEnabled !== 1" 
              size="mini" 
              @click="handleEnableModel(scope.row.modelId)" 
              type="success" 
              icon="el-icon-check">
              启用
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="500px"
    >
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="模型名称" prop="modelName">
          <el-input v-model="form.modelName" placeholder="请输入模型名称" />
        </el-form-item>
        <el-form-item label="模型编码" prop="modelCode">
          <el-input v-model="form.modelCode" placeholder="请输入模型编码" />
        </el-form-item>
        <el-form-item label="API密钥" prop="apiKey">
          <el-input v-model="form.apiKey" placeholder="请输入API密钥" type="password" />
        </el-form-item>
        <el-form-item label="API地址" prop="apiUrl">
          <el-input v-model="form.apiUrl" placeholder="请输入API地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAIModelList, getAIModelInfo, addAIModel, updateAIModel, deleteAIModel, enableAIModel } from '@/api/system/aiModel'

export default {
  name: 'AIModel',
  data() {
    return {
      modelList: [],
      dialogVisible: false,
      dialogTitle: '',
      form: {
        modelId: null,
        modelName: '',
        modelCode: '',
        apiKey: '',
        apiUrl: '',
        isEnabled: 0
      },
      rules: {
        modelName: [{ required: true, message: '请输入模型名称', trigger: 'blur' }],
        modelCode: [{ required: true, message: '请输入模型编码', trigger: 'blur' }],
        apiKey: [{ required: true, message: '请输入API密钥', trigger: 'blur' }],
        apiUrl: [{ required: true, message: '请输入API地址', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.loadModelList()
  },
  methods: {
    loadModelList() {
      getAIModelList().then(response => {
        this.modelList = response.data
      })
    },
    handleAdd() {
      this.dialogTitle = '添加AI模型'
      this.form = {
        modelId: null,
        modelName: '',
        modelCode: '',
        apiKey: '',
        apiUrl: '',
        isEnabled: 0
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑AI模型'
      getAIModelInfo(row.modelId).then(response => {
        this.form = response.data
        this.dialogVisible = true
      })
    },
    handleDelete(row) {
      this.$confirm('确定要删除该模型吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAIModel(row.modelId).then(response => {
          if (response.code === 200) {
            this.$message.success('删除成功')
            this.loadModelList()
          } else {
            this.$message.error('删除失败')
          }
        })
      })
    },
    handleEnableModel(modelId) {
      this.$confirm('确定要启用该模型吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableAIModel(modelId).then(response => {
          if (response.code === 200) {
            this.$message.success('启用成功')
            this.loadModelList()
          } else {
            this.$message.error('启用失败')
          }
        })
      })
    },
    handleEnable(row) {
      // 状态切换已通过handleEnableModel处理
    },
    handleSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.modelId) {
            updateAIModel(this.form).then(response => {
              if (response.code === 200) {
                this.$message.success('修改成功')
                this.dialogVisible = false
                this.loadModelList()
              } else {
                this.$message.error('修改失败')
              }
            })
          } else {
            addAIModel(this.form).then(response => {
              if (response.code === 200) {
                this.$message.success('添加成功')
                this.dialogVisible = false
                this.loadModelList()
              } else {
                this.$message.error('添加失败')
              }
            })
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>