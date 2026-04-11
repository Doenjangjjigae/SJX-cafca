<template>
  <div class="app-container home coffee-home">
    <!-- 顶部店铺概览区域 -->
    <div class="coffee-header">
      <div class="coffee-banner">
        <div class="banner-content">
          <h1>欢迎使用咖啡厅管理系统</h1>
          <p>高效管理您的咖啡厅业务，提升运营效率</p>
        </div>
        <!-- 预留图片区域 -->
        <div class="banner-image-area">
          <div class="image-placeholder">
            <i class="el-icon-picture-outline"></i>
            <p>店铺图片区域</p>
            <small>后续可在此添加店铺外观、环境等图片</small>
          </div>
        </div>
      </div>
    </div>

    <!-- 业务概览卡片 -->
    <el-row :gutter="20" class="coffee-stats">
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon coffee-icon">
              <i class="el-icon-coffee-cup"></i>
            </div>
            <div class="stat-info">
              <h3>今日订单</h3>
              <p class="stat-number">0</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon member-icon">
              <i class="el-icon-user"></i>
            </div>
            <div class="stat-info">
              <h3>会员数量</h3>
              <p class="stat-number">0</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon product-icon">
              <i class="el-icon-shopping-bag"></i>
            </div>
            <div class="stat-info">
              <h3>产品种类</h3>
              <p class="stat-number">0</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon revenue-icon">
              <i class="el-icon-money"></i>
            </div>
            <div class="stat-info">
              <h3>今日营收</h3>
              <p class="stat-number">¥0.00</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
        <!-- 业务操作区域 -->
    <el-row :gutter="20" class="coffee-operations">
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="operation-card">
          <div slot="header" class="card-header">
            <h3>订单管理</h3>
          </div>
          <div class="operation-content">
            <ul class="operation-list">
              <li>
                <el-button type="primary" plain @click="goToPage('/system/order')">
                  <i class="el-icon-s-order"></i> 订单列表
                </el-button>
              </li>
              <li>
                <el-button type="success" plain @click="goToPage('/system/cashier')">
                  <i class="el-icon-cashier"></i> 收银台
                </el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="operation-card">
          <div slot="header" class="card-header">
            <h3>产品管理</h3>
          </div>
          <div class="operation-content">
            <ul class="operation-list">
              <li>
                <el-button type="info" plain @click="goToPage('/system/product')">
                  <i class="el-icon-goods"></i> 产品列表
                </el-button>
              </li>
              <li>
                <el-button type="warning" plain @click="goToPage('/system/package')">
                  <i class="el-icon-present"></i> 套餐管理
                </el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="operation-card">
          <div slot="header" class="card-header">
            <h3>会员管理</h3>
          </div>
          <div class="operation-content">
            <ul class="operation-list">
              <li>
                <el-button type="danger" plain @click="goToPage('/system/member')">
                  <i class="el-icon-user-solid"></i> 会员列表
                </el-button>
              </li>
              <li>
                <el-button type="primary" plain @click="goToPage('/system/coupon')">
                  <i class="el-icon-ticket"></i> 优惠券管理
                </el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
    </el-row>
        <!-- 更多功能区域 -->
    <el-row :gutter="20" class="coffee-more-functions">
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="operation-card">
          <div slot="header" class="card-header">
            <h3>表格管理</h3>
          </div>
          <div class="operation-content">
            <ul class="operation-list">
              <li>
                <el-button type="info" plain @click="goToPage('/system/table')">
                  <i class="el-icon-table"></i> 桌位管理
                </el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="operation-card">
          <div slot="header" class="card-header">
            <h3>库存管理</h3>
          </div>
          <div class="operation-content">
            <ul class="operation-list">
              <li>
                <el-button type="warning" plain @click="goToPage('/system/inventory')">
                  <i class="el-icon-s-grid"></i> 库存管理
                </el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="operation-card">
          <div slot="header" class="card-header">
            <h3>AI助手</h3>
          </div>
          <div class="operation-content">
            <ul class="operation-list">
              <li>
                <el-button type="primary" plain @click="goToPage('/system/ai')">
                  <i class="el-icon-magic-stick"></i> AI助手
                </el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 底部信息区域 -->
    <div class="coffee-footer">
      <p>© 2026 咖啡厅管理系统 - 高效管理，轻松运营</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      version: '1.0.0'
    }
  },
  methods: {
    goToPage(path) {
      this.$router.push(path)
    }
  }
}
</script>

<style scoped>
.coffee-home {
  background-color: #f8f5f0;
  min-height: 100vh;
}

.coffee-header {
  background: linear-gradient(135deg, #8B4513 0%, #A0522D 100%);
  color: white;
  padding: 30px 0;
  margin-bottom: 30px;
}

.coffee-banner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.banner-content h1 {
  font-size: 36px;
  margin-bottom: 10px;
  font-weight: bold;
}

.banner-content p {
  font-size: 18px;
  opacity: 0.9;
}

.banner-image-area {
  width: 300px;
  height: 200px;
  border-radius: 8px;
  overflow: hidden;
  border: 2px dashed rgba(255, 255, 255, 0.5);
}

.image-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
}

.image-placeholder:hover {
  background-color: rgba(255, 255, 255, 0.2);
}

.image-placeholder i {
  font-size: 48px;
  margin-bottom: 10px;
}

.image-placeholder p {
  font-size: 16px;
  margin-bottom: 5px;
}

.image-placeholder small {
  font-size: 12px;
  opacity: 0.8;
}

.coffee-stats {
  padding: 0 20px;
  max-width: 1200px;
  margin: 0 auto 30px;
}

.stat-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.15);
}

.stat-content {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  font-size: 24px;
  color: white;
}

.coffee-icon {
  background-color: #8B4513;
}

.member-icon {
  background-color: #4169E1;
}

.product-icon {
  background-color: #20B2AA;
}

.revenue-icon {
  background-color: #FF6347;
}

.stat-info h3 {
  font-size: 16px;
  color: #666;
  margin-bottom: 5px;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.coffee-operations,
.coffee-more-functions {
  padding: 0 20px;
  max-width: 1200px;
  margin: 0 auto 30px;
}

.operation-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.operation-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.15);
}

.card-header h3 {
  font-size: 18px;
  color: #333;
  margin: 0;
}

.operation-content {
  padding: 20px;
}

.operation-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.operation-list li {
  margin-bottom: 10px;
}

.operation-list li:last-child {
  margin-bottom: 0;
}

.coffee-footer {
  text-align: center;
  padding: 30px 0;
  background-color: #333;
  color: white;
  margin-top: 50px;
}

.coffee-footer p {
  margin: 0;
  font-size: 14px;
  opacity: 0.8;
}

@media (max-width: 768px) {
  .coffee-banner {
    flex-direction: column;
    text-align: center;
  }
  
  .banner-image-area {
    margin-top: 20px;
  }
  
  .stat-content {
    flex-direction: column;
    text-align: center;
  }
  
  .stat-icon {
    margin-right: 0;
    margin-bottom: 15px;
  }
}
</style>