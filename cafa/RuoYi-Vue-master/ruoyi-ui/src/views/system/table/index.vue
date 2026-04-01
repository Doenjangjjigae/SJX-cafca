<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="桌台名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入桌台名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="桌台编号" prop="tableNumber">
        <el-input
          v-model="queryParams.tableNumber"
          placeholder="请输入桌台编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="空闲" value="0" />
          <el-option label="占用" value="1" />
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
          v-hasPermi="['system:table:add']"
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
          v-hasPermi="['system:table:edit']"
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
          v-hasPermi="['system:table:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="桌台ID" align="center" prop="tableId" />
      <el-table-column label="桌台名称" align="center" prop="tableName" />
      <el-table-column label="桌台编号" align="center" prop="tableNumber" />
      <el-table-column label="容纳人数" align="center" prop="capacity" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
            {{ scope.row.status === '0' ? '空闲' : '占用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:table:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:table:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.status === '0' ? 'el-icon-lock' : 'el-icon-unlock'"
            @click="handleStatusChange(scope.row)"
            v-hasPermi="['system:table:edit']"
          >
            {{ scope.row.status === '0' ? '占用' : '空闲' }}
          </el-button>
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

    <!-- 表单弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="桌台名称" prop="tableName">
          <el-input v-model="form.tableName" placeholder="请输入桌台名称" />
        </el-form-item>
        <el-form-item label="桌台编号" prop="tableNumber">
          <el-input v-model="form.tableNumber" placeholder="请输入桌台编号" />
        </el-form-item>
        <el-form-item label="容纳人数" prop="capacity">
          <el-input-number v-model="form.capacity" :min="1" :max="100" placeholder="请输入容纳人数" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">空闲</el-radio>
            <el-radio label="1">占用</el-radio>
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
  </div>
</template>

<script>
import { listTable, getTable, addTable, updateTable, deleteTable, updateTableStatus } from "@/api/system/table";

export default {
  name: "Table",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 桌台表格数据
      tableList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: null,
        tableNumber: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tableName: [
          { required: true, message: "桌台名称不能为空", trigger: "blur" }
        ],
        tableNumber: [
          { required: true, message: "桌台编号不能为空", trigger: "blur" }
        ],
        capacity: [
          { required: true, message: "容纳人数不能为空", trigger: "blur" }
        ]
      },
      // 选中的桌台ID
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询桌台列表 */
    getList() {
      this.loading = true;
      listTable(this.queryParams).then(response => {
        this.tableList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加桌台";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tableId = row.tableId || this.ids;
      getTable(tableId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改桌台";
      });
    },
    /** 状态变更操作 */
    handleStatusChange(row) {
      const status = row.status === '0' ? '1' : '0';
      this.$confirm(`确定要将桌台 ${row.tableName} 状态修改为${status === '0' ? '空闲' : '占用'}吗？`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        updateTableStatus(row.tableId, status).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.getList();
        });
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tableIds = row.tableId || this.ids;
      this.$confirm(`确定要删除所选桌台吗？`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        deleteTable(tableIds).then(response => {
          this.$modal.msgSuccess("删除成功");
          this.getList();
        });
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tableId != null) {
            updateTable(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTable(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 重置表单 */
    reset() {
      this.form = {
        tableId: null,
        tableName: null,
        tableNumber: null,
        capacity: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
    }
  }
};
</script>
