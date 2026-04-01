<template>
  <div class="app-container">
    <div class="ai-container">
      <div class="ai-header">
        <h2>AI客服</h2>
        <p>您好，我是AI客服助手，有什么可以帮助您的？</p>
      </div>
      
      <div class="ai-chat-content">
        <div v-for="(message, index) in messages" :key="index" :class="['message-item', message.type]">
          <div class="message-avatar">
            <img v-if="message.type === 'user'" src="/avatar/user.png" alt="用户" />
            <img v-else src="/avatar/ai.png" alt="AI" />
          </div>
          <div class="message-content">
            <div class="message-text">{{ message.content }}</div>
            <div class="message-time">{{ message.time }}</div>
          </div>
        </div>
        <div v-if="loading" class="loading">
          <el-icon class="is-loading"><i class="el-icon-loading"></i></el-icon>
          <span>AI正在思考...</span>
        </div>
      </div>
      
      <div class="ai-input-area">
        <el-input
          v-model="inputMessage"
          placeholder="请输入您的问题..."
          @keyup.enter.native="sendMessage"
          clearable
        />
        <el-button type="primary" @click="sendMessage" :disabled="!inputMessage.trim()">发送</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AIChat',
  data() {
    return {
      messages: [
        {
          type: 'ai',
          content: '您好，我是AI客服助手，有什么可以帮助您的？',
          time: this.formatTime(new Date())
        }
      ],
      inputMessage: '',
      loading: false
    }
  },
  methods: {
    sendMessage() {
      if (!this.inputMessage.trim()) return
      
      // 添加用户消息
      this.messages.push({
        type: 'user',
        content: this.inputMessage,
        time: this.formatTime(new Date())
      })
      
      // 显示加载状态
      this.loading = true
      
      // 调用AI API
      this.$api.ai.ask({
        question: this.inputMessage
      }).then(response => {
        // 隐藏加载状态
        this.loading = false
        
        // 添加AI回复
        this.messages.push({
          type: 'ai',
          content: response.data,
          time: this.formatTime(new Date())
        })
      }).catch(error => {
        // 隐藏加载状态
        this.loading = false
        
        // 添加错误消息
        this.messages.push({
          type: 'ai',
          content: '抱歉，AI服务暂时不可用，请稍后再试。',
          time: this.formatTime(new Date())
        })
        console.error('AI API error:', error)
      })
      
      // 清空输入框
      this.inputMessage = ''
    },
    formatTime(date) {
      const hours = date.getHours().toString().padStart(2, '0')
      const minutes = date.getMinutes().toString().padStart(2, '0')
      return `${hours}:${minutes}`
    }
  }
}
</script>

<style scoped>
.app-container {
  min-height: 100vh;
  padding: 20px;
  background-color: #f5f7fa;
}

.ai-container {
  max-width: 800px;
  margin: 0 auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.ai-header {
  background-color: #409eff;
  color: #fff;
  padding: 20px;
  text-align: center;
}

.ai-header h2 {
  margin: 0 0 10px 0;
  font-size: 24px;
}

.ai-header p {
  margin: 0;
  font-size: 14px;
  opacity: 0.9;
}

.ai-chat-content {
  height: 500px;
  overflow-y: auto;
  padding: 20px;
  background-color: #f9f9f9;
}

.message-item {
  display: flex;
  margin-bottom: 20px;
  animation: fadeIn 0.3s ease-in-out;
}

.message-item.user {
  justify-content: flex-end;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  margin: 0 10px;
  flex-shrink: 0;
}

.message-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.message-content {
  max-width: 70%;
  padding: 12px 16px;
  border-radius: 18px;
  position: relative;
}

.message-item.user .message-content {
  background-color: #409eff;
  color: #fff;
  border-bottom-right-radius: 4px;
}

.message-item.ai .message-content {
  background-color: #fff;
  color: #333;
  border-bottom-left-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.message-text {
  font-size: 14px;
  line-height: 1.5;
}

.message-time {
  font-size: 12px;
  opacity: 0.6;
  margin-top: 5px;
  text-align: right;
}

.loading {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
  color: #909399;
}

.loading i {
  margin-right: 10px;
}

.ai-input-area {
  display: flex;
  padding: 20px;
  background-color: #fff;
  border-top: 1px solid #ebeef5;
}

.ai-input-area .el-input {
  flex: 1;
  margin-right: 10px;
}

.ai-input-area .el-button {
  flex-shrink: 0;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>