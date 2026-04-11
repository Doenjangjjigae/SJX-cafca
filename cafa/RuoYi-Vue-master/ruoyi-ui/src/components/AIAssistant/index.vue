<template>
  <div class="ai-assistant">
    <div class="chat-window" v-if="show" :class="{ 'fullscreen': fullscreen }">
      <div class="chat-header">
        <h3>AI智能助手</h3>
        <div class="header-buttons">
          <button class="fullscreen-btn" @click="toggleFullscreen">
            <i v-if="!fullscreen" class="el-icon-full-screen"></i>
            <i v-else class="el-icon-remove"></i>
          </button>
          <button class="close-btn" @click="show = false">×</button>
        </div>
      </div>
      <div class="messages" ref="messagesContainer">
        <div v-for="(msg, index) in messages" :key="index" 
             :class="msg.type === 'user' ? 'user-message' : 'ai-message'">
          <div class="message-content">{{ msg.content }}</div>
        </div>
        <div v-if="loading" class="loading">
          <span class="loading-dot"></span>
          <span class="loading-dot"></span>
          <span class="loading-dot"></span>
        </div>
      </div>
      <div class="input-area">
        <input v-model="inputMessage" @keyup.enter="sendMessage" placeholder="输入问题...">
        <button @click="sendMessage" :disabled="loading || !inputMessage.trim()">发送</button>
      </div>
    </div>
    <button class="toggle-button" @click="show = !show" :class="{ 'active': show }">
      <i class="el-icon-message"></i>
    </button>
  </div>
</template>

<script>
export default {
  name: 'AIAssistant',
  data() {
    return {
      show: false,
      inputMessage: '',
      messages: [],
      loading: false,
      fullscreen: false
    }
  },
  mounted() {
    // 初始化消息
    this.messages.push({
      type: 'ai',
      content: '您好！我是咖啡店管理系统的智能助手，有什么可以帮您的吗？' +
               '\n\n您可以问我：\n- 产品信息\n- 库存状态\n- 销售数据\n- 系统操作指导'
    })
  },
  methods: {
    sendMessage() {
      if (!this.inputMessage.trim() || this.loading) return
      
      const question = this.inputMessage.trim()
      this.messages.push({
        type: 'user',
        content: question
      })
      this.inputMessage = ''
      this.loading = true
      
      // 滚动到底部
      this.scrollToBottom()
      
      // 调用AI API
      this.$api.ai.ask({ question }).then(response => {
        this.messages.push({
          type: 'ai',
          content: response.data
        })
        this.loading = false
        this.scrollToBottom()
      }).catch(error => {
        console.error('AI请求失败:', error)
        this.messages.push({
          type: 'ai',
          content: '抱歉，我暂时无法回答您的问题，请稍后再试。'
        })
        this.loading = false
        this.scrollToBottom()
      })
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messagesContainer
        if (container) {
          container.scrollTop = container.scrollHeight
        }
      })
    },
    toggleFullscreen() {
      this.fullscreen = !this.fullscreen
      // 控制左侧菜单的显示/隐藏
      this.$store.dispatch('app/toggleSideBarHide', this.fullscreen)
    }
  }
}
</script>

<style scoped>
.ai-assistant {
  position: fixed;
  bottom: 30px;
  right: 30px;
  z-index: 1000;
}

.toggle-button {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: #409EFF;
  color: white;
  border: none;
  font-size: 24px;
  cursor: pointer;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
}

.toggle-button:hover {
  background: #66B1FF;
  transform: scale(1.1);
}

.toggle-button.active {
  background: #66B1FF;
}

.chat-window {
  width: 350px;
  height: 450px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
  transition: all 0.3s ease;
}

.chat-window.fullscreen {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  border-radius: 0;
  margin: 0;
  z-index: 9999;
}

.chat-header {
  padding: 15px;
  background: #409EFF;
  color: white;
  border-radius: 8px 8px 0 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chat-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.header-buttons {
  display: flex;
  gap: 10px;
  align-items: center;
}

.fullscreen-btn {
  background: none;
  border: none;
  color: white;
  font-size: 16px;
  cursor: pointer;
  padding: 0;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.fullscreen-btn:hover {
  opacity: 0.8;
}

.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 20px;
  cursor: pointer;
  padding: 0;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.messages {
  flex: 1;
  padding: 15px;
  overflow-y: auto;
  background: #f8f9fa;
}

.message-content {
  max-width: 80%;
  padding: 10px 15px;
  border-radius: 18px;
  margin-bottom: 10px;
  line-height: 1.4;
}

.user-message .message-content {
  background: #E6F7FF;
  color: #1890FF;
  align-self: flex-end;
  margin-left: auto;
  border-bottom-right-radius: 4px;
}

.ai-message .message-content {
  background: white;
  color: #333;
  align-self: flex-start;
  border-bottom-left-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.loading {
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.loading-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #409EFF;
  margin: 0 2px;
  animation: loading 1.4s infinite ease-in-out both;
}

.loading-dot:nth-child(1) {
  animation-delay: -0.32s;
}

.loading-dot:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes loading {
  0%, 80%, 100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

.input-area {
  padding: 15px;
  border-top: 1px solid #eaeaea;
  display: flex;
  gap: 10px;
}

.input-area input {
  flex: 1;
  padding: 10px 15px;
  border: 1px solid #d9d9d9;
  border-radius: 20px;
  outline: none;
  transition: all 0.3s;
}

.input-area input:focus {
  border-color: #409EFF;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.input-area button {
  padding: 0 20px;
  background: #409EFF;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.input-area button:hover {
  background: #66B1FF;
}

.input-area button:disabled {
  background: #d9d9d9;
  cursor: not-allowed;
}
</style>