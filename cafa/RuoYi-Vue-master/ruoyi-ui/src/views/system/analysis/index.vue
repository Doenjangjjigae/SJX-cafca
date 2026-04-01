<template>
  <div class="app-container">
    <el-card class="operate-card">
      <div class="operate-area">
        <el-form :inline="true" :model="queryParams" class="demo-form-inline">
          <el-form-item label="统计类型">
            <el-radio-group v-model="queryParams.type" @change="handleTypeChange">
              <el-radio label="day">按日</el-radio>
              <el-radio label="week">按周</el-radio>
              <el-radio label="month">按月</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="handleDateChange"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">查询</el-button>
            <el-button @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>销售额统计</span>
            </div>
          </template>
          <div id="salesChart" class="chart"></div>
        </el-card>
      </el-col>

      <el-col :span="24">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>订单数量分析</span>
            </div>
          </template>
          <div id="orderChart" class="chart"></div>
        </el-card>
      </el-col>

      <el-col :span="24">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>畅销商品排行榜</span>
            </div>
          </template>
          <div id="productChart" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getSalesAnalysis, getOrderAnalysis, getTopProducts } from '@/api/system/analysis'

export default {
  name: 'Analysis',
  data() {
    return {
      queryParams: {
        type: 'day',
        beginTime: null,
        endTime: null
      },
      dateRange: [],
      salesChart: null,
      orderChart: null,
      productChart: null
    }
  },
  mounted() {
    this.initDateRange()
    this.initCharts()
    this.handleQuery()
  },
  beforeDestroy() {
    if (this.salesChart) {
      this.salesChart.dispose()
    }
    if (this.orderChart) {
      this.orderChart.dispose()
    }
    if (this.productChart) {
      this.productChart.dispose()
    }
  },
  methods: {
    initDateRange() {
      const end = new Date()
      const start = new Date()
      start.setDate(start.getDate() - 30)
      this.dateRange = [start, end]
      this.queryParams.beginTime = start
      this.queryParams.endTime = end
    },
    initCharts() {
      this.salesChart = echarts.init(document.getElementById('salesChart'))
      this.orderChart = echarts.init(document.getElementById('orderChart'))
      this.productChart = echarts.init(document.getElementById('productChart'))

      window.addEventListener('resize', () => {
        this.salesChart.resize()
        this.orderChart.resize()
        this.productChart.resize()
      })
    },
    handleTypeChange() {
      this.handleQuery()
    },
    handleDateChange(val) {
      if (val) {
        this.queryParams.beginTime = val[0]
        this.queryParams.endTime = val[1]
      }
    },
    handleQuery() {
      this.getSalesData()
      this.getOrderData()
      this.getTopProductsData()
    },
    resetQuery() {
      this.initDateRange()
      this.queryParams.type = 'day'
      this.handleQuery()
    },
    getSalesData() {
      getSalesAnalysis(this.queryParams.type, this.queryParams.beginTime, this.queryParams.endTime).then(response => {
        const data = response.data || []
        const xAxis = data.map(item => item.time)
        const series = data.map(item => item.sales)

        this.salesChart.setOption({
          title: {
            show: data.length === 0,
            text: '暂无销售数据',
            left: 'center',
            top: 'center',
            textStyle: {
              color: '#909399',
              fontSize: 14
            }
          },
          tooltip: {
            trigger: 'axis'
          },
          xAxis: {
            type: 'category',
            data: xAxis
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '¥{value}'
            }
          },
          series: [{
            data: series,
            type: 'line',
            smooth: true,
            itemStyle: {
              color: '#409EFF'
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                  offset: 0, color: 'rgba(64, 158, 255, 0.5)'
                }, {
                  offset: 1, color: 'rgba(64, 158, 255, 0.1)'
                }]
              }
            }
          }]
        })
      })
    },
    getOrderData() {
      getOrderAnalysis(this.queryParams.type, this.queryParams.beginTime, this.queryParams.endTime).then(response => {
        const data = response.data || []
        const xAxis = data.map(item => item.time)
        const series = data.map(item => item.orderCount)

        this.orderChart.setOption({
          title: {
            show: data.length === 0,
            text: '暂无订单数据',
            left: 'center',
            top: 'center',
            textStyle: {
              color: '#909399',
              fontSize: 14
            }
          },
          tooltip: {
            trigger: 'axis'
          },
          xAxis: {
            type: 'category',
            data: xAxis
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            data: series,
            type: 'bar',
            itemStyle: {
              color: '#67C23A'
            }
          }]
        })
      })
    },
    getTopProductsData() {
      getTopProducts(10, this.queryParams.beginTime, this.queryParams.endTime).then(response => {
        const data = response.data || []
        const xAxis = data.map(item => item.productName)
        const series = data.map(item => item.salesVolume)

        this.productChart.setOption({
          title: {
            show: data.length === 0,
            text: '暂无商品销售数据',
            left: 'center',
            top: 'center',
            textStyle: {
              color: '#909399',
              fontSize: 14
            }
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          xAxis: {
            type: 'value'
          },
          yAxis: {
            type: 'category',
            data: xAxis,
            axisLabel: {
              interval: 0,
              rotate: 0
            }
          },
          series: [{
            data: series,
            type: 'bar',
            itemStyle: {
              color: '#E6A23C'
            }
          }]
        })
      })
    }
  }
}
</script>

<style scoped>
.operate-area {
  margin-bottom: 20px;
}

.chart-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chart {
  width: 100%;
  height: 400px;
}
</style>
