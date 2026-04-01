<template>
  <div class="cashier-container">
    <el-card shadow="never" class="cashier-card">
      <template slot="header">
        <div class="card-header">
          <span>收银管理</span>
        </div>
      </template>
      
      <el-form :model="form" label-width="100px" class="cashier-form">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="桌号">
              <el-select v-model="form.tableId" placeholder="请选择桌号" clearable style="width: 100%">
                <el-option
                  v-for="table in tables"
                  :key="table.tableId"
                  :label="table.tableName"
                  :value="table.tableId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会员电话">
              <el-input v-model="form.memberPhone" placeholder="请输入会员电话">
                <el-button
                  slot="append"
                  type="primary"
                  @click="queryMember"
                >
                  查询
                </el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="会员信息">
              <el-input
                v-model="memberInfo"
                placeholder="会员信息"
                readonly
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20" v-if="form.memberId">
          <el-col :span="24">
            <el-form-item label="优惠券">
              <el-select v-model="form.couponId" placeholder="请选择优惠券" clearable style="width: 100%">
                <el-option
                  v-for="coupon in coupons"
                  :key="coupon.couponId"
                  :label="getCouponLabel(coupon)"
                  :value="coupon.couponId"
                >
                  <span style="float: left">{{ coupon.couponName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 12px">
                    {{ coupon.couponType === '1' ? '满' + coupon.minAmount + '减' + coupon.discountValue : '折扣' + (coupon.discountValue * 10).toFixed(1) + '折' }}
                  </span>
                </el-option>
              </el-select>
              <div v-if="selectedCouponInfo" style="margin-top: 5px; color: #67c23a; font-size: 12px">
                {{ selectedCouponInfo }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20" v-if="form.memberId">
          <el-col :span="24">
            <el-form-item label="积分抵现">
              <el-slider 
                v-model="form.usePoints" 
                :min="0" 
                :max="maxUsePoints"
                :step="10"
                :format-tooltip="formatPointsTooltip"
                show-input
                style="width: 100%"
              />
              <div style="margin-top: 5px; color: #909399; font-size: 12px">
                可用积分: {{ memberTotalPoints }} 分，可抵扣 ¥{{ (memberTotalPoints * 0.01).toFixed(2) }}（1积分=0.01元）
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      
      <el-card shadow="never" class="product-card">
        <template slot="header">
          <div class="card-header">
            <span>商品列表</span>
            <el-button type="primary" size="small" @click="addProduct">
              添加商品
            </el-button>
          </div>
        </template>
        
        <el-table :data="orderItems" style="width: 100%">
          <el-table-column prop="productName" label="商品名称" width="200">
            <template slot-scope="scope">
              {{ scope.row.productName }}
              <div v-if="scope.row.configOptionsText" style="font-size: 12px; color: #909399;">
                {{ scope.row.configOptionsText }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="单价" width="120">
            <template slot-scope="scope">
              ¥{{ scope.row.price }}
              <span v-if="scope.row.extraPrice > 0" style="color: #f56c6c;">+¥{{ scope.row.extraPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="数量" width="100">
            <template slot-scope="scope">
              <el-input-number
                v-model="scope.row.quantity"
                :min="1"
                size="small"
                @change="updateItemSubtotal(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column prop="subtotal" label="小计" width="100">
            <template slot-scope="scope">
              ¥{{ scope.row.subtotal }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.configGroups && scope.row.configGroups.length > 0"
                type="text"
                size="small"
                @click="editProductConfig(scope.row, scope.$index)"
              >
                配置
              </el-button>
              <el-button
                type="text"
                size="small"
                style="color: #f56c6c;"
                @click="removeItem(scope.$index)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      
      <el-card shadow="never" class="package-card" style="margin-top: 20px">
        <template slot="header">
          <div class="card-header">
            <span>套餐列表</span>
            <el-button type="primary" size="small" @click="addPackage">
              添加套餐
            </el-button>
          </div>
        </template>
        
        <el-table :data="orderPackageItems" style="width: 100%">
          <el-table-column prop="packageName" label="套餐名称" width="200">
            <template slot-scope="scope">
              {{ scope.row.packageName }}
              <div v-if="scope.row.configOptionsText" style="font-size: 12px; color: #909399;">
                {{ scope.row.configOptionsText }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="单价" width="120">
            <template slot-scope="scope">
              ¥{{ scope.row.price }}
              <span v-if="scope.row.extraPrice > 0" style="color: #f56c6c;">+¥{{ scope.row.extraPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="数量" width="100">
            <template slot-scope="scope">
              <el-input-number
                v-model="scope.row.quantity"
                :min="1"
                size="small"
                @change="updatePackageSubtotal(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column prop="subtotal" label="小计" width="100">
            <template slot-scope="scope">
              ¥{{ scope.row.subtotal }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button
                v-if="hasPackageConfig(scope.row)"
                type="text"
                size="small"
                @click="editPackageConfig(scope.row, scope.$index)"
              >
                配置
              </el-button>
              <el-button
                type="text"
                size="small"
                style="color: #f56c6c;"
                @click="removePackageItem(scope.$index)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      
      <el-card shadow="never" class="amount-card" style="margin-top: 20px">
        <div class="amount-info">
          <div class="amount-item">
            <span>总金额：</span>
            <span class="amount-value">¥{{ form.totalAmount.toFixed(2) }}</span>
          </div>
          <div class="amount-item">
            <span>优惠金额：</span>
            <span class="amount-value discount">¥{{ form.discountAmount.toFixed(2) }}</span>
          </div>
          <div class="amount-item total">
            <span>实际支付：</span>
            <span class="amount-value total">¥{{ form.actualAmount.toFixed(2) }}</span>
          </div>
          <div class="amount-item points" v-if="form.memberId">
            <span>本次可获得积分：</span>
            <span class="amount-value points">+{{ pointsToEarn }}</span>
          </div>
          <div class="amount-item points-after" v-if="form.memberId">
            <span>结算后积分：</span>
            <span class="amount-value points-after">{{ pointsAfterCheckout }}</span>
          </div>
        </div>
      </el-card>
      
      <div class="cashier-buttons">
        <el-button type="primary" size="large" @click="submitCheckout">
          确认收银
        </el-button>
        <el-button size="large" @click="resetForm">
          重置
        </el-button>
      </div>
    </el-card>
    
    <el-dialog
      title="选择商品"
      :visible.sync="productDialogVisible"
      width="800px"
    >
      <el-select v-model="selectedProduct" value-key="productId" placeholder="请选择商品" @change="onProductSelect" style="width: 100%">
        <el-option
          v-for="product in products"
          :key="product.productId"
          :label="product.productName + ' - ¥' + product.price"
          :value="product"
        />
      </el-select>
      
      <div v-if="selectedProduct && selectedProduct.configGroups && selectedProduct.configGroups.length > 0" style="margin-top: 20px;">
        <el-divider content-position="left">配置选项</el-divider>
        <el-form label-width="80px">
          <el-form-item 
            v-for="group in selectedProduct.configGroups" 
            :key="group.configGroupId"
            :label="group.groupName"
            :required="group.isRequired === '1'"
          >
            <div v-if="group.isMultiple === '1'">
              <el-checkbox-group v-model="tempConfigOptions[group.configGroupId]">
                <el-checkbox 
                  v-for="option in group.options" 
                  :key="option.configOptionId"
                  :label="option.configOptionId"
                >
                  {{ option.optionName }}
                  <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
                </el-checkbox>
              </el-checkbox-group>
            </div>
            <div v-else>
              <el-radio-group v-model="tempConfigOptions[group.configGroupId]">
                <el-radio 
                  v-for="option in group.options" 
                  :key="option.configOptionId"
                  :label="option.configOptionId"
                >
                  {{ option.optionName }}
                  <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
                </el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
        </el-form>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="productDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmAddProduct">确定</el-button>
      </div>
    </el-dialog>
    
    <el-dialog
      title="选择套餐"
      :visible.sync="packageDialogVisible"
      width="900px"
    >
      <el-select v-model="selectedPackage" value-key="packageId" placeholder="请选择套餐" @change="onPackageSelect" style="width: 100%">
        <el-option
          v-for="pkg in packages"
          :key="pkg.packageId"
          :label="pkg.packageName + ' - ¥' + pkg.price"
          :value="pkg"
        />
      </el-select>
      
      <div v-if="selectedPackage && selectedPackage.items && selectedPackage.items.length > 0" style="margin-top: 20px;">
        <el-divider content-position="left">套餐商品配置</el-divider>
        <el-card v-for="(item, itemIndex) in selectedPackage.items" :key="itemIndex" style="margin-bottom: 15px;" shadow="never">
          <div slot="header">
            <span>{{ item.productName }} x {{ item.quantity }}</span>
          </div>
          <div v-if="item.configGroups && item.configGroups.length > 0">
            <el-form label-width="80px" size="small">
              <el-form-item 
                v-for="group in item.configGroups" 
                :key="group.configGroupId"
                :label="group.groupName"
                :required="group.isRequired === '1'"
              >
                <div v-if="group.isMultiple === '1'">
                  <el-checkbox-group v-model="tempPackageConfigOptions[itemIndex + '_' + group.configGroupId]">
                    <el-checkbox 
                      v-for="option in group.options" 
                      :key="option.configOptionId"
                      :label="option.configOptionId"
                    >
                      {{ option.optionName }}
                      <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
                    </el-checkbox>
                  </el-checkbox-group>
                </div>
                <div v-else>
                  <el-radio-group v-model="tempPackageConfigOptions[itemIndex + '_' + group.configGroupId]">
                    <el-radio 
                      v-for="option in group.options" 
                      :key="option.configOptionId"
                      :label="option.configOptionId"
                    >
                      {{ option.optionName }}
                      <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
                    </el-radio>
                  </el-radio-group>
                </div>
              </el-form-item>
            </el-form>
          </div>
          <el-empty v-else description="该商品无配置选项" :image-size="40" />
        </el-card>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="packageDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmAddPackage">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="修改商品配置"
      :visible.sync="configDialogVisible"
      width="600px"
    >
      <el-form label-width="80px" v-if="currentEditItem">
        <el-form-item 
          v-for="group in currentEditItem.configGroups" 
          :key="group.configGroupId"
          :label="group.groupName"
          :required="group.isRequired === '1'"
        >
          <div v-if="group.isMultiple === '1'">
            <el-checkbox-group v-model="currentEditItem.tempConfigOptions[group.configGroupId]">
              <el-checkbox 
                v-for="option in group.options" 
                :key="option.configOptionId"
                :label="option.configOptionId"
              >
                {{ option.optionName }}
                <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
              </el-checkbox>
            </el-checkbox-group>
          </div>
          <div v-else>
            <el-radio-group v-model="currentEditItem.tempConfigOptions[group.configGroupId]">
              <el-radio 
                v-for="option in group.options" 
                :key="option.configOptionId"
                :label="option.configOptionId"
              >
                {{ option.optionName }}
                <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
              </el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="configDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmEditConfig">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="修改套餐配置"
      :visible.sync="packageConfigDialogVisible"
      width="700px"
    >
      <div v-if="currentEditPackage">
        <el-card v-for="(item, itemIndex) in currentEditPackage.items" :key="itemIndex" style="margin-bottom: 15px;" shadow="never">
          <div slot="header">
            <span>{{ item.productName }} x {{ item.quantity }}</span>
          </div>
          <div v-if="item.configGroups && item.configGroups.length > 0">
            <el-form label-width="80px" size="small">
              <el-form-item 
                v-for="group in item.configGroups" 
                :key="group.configGroupId"
                :label="group.groupName"
                :required="group.isRequired === '1'"
              >
                <div v-if="group.isMultiple === '1'">
                  <el-checkbox-group v-model="currentEditPackage.tempConfigOptions[itemIndex + '_' + group.configGroupId]">
                    <el-checkbox 
                      v-for="option in group.options" 
                      :key="option.configOptionId"
                      :label="option.configOptionId"
                    >
                      {{ option.optionName }}
                      <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
                    </el-checkbox>
                  </el-checkbox-group>
                </div>
                <div v-else>
                  <el-radio-group v-model="currentEditPackage.tempConfigOptions[itemIndex + '_' + group.configGroupId]">
                    <el-radio 
                      v-for="option in group.options" 
                      :key="option.configOptionId"
                      :label="option.configOptionId"
                    >
                      {{ option.optionName }}
                      <span v-if="option.extraPrice > 0" style="color: #f56c6c;">(+¥{{ option.extraPrice }})</span>
                    </el-radio>
                  </el-radio-group>
                </div>
              </el-form-item>
            </el-form>
          </div>
          <el-empty v-else description="该商品无配置选项" :image-size="40" />
        </el-card>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="packageConfigDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmEditPackageConfig">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getMemberByPhone, calculateDiscount, checkout } from '@/api/system/cashier'
import { listTable } from '@/api/system/table'
import { listProduct, getProduct } from '@/api/system/product'
import { listPackage, getPackage } from '@/api/system/package'
import { getPublishedCoupons } from '@/api/system/coupon'

export default {
  name: 'Cashier',
  data() {
    return {
      form: {
        tableId: '',
        tableName: '',
        customerName: '',
        customerPhone: '',
        memberId: null,
        memberPhone: '',
        couponId: null,
        usePoints: 0,
        totalAmount: 0,
        discountAmount: 0,
        actualAmount: 0
      },
      memberInfo: '',
      memberTotalPoints: 0,
      calculatedPointsToEarn: 0,
      calculatedPointsAfterCheckout: 0,
      tables: [],
      products: [],
      packages: [],
      coupons: [],
      orderItems: [],
      orderPackageItems: [],
      productDialogVisible: false,
      packageDialogVisible: false,
      configDialogVisible: false,
      packageConfigDialogVisible: false,
      selectedProduct: null,
      selectedPackage: null,
      tempConfigOptions: {},
      tempPackageConfigOptions: {},
      currentEditItem: null,
      currentEditIndex: -1,
      currentEditPackage: null,
      currentEditPackageIndex: -1
    }
  },
  computed: {
    maxUsePoints() {
      const maxByPoints = this.memberTotalPoints
      const maxByAmount = Math.floor(this.form.actualAmount / 0.01)
      return Math.min(maxByPoints, maxByAmount)
    },
    pointsToEarn() {
      return this.calculatedPointsToEarn
    },
    pointsAfterCheckout() {
      return this.calculatedPointsAfterCheckout
    },
    selectedCouponInfo() {
      if (!this.form.couponId) return ''
      const coupon = this.coupons.find(c => c.couponId === this.form.couponId)
      if (!coupon) return ''
      if (coupon.couponType === '1') {
        return `满${coupon.minAmount}元减${coupon.discountValue}元`
      } else {
        return `${(coupon.discountValue * 10).toFixed(1)}折优惠`
      }
    }
  },
  created() {
    this.loadTables()
    this.loadProducts()
    this.loadPackages()
    this.loadPublishedCoupons()
  },
  methods: {
    getCouponLabel(coupon) {
      if (!coupon) return ''
      if (coupon.couponType === '1') {
        return `${coupon.couponName} (满${coupon.minAmount}减${coupon.discountValue})`
      } else {
        return `${coupon.couponName} (${(coupon.discountValue * 10).toFixed(1)}折)`
      }
    },
    formatPointsTooltip(val) {
      return `${val}积分 (抵¥${(val * 0.01).toFixed(2)})`
    },
    loadTables() {
      listTable({}).then(response => {
        if (response && response.rows) {
          this.tables = response.rows
        }
      }).catch(error => {
        this.$message.error('加载桌台失败')
      })
    },
    
    loadProducts() {
      listProduct({}).then(response => {
        if (response && response.rows) {
          this.products = response.rows
        }
      }).catch(error => {
        this.$message.error('加载商品失败')
      })
    },
    
    loadPackages() {
      listPackage({}).then(response => {
        if (response && response.rows) {
          this.packages = response.rows
        }
      }).catch(error => {
        this.$message.error('加载套餐失败')
      })
    },
    
    loadPublishedCoupons() {
      getPublishedCoupons().then(response => {
        this.coupons = response.data || []
      }).catch(error => {
        this.$message.error('加载优惠券失败')
      })
    },
    
    queryMember() {
      if (!this.form.memberPhone) {
        this.$message.warning('请输入会员电话')
        return
      }
      
      getMemberByPhone(this.form.memberPhone).then(response => {
        const member = response.data
        if (member) {
          this.form.memberId = member.memberId
          this.memberTotalPoints = member.totalPoints || 0
          const memberCode = member.memberCode ? '会员卡号：' + member.memberCode : ''
          const discountRate = member.discountRate ? (member.discountRate * 100).toFixed(0) + '%' : '无折扣'
          this.memberInfo = member.memberName + (memberCode ? ' - ' + memberCode : '') + ' - 折扣：' + discountRate + ' - 积分：' + this.memberTotalPoints
        } else {
          this.$message.info('未找到该会员信息')
          this.form.memberId = null
          this.memberInfo = ''
          this.memberTotalPoints = 0
          this.form.couponId = null
          this.form.usePoints = 0
        }
        this.calculateTotal()
      })
    },
    
    addProduct() {
      this.selectedProduct = null
      this.tempConfigOptions = {}
      this.productDialogVisible = true
    },

    onProductSelect(product) {
      if (product && product.configGroups) {
        this.tempConfigOptions = {}
        for (let group of product.configGroups) {
          if (group.isRequired === '1' && group.options && group.options.length > 0) {
            const defaultOption = group.options.find(o => o.isDefault === '1')
            if (defaultOption) {
              if (group.isMultiple === '1') {
                this.tempConfigOptions[group.configGroupId] = [defaultOption.configOptionId]
              } else {
                this.tempConfigOptions[group.configGroupId] = defaultOption.configOptionId
              }
            }
          }
        }
      }
    },
    
    confirmAddProduct() {
      if (this.selectedProduct) {
        const configOptionList = this.buildConfigOptionList(this.selectedProduct, this.tempConfigOptions)
        const extraPrice = this.calculateExtraPrice(this.selectedProduct, configOptionList)
        const configOptionsText = this.buildConfigOptionsText(this.selectedProduct, configOptionList)
        
        const newItem = {
          productId: this.selectedProduct.productId,
          productName: this.selectedProduct.productName,
          price: this.selectedProduct.price,
          extraPrice: extraPrice,
          quantity: 1,
          subtotal: this.selectedProduct.price + extraPrice,
          configGroups: this.selectedProduct.configGroups || [],
          tempConfigOptions: JSON.parse(JSON.stringify(this.tempConfigOptions)),
          configOptionList: configOptionList,
          configOptionsText: configOptionsText
        }
        this.orderItems.push(newItem)
        this.calculateTotal()
        this.productDialogVisible = false
      }
    },

    addPackage() {
      this.selectedPackage = null
      this.tempPackageConfigOptions = {}
      this.packageDialogVisible = true
    },

    onPackageSelect(pkg) {
      if (pkg && pkg.items) {
        this.tempPackageConfigOptions = {}
        for (let itemIndex = 0; itemIndex < pkg.items.length; itemIndex++) {
          const item = pkg.items[itemIndex]
          if (item.configGroups) {
            for (let group of item.configGroups) {
              if (group.isRequired === '1' && group.options && group.options.length > 0) {
                const defaultOption = group.options.find(o => o.isDefault === '1')
                if (defaultOption) {
                  const key = itemIndex + '_' + group.configGroupId
                  if (group.isMultiple === '1') {
                    this.tempPackageConfigOptions[key] = [defaultOption.configOptionId]
                  } else {
                    this.tempPackageConfigOptions[key] = defaultOption.configOptionId
                  }
                }
              }
            }
          }
        }
      }
    },

    confirmAddPackage() {
      if (this.selectedPackage) {
        const { extraPrice, configOptionsText, itemConfigs } = this.buildPackageConfigInfo(this.selectedPackage, this.tempPackageConfigOptions)
        
        const newItem = {
          packageId: this.selectedPackage.packageId,
          packageName: this.selectedPackage.packageName,
          price: this.selectedPackage.price,
          extraPrice: extraPrice,
          quantity: 1,
          subtotal: this.selectedPackage.price + extraPrice,
          items: this.selectedPackage.items || [],
          tempConfigOptions: JSON.parse(JSON.stringify(this.tempPackageConfigOptions)),
          itemConfigs: itemConfigs,
          configOptionsText: configOptionsText
        }
        this.orderPackageItems.push(newItem)
        this.calculateTotal()
        this.packageDialogVisible = false
      }
    },

    buildPackageConfigInfo(pkg, tempConfigOptions) {
      let extraPrice = 0
      const configTexts = []
      const itemConfigs = []
      
      if (pkg.items) {
        for (let itemIndex = 0; itemIndex < pkg.items.length; itemIndex++) {
          const item = pkg.items[itemIndex]
          const itemConfigList = []
          
          if (item.configGroups) {
            for (let group of item.configGroups) {
              const key = itemIndex + '_' + group.configGroupId
              const selectedIds = tempConfigOptions[key]
              if (!selectedIds) continue
              
              const ids = Array.isArray(selectedIds) ? selectedIds : [selectedIds]
              for (let optionId of ids) {
                const option = group.options.find(o => o.configOptionId === optionId)
                if (option) {
                  itemConfigList.push({
                    configGroupId: group.configGroupId,
                    groupName: group.groupName,
                    groupType: group.groupType,
                    configOptionId: option.configOptionId,
                    optionName: option.optionName,
                    optionValue: option.optionValue,
                    extraPrice: option.extraPrice,
                    quantity: Array.isArray(selectedIds) ? 1 : 1
                  })
                  if (option.extraPrice) {
                    extraPrice += option.extraPrice
                  }
                  configTexts.push(option.optionName)
                }
              }
            }
          }
          itemConfigs.push({
            productId: item.productId,
            productName: item.productName,
            quantity: item.quantity,
            configOptionList: itemConfigList
          })
        }
      }
      
      return {
        extraPrice: extraPrice,
        configOptionsText: configTexts.join('、'),
        itemConfigs: itemConfigs
      }
    },

    hasPackageConfig(pkg) {
      if (!pkg.items) return false
      for (let item of pkg.items) {
        if (item.configGroups && item.configGroups.length > 0) {
          return true
        }
      }
      return false
    },

    editPackageConfig(row, index) {
      this.currentEditPackage = row
      this.currentEditPackageIndex = index
      this.packageConfigDialogVisible = true
    },

    confirmEditPackageConfig() {
      if (this.currentEditPackage) {
        const { extraPrice, configOptionsText, itemConfigs } = this.buildPackageConfigInfo(
          { items: this.currentEditPackage.items },
          this.currentEditPackage.tempConfigOptions
        )
        this.currentEditPackage.extraPrice = extraPrice
        this.currentEditPackage.configOptionsText = configOptionsText
        this.currentEditPackage.itemConfigs = itemConfigs
        this.updatePackageSubtotal(this.currentEditPackage)
      }
      this.packageConfigDialogVisible = false
    },

    buildConfigOptionList(product, tempConfigOptions) {
      const result = []
      if (!product.configGroups) return result
      
      for (let group of product.configGroups) {
        const selectedIds = tempConfigOptions[group.configGroupId]
        if (!selectedIds) continue
        
        const ids = Array.isArray(selectedIds) ? selectedIds : [selectedIds]
        for (let optionId of ids) {
          const option = group.options.find(o => o.configOptionId === optionId)
          if (option) {
            result.push({
              configGroupId: group.configGroupId,
              groupName: group.groupName,
              groupType: group.groupType,
              configOptionId: option.configOptionId,
              optionName: option.optionName,
              optionValue: option.optionValue,
              extraPrice: option.extraPrice,
              quantity: Array.isArray(selectedIds) ? 1 : 1
            })
          }
        }
      }
      return result
    },

    calculateExtraPrice(product, configOptionList) {
      let extraPrice = 0
      for (let option of configOptionList) {
        if (option.extraPrice) {
          extraPrice += option.extraPrice * (option.quantity || 1)
        }
      }
      return extraPrice
    },

    buildConfigOptionsText(product, configOptionList) {
      if (!configOptionList || configOptionList.length === 0) return ''
      return configOptionList.map(o => o.optionName).join('、')
    },

    editProductConfig(row, index) {
      this.currentEditItem = row
      this.currentEditIndex = index
      this.configDialogVisible = true
    },

    confirmEditConfig() {
      if (this.currentEditItem) {
        const configOptionList = this.buildConfigOptionList(
          { configGroups: this.currentEditItem.configGroups },
          this.currentEditItem.tempConfigOptions
        )
        this.currentEditItem.configOptionList = configOptionList
        this.currentEditItem.extraPrice = this.calculateExtraPrice(null, configOptionList)
        this.currentEditItem.configOptionsText = this.buildConfigOptionsText(null, configOptionList)
        this.updateItemSubtotal(this.currentEditItem)
      }
      this.configDialogVisible = false
    },
    
    updateItemSubtotal(item) {
      item.subtotal = (item.price + (item.extraPrice || 0)) * item.quantity
      this.calculateTotal()
    },
    
    updatePackageSubtotal(item) {
      item.subtotal = (item.price + (item.extraPrice || 0)) * item.quantity
      this.calculateTotal()
    },
    
    removeItem(index) {
      this.orderItems.splice(index, 1)
      this.calculateTotal()
    },
    
    removePackageItem(index) {
      this.orderPackageItems.splice(index, 1)
      this.calculateTotal()
    },
    
    calculateTotal() {
      let total = 0
      
      this.orderItems.forEach(item => {
        total += item.subtotal
      })
      
      this.orderPackageItems.forEach(item => {
        total += item.subtotal
      })
      
      this.form.totalAmount = total
      
      if (this.form.memberId || this.form.couponId || this.form.usePoints > 0) {
        calculateDiscount({
          memberId: this.form.memberId,
          couponId: this.form.couponId,
          totalAmount: total,
          usePoints: this.form.usePoints || 0
        }).then(response => {
          const data = response.data
          this.form.discountAmount = data.discountAmount
          this.form.actualAmount = data.actualAmount
          this.calculatedPointsToEarn = data.pointsToEarn || 0
          this.calculatedPointsAfterCheckout = data.pointsAfterCheckout || 0
        })
      } else {
        this.form.discountAmount = 0
        this.form.actualAmount = total
        this.calculatedPointsToEarn = Math.floor(total)
        this.calculatedPointsAfterCheckout = this.memberTotalPoints + this.calculatedPointsToEarn
      }
    },
    
    submitCheckout() {
      if (!this.form.tableId) {
        this.$message.warning('请选择桌台')
        return
      }
      
      if (this.orderItems.length === 0 && this.orderPackageItems.length === 0) {
        this.$message.warning('请添加商品或套餐')
        return
      }
      
      const table = this.tables.find(t => t.tableId === this.form.tableId)
      if (table) {
        this.form.tableName = table.tableName
      }
      
      const orderItemsData = this.orderItems.map(item => ({
        productId: item.productId,
        productName: item.productName,
        price: item.price + (item.extraPrice || 0),
        quantity: item.quantity,
        subtotal: item.subtotal,
        configOptionList: item.configOptionList || []
      }))
      
      const orderPackageItemsData = this.orderPackageItems.map(item => ({
        packageId: item.packageId,
        packageName: item.packageName,
        price: item.price + (item.extraPrice || 0),
        quantity: item.quantity,
        subtotal: item.subtotal,
        itemConfigs: item.itemConfigs || []
      }))
      
      const checkoutData = {
        tableId: this.form.tableId,
        tableName: this.form.tableName,
        customerName: '',
        customerPhone: this.form.memberId ? this.form.memberPhone : '',
        memberId: this.form.memberId,
        couponId: this.form.couponId,
        usePoints: this.form.usePoints || 0,
        totalAmount: this.form.totalAmount,
        discountAmount: this.form.discountAmount,
        actualAmount: this.form.actualAmount,
        orderItems: orderItemsData,
        orderPackageItems: orderPackageItemsData
      }
      
      checkout(checkoutData).then(response => {
        this.$message.success('收银成功')
        this.resetForm()
      }).catch(error => {
        this.$message.error('收银失败：' + (error.msg || error.message || '未知错误'))
      })
    },
    
    resetForm() {
      this.form = {
        tableId: '',
        tableName: '',
        customerName: '',
        customerPhone: '',
        memberId: null,
        memberPhone: '',
        couponId: null,
        usePoints: 0,
        totalAmount: 0,
        discountAmount: 0,
        actualAmount: 0
      }
      this.memberInfo = ''
      this.memberTotalPoints = 0
      this.calculatedPointsToEarn = 0
      this.calculatedPointsAfterCheckout = 0
      this.coupons = []
      this.orderItems = []
      this.orderPackageItems = []
    }
  },
  watch: {
    'form.usePoints'(val) {
      this.calculateTotal()
    },
    'form.couponId'(val) {
      this.calculateTotal()
    }
  }
}
</script>

<style scoped>
.cashier-container {
  padding: 20px;
}

.cashier-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.cashier-form {
  margin-bottom: 20px;
}

.product-card,
.package-card {
  margin-bottom: 20px;
}

.amount-card {
  margin-bottom: 20px;
}

.amount-info {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 30px;
}

.amount-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.amount-value {
  font-size: 16px;
  font-weight: bold;
}

.amount-value.discount {
  color: #f56c6c;
}

.amount-item.total {
  font-size: 18px;
}

.amount-value.total {
  color: #409eff;
  font-size: 20px;
}

.amount-item.points {
  font-size: 16px;
}

.amount-value.points {
  color: #67c23a;
  font-size: 18px;
  font-weight: bold;
}

.amount-item.points-after {
  font-size: 16px;
}

.amount-value.points-after {
  color: #e6a23c;
  font-size: 18px;
  font-weight: bold;
}

.cashier-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

.dialog-footer {
  text-align: right;
}
</style>
