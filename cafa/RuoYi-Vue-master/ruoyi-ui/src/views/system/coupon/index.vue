<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="优惠券名称" prop="couponName">
        <el-input
          v-model="queryParams.couponName"
          placeholder="请输入优惠券名称"
          clearable
          size="small"
          style="width: 240px"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="正常" value="0" />
          <el-option label="禁用" value="1" />
        </el-select>
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
          v-hasPermi="['system:coupon:add']"
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
          v-hasPermi="['system:coupon:edit']"
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
          v-hasPermi="['system:coupon:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          :disabled="multiple"
          @click="handleIssue"
        >发放</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="couponList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="优惠券ID" align="center" prop="couponId" />
      <el-table-column label="优惠券名称" align="center" prop="couponName" />
      <el-table-column label="优惠券类型" align="center" prop="couponType">
        <template slot-scope="scope">
          {{ scope.row.couponType === '1' ? '满减' : '折扣' }}
        </template>
      </el-table-column>
      <el-table-column label="优惠金额" align="center" prop="discountValue" />
      <el-table-column label="最低消费" align="center" prop="minAmount" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180" />
      <el-table-column label="结束时间" align="center" prop="endTime" width="180" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
            {{ scope.row.status === '0' ? '正常' : '禁用' }}
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
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:coupon:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:coupon:remove']"
          >删除</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="优惠券名称" prop="couponName">
          <el-input v-model="form.couponName" placeholder="请输入优惠券名称" />
        </el-form-item>
        <el-form-item label="优惠券类型" prop="couponType">
          <el-radio-group v-model="form.couponType">
            <el-radio label="1">满减</el-radio>
            <el-radio label="2">折扣</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="优惠金额" prop="discountValue">
          <el-input-number v-model="form.discountValue" :min="0.01" :max="999999" :step="0.01" placeholder="请输入优惠金额" />
        </el-form-item>
        <el-form-item label="最低消费" prop="minAmount">
          <el-input-number v-model="form.minAmount" :min="0" :max="999999" :step="0.01" placeholder="请输入最低消费" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            placeholder="请选择开始时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            v-model="form.endTime"
            type="datetime"
            placeholder="请选择结束时间"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCoupon, getCoupon, delCoupon, addCoupon, updateCoupon, batchIssueCoupon } from "@/api/system/coupon";

export default {
  name: "Coupon",
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      couponList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        couponName: "",
        status: ""
      },
      form: {},
      rules: {
        couponName: [
          { required: true, message: "优惠券名称不能为空", trigger: "blur" }
        ],
        couponType: [
          { required: true, message: "优惠券类型不能为空", trigger: "blur" }
        ],
        discountValue: [
          { required: true, message: "优惠金额不能为空", trigger: "blur" }
        ],
        minAmount: [
          { required: true, message: "最低消费不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ]
      },
      ids: [],
      single: true,
      multiple: true
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listCoupon(this.queryParams).then(response => {
        this.couponList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        couponId: null,
        couponName: null,
        couponType: "1",
        discountValue: null,
        minAmount: null,
        status: "0",
        startTime: null,
        endTime: null
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.couponId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加优惠券";
    },
    handleUpdate(row) {
      this.reset();
      const couponId = row.couponId || this.ids;
      getCoupon(couponId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改优惠券";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.couponId != null) {
            updateCoupon(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCoupon(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const couponIds = row.couponId || this.ids;
      this.$modal.confirm('是否确认删除优惠券编号为"' + couponIds + '"的数据项？').then(function() {
        return delCoupon(couponIds);
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.getList();
      }).catch(() => {});
    },
    handleIssue() {
      const couponIds = this.ids;
      if (couponIds.length === 0) {
        this.$modal.msgWarning("请先选择要发放的优惠券");
        return;
      }
      this.$modal.confirm('是否确认将选中的优惠券发放给所有会员？').then(() => {
        return batchIssueCoupon(couponIds);
      }).then((response) => {
        this.$modal.msgSuccess(response.msg || "发放成功");
        this.getList();
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
.dialog-footer {
  text-align: center;
}
</style>
