<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="套餐名称" prop="packageName">
        <el-input
          v-model="queryParams.packageName"
          placeholder="请输入套餐名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="套餐编码" prop="packageCode">
        <el-input
          v-model="queryParams.packageCode"
          placeholder="请输入套餐编码"
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
          v-hasPermi="['system:package:add']"
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
          v-hasPermi="['system:package:edit']"
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
          v-hasPermi="['system:package:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="packageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="套餐名称" align="center" prop="packageName" />
      <el-table-column label="套餐编码" align="center" prop="packageCode" />
      <el-table-column label="套餐价格" align="center" prop="price" />
      <el-table-column label="包含商品" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.items && scope.row.items.length > 0">
            {{ scope.row.items.map(i => i.productName + 'x' + i.quantity).join('、') }}
          </span>
          <span v-else style="color: #909399;">未配置</span>
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
            v-hasPermi="['system:package:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:package:remove']"
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

    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="套餐名称" prop="packageName">
              <el-input v-model="form.packageName" placeholder="请输入套餐名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="套餐编码" prop="packageCode">
              <el-input v-model="form.packageCode" placeholder="请输入套餐编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="套餐价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入套餐价格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option label="正常" value="0" />
                <el-option label="下架" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>

        <el-divider content-position="left">包含商品</el-divider>
        
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addItem" style="margin-bottom: 15px;">
          添加商品
        </el-button>

        <el-table :data="form.items" border style="width: 100%" v-if="form.items && form.items.length > 0">
          <el-table-column label="商品" width="250">
            <template slot-scope="scope">
              <el-select v-model="scope.row.productId" placeholder="选择商品" @change="onProductChange(scope.row)" style="width: 100%">
                <el-option
                  v-for="product in productList"
                  :key="product.productId"
                  :label="product.productName"
                  :value="product.productId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="单价" width="100">
            <template slot-scope="scope">
              ¥{{ scope.row.productPrice || 0 }}
            </template>
          </el-table-column>
          <el-table-column label="数量" width="120">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.quantity" :min="1" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="配置信息" width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.hasConfig" style="color: #67c23a; font-size: 12px;">
                收银时可配置
              </span>
              <span v-else style="color: #909399; font-size: 12px;">无配置</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template slot-scope="scope">
              <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="removeItem(scope.$index)" />
            </template>
          </el-table-column>
        </el-table>
        <el-empty v-else description="暂无商品，请点击上方按钮添加" :image-size="60" />
        
        <div v-if="form.items && form.items.length > 0" style="margin-top: 15px; padding: 10px; background: #f5f7fa; border-radius: 4px;">
          <el-row>
            <el-col :span="12">
              <span>商品原价合计：¥{{ calculateOriginalPrice() }}</span>
            </el-col>
            <el-col :span="12" style="text-align: right;">
              <span>套餐价格：¥{{ form.price || 0 }}</span>
              <span v-if="calculateOriginalPrice() > (form.price || 0)" style="color: #67c23a; margin-left: 10px;">
                优惠 ¥{{ (calculateOriginalPrice() - (form.price || 0)).toFixed(2) }}
              </span>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPackage, getPackage, addPackage, updatePackage, delPackages } from '@/api/system/package'
import { listProduct } from '@/api/system/product'
import Pagination from '@/components/Pagination'
import RightToolbar from '@/components/RightToolbar'

export default {
  name: 'Package',
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
      packageList: [],
      productList: [],
      title: '',
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        packageName: '',
        packageCode: '',
        status: ''
      },
      form: {},
      rules: {
        packageName: [
          { required: true, message: '套餐名称不能为空', trigger: 'blur' }
        ],
        packageCode: [
          { required: true, message: '套餐编码不能为空', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '套餐价格不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.loadProductList()
  },
  methods: {
    getList() {
      this.loading = true
      listPackage(this.queryParams).then(response => {
        this.packageList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    loadProductList() {
      listProduct({}).then(response => {
        this.productList = response.rows || []
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        items: []
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
      this.ids = selection.map(item => item.packageId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '新增套餐'
    },
    handleUpdate(row) {
      this.reset()
      const packageId = row.packageId || this.ids
      getPackage(packageId).then(response => {
        this.form = response.data
        if (!this.form.items) {
          this.form.items = []
        }
        for (let item of this.form.items) {
          const product = this.productList.find(p => p.productId === item.productId)
          if (product) {
            item.productPrice = product.price
            item.hasConfig = product.configGroups && product.configGroups.length > 0
          }
        }
        this.open = true
        this.title = '修改套餐'
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.packageId != null) {
            updatePackage(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPackage(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      let packageIds = row.packageId || this.ids
      if (typeof packageIds === 'number') {
        packageIds = [packageIds]
      }
      this.$modal.confirm('是否确认删除套餐编号为"' + packageIds + '"的数据项?').then(() => {
        return delPackages(packageIds)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      }).catch(() => {})
    },
    addItem() {
      if (!this.form.items) {
        this.form.items = []
      }
      this.form.items.push({
        productId: null,
        productName: '',
        productPrice: 0,
        quantity: 1,
        hasConfig: false
      })
    },
    removeItem(index) {
      this.form.items.splice(index, 1)
    },
    onProductChange(row) {
      const product = this.productList.find(p => p.productId === row.productId)
      if (product) {
        row.productName = product.productName
        row.productPrice = product.price
        row.hasConfig = product.configGroups && product.configGroups.length > 0
      }
    },
    calculateOriginalPrice() {
      if (!this.form.items) return 0
      let total = 0
      for (let item of this.form.items) {
        total += (item.productPrice || 0) * (item.quantity || 1)
      }
      return total.toFixed(2)
    }
  }
}
</script>

<style scoped>
.fixed-width {
  width: 150px;
}

.small-padding {
  padding-left: 0;
  padding-right: 0;
}
</style>
