<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品编码" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入商品编码"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入分类"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option label="正常" value="0" />
          <el-option label="下架" value="1" />
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
          plain
          icon="el-icon-plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['system:product:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="small"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:product:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="small"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:product:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品ID" align="center" prop="productId" />
      <el-table-column label="商品图片" align="center" width="100">
        <template slot-scope="scope">
          <img v-if="scope.row.imageUrl" :src="getImageUrl(scope.row.imageUrl)" class="list-avatar" />
          <i v-else class="el-icon-picture-outline list-avatar-placeholder"></i>
        </template>
      </el-table-column>
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="商品编码" align="center" prop="productCode" />
      <el-table-column label="单价" align="center" prop="price" />
      <el-table-column label="分类" align="center" prop="category" />
      <el-table-column label="配置状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="hasConfig(scope.row)" size="small" type="success">已配置</el-tag>
          <el-tag v-else size="small" type="info">未配置</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" size="small">正常</el-tag>
          <el-tag type="danger" v-else-if="scope.row.status === '1'" size="small">下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:product:remove']"
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

    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入商品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品编码" prop="productCode">
              <el-input v-model="form.productCode" placeholder="请输入商品编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单价" prop="price">
              <el-input v-model="form.price" placeholder="请输入单价" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类" prop="category">
              <el-input v-model="form.category" placeholder="请输入分类" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="正常" value="0" />
                <el-option label="下架" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="商品图片">
              <el-upload
                class="avatar-uploader"
                :action="'/dev-api/common/upload'"
                :show-file-list="false"
                :on-success="handleImageUpload"
                :before-upload="beforeImageUpload"
                :headers="{ Authorization: 'Bearer ' + token }"
                accept="image/*"
              >
                <img v-if="form.imageUrl" :src="getImageUrl(form.imageUrl)" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">默认原料配置</el-divider>

        <el-button type="primary" size="small" icon="el-icon-plus" @click="addDefaultMaterial" style="margin-bottom: 15px;">
          添加默认原料
        </el-button>

        <el-table :data="form.defaultMaterials" border style="width: 100%" v-if="form.defaultMaterials && form.defaultMaterials.length > 0">
          <el-table-column label="原料" width="200">
            <template slot-scope="scope">
              <el-select v-model="scope.row.materialId" placeholder="选择原料" @change="onDefaultMaterialChange(scope.row)">
                <el-option
                  v-for="material in materialList"
                  :key="material.materialId"
                  :label="material.materialName + '(' + material.unit + ')'"
                  :value="material.materialId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="用量" width="150">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.quantity" :min="0" :precision="2" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template slot-scope="scope">
              <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="removeDefaultMaterial(scope.$index)" />
            </template>
          </el-table-column>
        </el-table>
        <el-empty v-else description="暂无默认原料配置" :image-size="60" />

        <el-divider content-position="left">配置选项（温度、冰量、甜度、加料等）</el-divider>

        <el-button type="primary" size="small" icon="el-icon-plus" @click="addConfigGroup" style="margin-bottom: 15px;">
          添加配置组
        </el-button>

        <div v-if="form.configGroups && form.configGroups.length > 0">
          <el-card v-for="(group, groupIndex) in form.configGroups" :key="groupIndex" style="margin-bottom: 15px;">
            <div slot="header" class="card-header">
              <span>{{ group.groupName || '配置组' + (groupIndex + 1) }}</span>
              <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="removeConfigGroup(groupIndex)" />
            </div>
            <el-form label-width="80px">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="配置名称">
                    <el-input v-model="group.groupName" placeholder="如：温度、冰量" size="small" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="配置类型">
                    <el-select v-model="group.groupType" placeholder="选择类型" size="small">
                      <el-option label="温度" value="temperature" />
                      <el-option label="冰量" value="ice" />
                      <el-option label="甜度" value="sweetness" />
                      <el-option label="加料" value="topping" />
                      <el-option label="其他" value="other" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="必选">
                    <el-switch v-model="group.isRequired" active-value="1" inactive-value="0" />
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="可多选">
                    <el-switch v-model="group.isMultiple" active-value="1" inactive-value="0" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-divider content-position="left">选项配置</el-divider>

              <el-button type="success" size="mini" icon="el-icon-plus" @click="addConfigOption(group)" style="margin-bottom: 10px;">
                添加选项
              </el-button>

              <el-table :data="group.options" border size="small" v-if="group.options && group.options.length > 0">
                <el-table-column label="选项名称" width="120">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.optionName" placeholder="如：热、冷" size="small" />
                  </template>
                </el-table-column>
                <el-table-column label="选项值" width="100">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.optionValue" placeholder="如：hot" size="small" />
                  </template>
                </el-table-column>
                <el-table-column label="额外价格" width="100">
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.extraPrice" :min="0" :precision="2" size="small" />
                  </template>
                </el-table-column>
                <el-table-column label="默认" width="60">
                  <template slot-scope="scope">
                    <el-switch v-model="scope.row.isDefault" active-value="1" inactive-value="0" />
                  </template>
                </el-table-column>
                <el-table-column label="关联原料">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="configOptionMaterials(scope.row, scope.$index, groupIndex)">
                      配置原料({{ scope.row.materials ? scope.row.materials.length : 0 }})
                    </el-button>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="60">
                  <template slot-scope="scope">
                    <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="removeConfigOption(group, scope.$index)" />
                  </template>
                </el-table-column>
              </el-table>
              <el-empty v-else description="暂无选项" :image-size="40" />
            </el-form>
          </el-card>
        </div>
        <el-empty v-else description="暂无配置选项" :image-size="60" />
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="配置选项原料" :visible.sync="materialDialogVisible" width="600px" append-to-body>
      <div v-if="currentOption">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addOptionMaterial" style="margin-bottom: 15px;">
          添加原料
        </el-button>
        <el-table :data="currentOption.materials" border>
          <el-table-column label="原料" width="200">
            <template slot-scope="scope">
              <el-select v-model="scope.row.materialId" placeholder="选择原料" @change="onOptionMaterialChange(scope.row)">
                <el-option
                  v-for="material in materialList"
                  :key="material.materialId"
                  :label="material.materialName + '(' + material.unit + ')'"
                  :value="material.materialId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="用量">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.quantity" :min="0" :precision="2" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template slot-scope="scope">
              <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="removeOptionMaterial(scope.$index)" />
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmOptionMaterials">确 定</el-button>
        <el-button @click="materialDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProduct, getProduct, addProduct, updateProduct, delProduct, delProducts, listMaterial } from '@/api/system/product'
import Pagination from '@/components/Pagination'
import RightToolbar from '@/components/RightToolbar'
import { getToken } from '@/utils/auth'

export default {
  name: 'Product',
  components: {
    Pagination,
    RightToolbar
  },
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      productList: [],
      title: '',
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: '',
        productCode: '',
        category: '',
        status: ''
      },
      form: {},
      rules: {
        productName: [
          { required: true, message: '商品名称不能为空', trigger: 'blur' }
        ],
        productCode: [
          { required: true, message: '商品编码不能为空', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '单价不能为空', trigger: 'blur' }
        ]
      },
      materialList: [],
      materialDialogVisible: false,
    currentOption: null,
    currentOptionIndex: -1,
    currentGroupIndex: -1,
    token: ''
    }
  },
  created() {
    this.token = getToken()
    this.getList()
    this.loadMaterialList()
  },
  methods: {
    getImageUrl(url) {
      if (!url) return ''
      if (url.startsWith('http://') || url.startsWith('https://')) {
        return url
      }
      return process.env.VUE_APP_BASE_API + url
    },
    hasConfig(row) {
      return (row.materials && row.materials.length > 0) || 
             (row.configGroups && row.configGroups.length > 0) ||
             (row.defaultMaterials && row.defaultMaterials.length > 0)
    },
    getList() {
      this.loading = true
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    loadMaterialList() {
      listMaterial({}).then(response => {
        this.materialList = response.rows || []
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        defaultMaterials: [],
        configGroups: []
      }
      this.resetForm('form')
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.productId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.loadMaterialList()
      this.open = true
      this.title = '新增商品'
    },
    handleUpdate(row) {
      this.reset()
      this.loadMaterialList()
      const productId = row.productId || this.ids
      getProduct(productId).then(response => {
        this.form = response.data
        if (!this.form.defaultMaterials) {
          this.form.defaultMaterials = []
        }
        if (!this.form.configGroups) {
          this.form.configGroups = []
        }
        for (let group of this.form.configGroups) {
          if (!group.options) {
            group.options = []
          }
          for (let option of group.options) {
            if (!option.materials) {
              option.materials = []
            }
          }
        }
        this.open = true
        this.title = '修改商品'
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.productId != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addProduct(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      let productIds = row.productId || this.ids
      if (typeof productIds === 'number') {
        productIds = [productIds]
      }
      this.$modal.confirm('是否确认删除商品编号为"' + productIds + '"的数据项?').then(() => {
        return delProducts(productIds)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      }).catch(() => {})
    },
    handleExport() {
      this.download('system/product/export', { ...this.queryParams }, `product_${new Date().getTime()}.xlsx`)
    },
    addDefaultMaterial() {
      if (!this.form.defaultMaterials) {
        this.form.defaultMaterials = []
      }
      this.form.defaultMaterials.push({
        materialId: null,
        materialName: '',
        quantity: 1
      })
    },
    removeDefaultMaterial(index) {
      this.form.defaultMaterials.splice(index, 1)
    },
    onDefaultMaterialChange(row) {
      const material = this.materialList.find(m => m.materialId === row.materialId)
      if (material) {
        row.materialName = material.materialName
        row.unit = material.unit
      }
    },
    addConfigGroup() {
      if (!this.form.configGroups) {
        this.form.configGroups = []
      }
      this.form.configGroups.push({
        groupName: '',
        groupType: '',
        isRequired: '1',
        isMultiple: '0',
        sortOrder: this.form.configGroups.length,
        options: []
      })
    },
    removeConfigGroup(index) {
      this.form.configGroups.splice(index, 1)
    },
    addConfigOption(group) {
      if (!group.options) {
        group.options = []
      }
      group.options.push({
        optionName: '',
        optionValue: '',
        extraPrice: 0,
        isDefault: '0',
        sortOrder: group.options.length,
        materials: []
      })
    },
    removeConfigOption(group, index) {
      group.options.splice(index, 1)
    },
    configOptionMaterials(option, optionIndex, groupIndex) {
      this.currentOption = option
      this.currentOptionIndex = optionIndex
      this.currentGroupIndex = groupIndex
      if (!this.currentOption.materials) {
        this.currentOption.materials = []
      }
      this.materialDialogVisible = true
    },
    addOptionMaterial() {
      this.currentOption.materials.push({
        materialId: null,
        materialName: '',
        quantity: 1
      })
    },
    removeOptionMaterial(index) {
      this.currentOption.materials.splice(index, 1)
    },
    onOptionMaterialChange(row) {
      const material = this.materialList.find(m => m.materialId === row.materialId)
      if (material) {
        row.materialName = material.materialName
      }
    },
    confirmOptionMaterials() {
      this.form.configGroups[this.currentGroupIndex].options[this.currentOptionIndex].materials = this.currentOption.materials
      this.materialDialogVisible = false
    },
    handleImageUpload(response) {
      if (response.code === 200) {
        this.form.imageUrl = response.url
        this.$modal.msgSuccess('图片上传成功')
      } else {
        this.$modal.msgError('图片上传失败')
      }
    },
    beforeImageUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$modal.msgError('只能上传图片文件！')
      }
      if (!isLt2M) {
        this.$modal.msgError('图片大小不能超过 2MB！')
      }
      return isJPG && isLt2M
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
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
.list-avatar {
  width: 60px;
  height: 60px;
  border-radius: 4px;
  object-fit: cover;
}
.list-avatar-placeholder {
  font-size: 40px;
  color: #d9d9d9;
  width: 60px;
  height: 60px;
  line-height: 60px;
  text-align: center;
}
</style>
