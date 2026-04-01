<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会员姓名" prop="memberName">
        <el-input
          v-model="queryParams.memberName"
          placeholder="请输入会员姓名"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员编号" prop="memberCode">
        <el-input
          v-model="queryParams.memberCode"
          placeholder="请输入会员编号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入联系电话"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:member:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:member:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:member:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-view"
          size="mini"
          @click="handleViewPoints"
          v-hasPermi="['system:member:points:query']"
        >积分记录</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-document"
          size="mini"
          @click="handleViewConsume"
          v-hasPermi="['system:member:consume:query']"
        >消费记录</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="memberList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="会员ID" align="center" prop="memberId" width="100" />
      <el-table-column label="会员姓名" align="center" prop="memberName" width="120" />
      <el-table-column label="会员编号" align="center" prop="memberCode" width="120" />
      <el-table-column label="联系电话" align="center" prop="phone" width="120" />
      <el-table-column label="邮箱" align="center" prop="email" width="180" />
      <el-table-column label="出生日期" align="center" prop="birthdate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" prop="gender" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.gender === '0'">男</span>
          <span v-else-if="scope.row.gender === '1'">女</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="总积分" align="center" prop="totalPoints" width="100" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
            {{ scope.row.status === '0' ? '正常' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:member:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:member:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewPoints(scope.row)"
            v-hasPermi="['system:member:points:query']"
          >积分记录</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleViewConsume(scope.row)"
            v-hasPermi="['system:member:consume:query']"
          >消费记录</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-present"
            @click="handleGrantCoupon(scope.row)"
            v-hasPermi="['system:member:coupon:grant']"
          >发放优惠券</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员姓名" prop="memberName">
          <el-input v-model="form.memberName" placeholder="请输入会员姓名" />
        </el-form-item>
        <el-form-item label="会员编号" prop="memberCode">
          <el-input v-model="form.memberCode" placeholder="请输入会员编号" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthdate">
          <el-date-picker
            v-model="form.birthdate"
            type="date"
            placeholder="选择出生日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 积分记录对话框 -->
    <el-dialog :title="'会员积分记录'" :visible.sync="pointsDialogVisible" width="800px" append-to-body>
      <el-form :model="pointsQueryParams" ref="pointsQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="积分类型">
          <el-select v-model="pointsQueryParams.type" placeholder="请选择积分类型" clearable style="width: 120px">
            <el-option label="消费获得" value="0" />
            <el-option label="手动录入" value="1" />
            <el-option label="积分兑换" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getPointsList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetPointsQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="2">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAddPoints"
            v-hasPermi="['system:member:points:add']"
          >手动录入积分</el-button>
        </el-col>
      </el-row>

      <el-table v-loading="pointsLoading" :data="pointsList" @selection-change="handlePointsSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="记录ID" align="center" prop="id" width="100" />
        <el-table-column label="会员编号" align="center" prop="memberCode" width="120" />
        <el-table-column label="积分数量" align="center" prop="points" width="100" />
        <el-table-column label="积分类型" align="center" prop="type" width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.type === '0'">消费获得</span>
            <span v-else-if="scope.row.type === '1'">手动录入</span>
            <span v-else-if="scope.row.type === '2'">积分兑换</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" />
        <el-table-column label="操作人" align="center" prop="createBy" width="120" />
        <el-table-column label="操作时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDeletePoints(scope.row)"
              v-hasPermi="['system:member:points:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="pointsTotal>0"
        :total="pointsTotal"
        :page.sync="pointsQueryParams.pageNum"
        :limit.sync="pointsQueryParams.pageSize"
        @pagination="getPointsList"
      />
    </el-dialog>

    <!-- 消费记录对话框 -->
    <el-dialog :title="'会员消费记录'" :visible.sync="consumeDialogVisible" width="800px" append-to-body>
      <el-form :model="consumeQueryParams" ref="consumeQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input
            v-model="consumeQueryParams.orderNumber"
            placeholder="请输入订单编号"
            clearable
            style="width: 180px"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="consumeQueryParams.status" placeholder="请选择状态" clearable style="width: 120px">
            <el-option label="正常" value="0" />
            <el-option label="取消" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getConsumeList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetConsumeQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="consumeLoading" :data="consumeList" @selection-change="handleConsumeSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="记录ID" align="center" prop="id" width="100" />
        <el-table-column label="会员编号" align="center" prop="memberCode" width="120" />
        <el-table-column label="订单编号" align="center" prop="orderNumber" width="180" />
        <el-table-column label="消费金额" align="center" prop="amount" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.amount }}</span>
          </template>
        </el-table-column>
        <el-table-column label="获得积分" align="center" prop="points" width="100" />
        <el-table-column label="状态" align="center" prop="status" width="80">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
              {{ scope.row.status === '0' ? '正常' : '取消' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" />
        <el-table-column label="操作时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="consumeTotal>0"
        :total="consumeTotal"
        :page.sync="consumeQueryParams.pageNum"
        :limit.sync="consumeQueryParams.pageSize"
        @pagination="getConsumeList"
      />
    </el-dialog>

    <!-- 手动录入积分对话框 -->
    <el-dialog :title="'手动录入积分'" :visible.sync="addPointsDialogVisible" width="400px" append-to-body>
      <el-form ref="pointsForm" :model="pointsForm" :rules="pointsRules" label-width="80px">
        <el-form-item label="会员编号" prop="memberCode">
          <el-input v-model="pointsForm.memberCode" disabled />
        </el-form-item>
        <el-form-item label="积分数量" prop="points">
          <el-input v-model.number="pointsForm.points" type="number" placeholder="请输入积分数量" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="pointsForm.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPointsForm">确 定</el-button>
        <el-button @click="cancelAddPoints">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 发放优惠券对话框 -->
    <el-dialog title="发放优惠券" :visible.sync="grantCouponDialogVisible" width="500px" append-to-body>
      <el-form ref="grantCouponForm" :model="grantCouponForm" :rules="grantCouponRules" label-width="80px">
        <el-form-item label="会员姓名">
          <el-input v-model="grantCouponForm.memberName" disabled />
        </el-form-item>
        <el-form-item label="选择优惠券" prop="couponId">
          <el-select v-model="grantCouponForm.couponId" placeholder="请选择优惠券" style="width: 100%">
            <el-option
              v-for="coupon in availableCoupons"
              :key="coupon.couponId"
              :label="coupon.couponName + ' (' + (coupon.couponType === '1' ? '满减' : '折扣') + ')'"
              :value="coupon.couponId"
            >
              <span>{{ coupon.couponName }}</span>
              <span style="float: right; color: #8492a6; font-size: 12px">
                {{ coupon.couponType === '1' ? '满' + coupon.minAmount + '减' + coupon.discountValue : '折扣' + (coupon.discountValue * 10) + '折' }}
              </span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitGrantCoupon">确 定</el-button>
        <el-button @click="grantCouponDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMember, getMember, addMember, updateMember, delMember, grantCoupon, getMemberCoupons } from '@/api/system/member'
import { listMemberPoints, addManualPoints, delMemberPoints } from '@/api/system/memberPoints'
import { listMemberConsume } from '@/api/system/memberConsume'
import { listCoupon } from '@/api/system/coupon'

export default {
  name: 'Member',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 会员列表
      memberList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        memberName: '',
        memberCode: '',
        phone: ''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        memberName: [
          { required: true, message: '会员姓名不能为空', trigger: 'blur' }
        ],
        memberCode: [
          { required: true, message: '会员编号不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' }
        ]
      },
      // 积分记录相关
      pointsDialogVisible: false,
      pointsLoading: false,
      pointsIds: [],
      pointsTotal: 0,
      pointsList: [],
      pointsQueryParams: {
        pageNum: 1,
        pageSize: 10,
        memberId: '',
        type: ''
      },
      // 消费记录相关
      consumeDialogVisible: false,
      consumeLoading: false,
      consumeIds: [],
      consumeTotal: 0,
      consumeList: [],
      consumeQueryParams: {
        pageNum: 1,
        pageSize: 10,
        memberId: '',
        orderNumber: '',
        status: ''
      },
      // 手动录入积分相关
      addPointsDialogVisible: false,
      pointsForm: {
        memberId: '',
        memberCode: '',
        points: 0,
        remark: ''
      },
      pointsRules: {
        points: [
          { required: true, message: '积分数量不能为空', trigger: 'blur' },
          { type: 'number', message: '积分数量必须为数字', trigger: 'blur' }
        ]
      },
      grantCouponDialogVisible: false,
      grantCouponForm: {
        memberId: '',
        memberName: '',
        couponId: ''
      },
      grantCouponRules: {
        couponId: [
          { required: true, message: '请选择优惠券', trigger: 'change' }
        ]
      },
      availableCoupons: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询会员列表 */
    getList() {
      this.loading = true
      listMember(this.queryParams).then(response => {
        this.memberList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询积分记录列表 */
    getPointsList() {
      this.pointsLoading = true
      listMemberPoints(this.pointsQueryParams).then(response => {
        this.pointsList = response.rows
        this.pointsTotal = response.total
        this.pointsLoading = false
      })
    },
    /** 查询消费记录列表 */
    getConsumeList() {
      this.consumeLoading = true
      listMemberConsume(this.consumeQueryParams).then(response => {
        this.consumeList = response.rows
        this.consumeTotal = response.total
        this.consumeLoading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        memberId: null,
        memberName: '',
        memberCode: '',
        phone: '',
        email: '',
        birthdate: null,
        gender: '0',
        address: '',
        totalPoints: 0,
        status: '0',
        remark: ''
      }
      this.resetForm('form')
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
    // 重置积分查询
    resetPointsQuery() {
      this.resetForm('pointsQueryForm')
      this.getPointsList()
    },
    // 重置消费查询
    resetConsumeQuery() {
      this.resetForm('consumeQueryForm')
      this.getConsumeList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.memberId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 积分记录多选框选中数据
    handlePointsSelectionChange(selection) {
      this.pointsIds = selection.map(item => item.id)
    },
    // 消费记录多选框选中数据
    handleConsumeSelectionChange(selection) {
      this.consumeIds = selection.map(item => item.id)
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加会员'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const memberId = row.memberId || this.ids
      getMember(memberId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改会员'
      })
    },
    /** 查看积分记录 */
    handleViewPoints(row) {
      const memberId = row.memberId || this.ids
      this.pointsQueryParams.memberId = memberId
      this.pointsDialogVisible = true
      this.getPointsList()
    },
    /** 查看消费记录 */
    handleViewConsume(row) {
      const memberId = row.memberId || this.ids
      this.consumeQueryParams.memberId = memberId
      this.consumeDialogVisible = true
      this.getConsumeList()
    },
    /** 手动录入积分 */
    handleAddPoints() {
      const memberId = this.pointsQueryParams.memberId
      getMember(memberId).then(response => {
        this.pointsForm.memberId = response.data.memberId
        this.pointsForm.memberCode = response.data.memberCode
        this.addPointsDialogVisible = true
      })
    },
    /** 提交积分表单 */
    submitPointsForm() {
      this.$refs['pointsForm'].validate(valid => {
        if (valid) {
          addManualPoints(this.pointsForm.memberId, this.pointsForm.points, this.pointsForm.remark).then(response => {
            if (response.code === 200) {
              this.$modal.msgSuccess('录入成功')
              this.addPointsDialogVisible = false
              this.getPointsList()
              this.getList()
            }
          })
        }
      })
    },
    /** 取消录入积分 */
    cancelAddPoints() {
      this.addPointsDialogVisible = false
      this.pointsForm = {
        memberId: '',
        memberCode: '',
        points: 0,
        remark: ''
      }
      this.resetForm('pointsForm')
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const memberIds = row.memberId || this.ids
      this.$modal.confirm('确定要删除选中的会员吗？').then(function() {
        return delMember(memberIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    /** 删除积分记录 */
    handleDeletePoints(row) {
      const ids = row.id || this.pointsIds
      this.$modal.confirm('确定要删除选中的积分记录吗？').then(function() {
        return delMemberPoints(ids)
      }).then(() => {
        this.getPointsList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    /** 提交表单 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.memberId != null) {
            updateMember(this.form).then(response => {
              if (response.code === 200) {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              }
            })
          } else {
            addMember(this.form).then(response => {
              if (response.code === 200) {
                this.$modal.msgSuccess('新增成功')
                this.open = false
                this.getList()
              }
            })
          }
        }
      })
    },
    handleGrantCoupon(row) {
      this.grantCouponForm = {
        memberId: row.memberId,
        memberName: row.memberName,
        couponId: ''
      }
      listCoupon({ status: '0', pageSize: 100 }).then(response => {
        this.availableCoupons = response.rows
        this.grantCouponDialogVisible = true
      })
    },
    submitGrantCoupon() {
      this.$refs['grantCouponForm'].validate(valid => {
        if (valid) {
          grantCoupon(this.grantCouponForm.memberId, this.grantCouponForm.couponId).then(response => {
            this.$modal.msgSuccess('发放成功')
            this.grantCouponDialogVisible = false
          })
        }
      })
    }
  }
}
</script>
