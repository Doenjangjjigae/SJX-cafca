<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入订单编号"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="桌台ID" prop="tableId">
        <el-input
          v-model="queryParams.tableId"
          placeholder="请输入桌台ID"
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
          <el-option label="待支付" value="0" />
          <el-option label="已支付" value="1" />
          <el-option label="已完成" value="2" />
          <el-option label="已取消" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['system:order:add']"
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
          v-hasPermi="['system:order:edit']"
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
          v-hasPermi="['system:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="orderId" width="80" />
      <el-table-column label="订单编号" align="center" prop="orderNumber" width="180" />
      <el-table-column label="桌台名称" align="center" prop="tableName" width="100" />
      <el-table-column label="顾客电话" align="center" prop="customerPhone" width="120" />
      <el-table-column label="总金额" align="center" prop="totalAmount" width="100">
        <template slot-scope="scope">
          ¥{{ scope.row.totalAmount ? scope.row.totalAmount.toFixed(2) : '0.00' }}
        </template>
      </el-table-column>
      <el-table-column label="优惠金额" align="center" prop="discountAmount" width="100">
        <template slot-scope="scope">
          <span style="color: #f56c6c;">¥{{ scope.row.discountAmount ? scope.row.discountAmount.toFixed(2) : '0.00' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实付金额" align="center" prop="actualAmount" width="100">
        <template slot-scope="scope">
          <span style="color: #409eff; font-weight: bold;">¥{{ scope.row.actualAmount ? scope.row.actualAmount.toFixed(2) : '0.00' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" size="small">待支付</el-tag>
          <el-tag type="success" v-else-if="scope.row.status === '1'" size="small">已支付</el-tag>
          <el-tag type="info" v-else-if="scope.row.status === '2'" size="small">已完成</el-tag>
          <el-tag type="danger" v-else-if="scope.row.status === '3'" size="small">已取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:order:remove']"
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

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="桌台ID" prop="tableId">
          <el-input v-model="form.tableId" placeholder="请输入桌台ID" />
        </el-form-item>
        <el-form-item label="桌台名称" prop="tableName">
          <el-input v-model="form.tableName" placeholder="请输入桌台名称" />
        </el-form-item>
        <el-form-item label="顾客电话" prop="customerPhone">
          <el-input v-model="form.customerPhone" placeholder="请输入顾客电话" />
        </el-form-item>
        <el-form-item label="总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入总金额" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="待支付" value="0" />
            <el-option label="已支付" value="1" />
            <el-option label="已完成" value="2" />
            <el-option label="已取消" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>

      <el-divider content-position="left">商品列表</el-divider>
      <el-table :data="form.items || []" border style="width: 100%" v-if="form.items && form.items.length > 0">
        <el-table-column label="商品名称" prop="productName" />
        <el-table-column label="单价" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.price ? scope.row.price.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" width="80" />
        <el-table-column label="小计" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.subtotal ? scope.row.subtotal.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="原料选项" width="150">
          <template slot-scope="scope">
            <span v-if="scope.row.materialOptions">{{ formatMaterialOptions(scope.row.materialOptions) }}</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无商品信息" :image-size="60" />

      <el-divider content-position="left">套餐列表</el-divider>
      <el-table :data="form.packageItems || []" border style="width: 100%" v-if="form.packageItems && form.packageItems.length > 0">
        <el-table-column label="套餐名称" prop="packageName" />
        <el-table-column label="单价" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.price ? scope.row.price.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" width="80" />
        <el-table-column label="小计" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.subtotal ? scope.row.subtotal.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="配置选项" width="200">
          <template slot-scope="scope">
            <span v-if="scope.row.itemConfigs && scope.row.itemConfigs.length > 0">
              {{ formatPackageItemConfigs(scope.row.itemConfigs) }}
            </span>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无套餐信息" :image-size="60" />

      <el-divider content-position="left">优惠信息</el-divider>
      <div v-if="form.coupons && form.coupons.length > 0">
        <el-table :data="form.coupons" border style="width: 100%">
          <el-table-column label="优惠券名称" width="200">
            <template slot-scope="scope">
              {{ scope.row.coupon ? scope.row.coupon.couponName : '-' }}
            </template>
          </el-table-column>
          <el-table-column label="优惠类型" width="120">
            <template slot-scope="scope">
              <span v-if="scope.row.coupon">
                <el-tag v-if="scope.row.coupon.couponType === '1'" size="small" type="danger">满减</el-tag>
                <el-tag v-else size="small" type="warning">折扣</el-tag>
              </span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="优惠详情" width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.coupon">
                {{ scope.row.coupon.couponType === '1' ? '满' + scope.row.coupon.minAmount + '减' + scope.row.coupon.discountValue : (scope.row.coupon.discountValue * 10).toFixed(1) + '折' }}
              </span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="优惠金额" width="120">
            <template slot-scope="scope">
              <span style="color: #f56c6c;">¥{{ scope.row.discountAmount ? scope.row.discountAmount.toFixed(2) : '0.00' }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-else style="color: #909399; text-align: center; padding: 10px;">
        未使用优惠券
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 订单详情对话框 -->
    <el-dialog title="订单详情" :visible.sync="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border v-if="orderDetail.order">
        <el-descriptions-item label="订单编号">{{ orderDetail.order.orderNumber }}</el-descriptions-item>
        <el-descriptions-item label="桌台名称">{{ orderDetail.order.tableName }}</el-descriptions-item>
        <el-descriptions-item label="顾客电话">{{ orderDetail.order.customerPhone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="总金额">
          <span style="font-weight: bold;">¥{{ orderDetail.order.totalAmount ? orderDetail.order.totalAmount.toFixed(2) : '0.00' }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="优惠金额">
          <span style="color: #f56c6c; font-weight: bold;">¥{{ orderDetail.order.discountAmount ? orderDetail.order.discountAmount.toFixed(2) : '0.00' }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="积分抵扣">
          <span style="color: #67c23a; font-weight: bold;">{{ getUsePointsText() }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="实付金额">
          <span style="color: #409eff; font-weight: bold; font-size: 16px;">¥{{ orderDetail.order.actualAmount ? orderDetail.order.actualAmount.toFixed(2) : '0.00' }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag v-if="orderDetail.order.status === '0'" size="small">待支付</el-tag>
          <el-tag type="success" v-else-if="orderDetail.order.status === '1'" size="small">已支付</el-tag>
          <el-tag type="info" v-else-if="orderDetail.order.status === '2'" size="small">已完成</el-tag>
          <el-tag type="danger" v-else-if="orderDetail.order.status === '3'" size="small">已取消</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ orderDetail.order.createTime }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">商品列表</el-divider>
      <el-table :data="orderDetail.items" border style="width: 100%" v-if="orderDetail.items && orderDetail.items.length > 0">
        <el-table-column label="商品名称" width="200">
          <template slot-scope="scope">
            {{ scope.row.productName }}
            <div v-if="scope.row.configOptionsText" style="font-size: 12px; color: #909399; margin-top: 4px;">
              {{ scope.row.configOptionsText }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="单价" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.price ? scope.row.price.toFixed(2) : '0.00' }}
            <span v-if="scope.row.extraPrice > 0" style="color: #f56c6c; font-size: 12px;">+¥{{ scope.row.extraPrice.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" width="80" />
        <el-table-column label="小计" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.subtotal ? scope.row.subtotal.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="配置选项" width="200">
          <template slot-scope="scope">
            <span v-if="scope.row.configOptionList && scope.row.configOptionList.length > 0">
              {{ formatConfigOptions(scope.row.configOptionList) }}
            </span>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无商品信息" :image-size="60" />

      <el-divider content-position="left">套餐列表</el-divider>
      <el-table :data="orderDetail.packageItems" border style="width: 100%" v-if="orderDetail.packageItems && orderDetail.packageItems.length > 0">
        <el-table-column label="套餐名称" width="200">
          <template slot-scope="scope">
            {{ scope.row.packageName }}
            <div v-if="scope.row.configOptionsText" style="font-size: 12px; color: #909399; margin-top: 4px;">
              {{ scope.row.configOptionsText }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="单价" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.price ? scope.row.price.toFixed(2) : '0.00' }}
            <span v-if="scope.row.extraPrice > 0" style="color: #f56c6c; font-size: 12px;">+¥{{ scope.row.extraPrice.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" width="80" />
        <el-table-column label="小计" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.subtotal ? scope.row.subtotal.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="配置选项" width="200">
          <template slot-scope="scope">
            <span v-if="scope.row.itemConfigs && scope.row.itemConfigs.length > 0">
              {{ formatPackageItemConfigs(scope.row.itemConfigs) }}
            </span>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无套餐信息" :image-size="60" />

      <el-divider content-position="left">优惠信息</el-divider>
      <div v-if="orderDetail.coupons && orderDetail.coupons.length > 0">
        <el-table :data="orderDetail.coupons" border style="width: 100%">
          <el-table-column label="优惠券名称" width="200">
            <template slot-scope="scope">
              {{ scope.row.coupon ? scope.row.coupon.couponName : '-' }}
            </template>
          </el-table-column>
          <el-table-column label="优惠类型" width="120">
            <template slot-scope="scope">
              <span v-if="scope.row.coupon">
                <el-tag v-if="scope.row.coupon.couponType === '1'" size="small" type="danger">满减</el-tag>
                <el-tag v-else size="small" type="warning">折扣</el-tag>
              </span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="优惠详情" width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.coupon">
                {{ scope.row.coupon.couponType === '1' ? '满' + scope.row.coupon.minAmount + '减' + scope.row.coupon.discountValue : (scope.row.coupon.discountValue * 10).toFixed(1) + '折' }}
              </span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="优惠金额" width="120">
            <template slot-scope="scope">
              <span style="color: #f56c6c;">¥{{ scope.row.discountAmount ? scope.row.discountAmount.toFixed(2) : '0.00' }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-else style="color: #909399; text-align: center; padding: 10px;">
        未使用优惠券
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder, getOrder, addOrder, updateOrder, delOrder, delOrders, getOrderDetail } from '@/api/system/order'
import Pagination from '@/components/Pagination'
import RightToolbar from '@/components/RightToolbar'

export default {
  name: 'Order',
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
      orderList: [],
      title: '',
      open: false,
      dateRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: '',
        tableId: null,
        status: '',
        beginTime: '',
        endTime: ''
      },
      form: {},
      rules: {
        orderNumber: [
          { required: true, message: '订单编号不能为空', trigger: 'blur' }
        ],
        tableId: [
          { required: true, message: '桌台ID不能为空', trigger: 'blur' }
        ],
        tableName: [
          { required: true, message: '桌台名称不能为空', trigger: 'blur' }
        ]
      },
      detailOpen: false,
      orderDetail: {
        order: null,
        items: [],
        packageItems: [],
        coupons: []
      }
    }
  },
  created() {
    this.handleRouteParams()
    this.getList()
  },
  beforeMount() {
    this.handleRouteParams()
  },
  methods: {
    handleRouteParams() {
      const dateRange = this.$route.query.dateRange
      if (dateRange) {
        const dates = dateRange.split(',')
        if (dates.length === 2) {
          this.dateRange = [dates[0], dates[1]]
          this.queryParams.beginTime = dates[0]
          this.queryParams.endTime = dates[1]
        }
      }
    },
    getList() {
      this.loading = true
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {}
      this.resetForm('form')
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      if (this.dateRange && this.dateRange.length > 0) {
        this.queryParams.beginTime = this.dateRange[0]
        this.queryParams.endTime = this.dateRange[1]
      } else {
        this.queryParams.beginTime = ''
        this.queryParams.endTime = ''
      }
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.dateRange = []
      this.queryParams.beginTime = ''
      this.queryParams.endTime = ''
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '新增订单'
    },
    handleUpdate(row) {
      this.reset()
      const orderId = row.orderId || this.ids
      getOrderDetail(orderId).then(response => {
        this.form = response.data.order
        this.form.items = response.data.items
        this.form.packageItems = response.data.packageItems
        this.form.coupons = response.data.coupons
        this.open = true
        this.title = '修改订单'
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          const orderData = {
            ...this.form,
            items: this.form.items || [],
            packageItems: this.form.packageItems || [],
            coupons: this.form.coupons || []
          }
          if (this.form.orderId != null) {
            updateOrder(orderData).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addOrder(orderData).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      let orderIds = row.orderId || this.ids
      if (typeof orderIds === 'number') {
        orderIds = [orderIds]
      }
      this.$modal.confirm('是否确认删除订单编号为"' + orderIds + '"的数据项?').then(() => {
        return delOrders(orderIds)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      }).catch(() => {})
    },
    handleExport() {
      this.download('system/order/export', { ...this.queryParams }, `order_${new Date().getTime()}.xlsx`)
    },
    handleDetail(row) {
      getOrderDetail(row.orderId).then(response => {
        this.orderDetail = response.data
        this.detailOpen = true
      })
    },
    formatMaterialOptions(options) {
      if (!options) return '-'
      try {
        const opts = JSON.parse(options)
        if (Array.isArray(opts) && opts.length > 0) {
          return opts.map(o => o.optionLabel).join(', ')
        }
        return '-'
      } catch (e) {
        return options
      }
    },
    formatConfigOptions(configOptionList) {
      if (!configOptionList || !Array.isArray(configOptionList)) return '-'
      return configOptionList.map(o => o.optionName).join('、')
    },
    getUsePointsText() {
      const points = this.orderDetail && this.orderDetail.order && this.orderDetail.order.usePoints
      if (!points || points === 0) return '0积分 (¥0.00)'
      const pointsValue = parseInt(points) || 0
      const discountValue = (pointsValue * 0.01).toFixed(2)
      return pointsValue + '积分 (¥' + discountValue + ')'
    },
    formatPackageItemConfigs(itemConfigs) {
      if (!itemConfigs || !Array.isArray(itemConfigs)) return '-'
      const texts = []
      for (let itemConfig of itemConfigs) {
        if (itemConfig.configOptionList && itemConfig.configOptionList.length > 0) {
          const optionsText = itemConfig.configOptionList.map(o => o.optionName).join(', ')
          texts.push(itemConfig.productName + ': ' + optionsText)
        }
      }
      return texts.length > 0 ? texts.join('; ') : '-'
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
