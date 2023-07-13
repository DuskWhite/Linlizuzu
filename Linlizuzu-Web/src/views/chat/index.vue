<template>
  <div style="margin-bottom: 50px">
    <el-row>
      <el-col :span="6">
        <el-card
          style="
            height: 600px;
            color: #333;
            border-radius: 5px 0px 0px 5px;
            box-shadow: 0 2px 0px #ccc;
          "
        >
          <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc">
            在线用户<span style="font-size: 12px"
              >（点击聊天气泡开始聊天）</span
            >
          </div>
          <div
            style="padding: 10px 0"
            v-for="user in users"
            :key="user.username"
          >
            <span>{{ user.username }}</span>
            <i
              class="el-icon-chat-dot-round"
              style="margin-left: 10px; font-size: 16px; cursor: pointer"
              @click="chatUser = user.username"
            ></i>
            <span
              style="font-size: 12px; color: limegreen; margin-left: 5px"
              v-if="user.username === chatUser"
              >chatting...</span
            >
          </div>
        </el-card>
      </el-col>

      <el-col :span="18">
        <el-card class="messagecard">
          <div style="text-align: left; line-height: 30px">
            {{ chatUser }}
          </div>
          <el-divider></el-divider>
          <div style="height: 350px; overflow: auto" v-html="content"></div>
          <div>{{ this.showtext }}</div>
          <div style="height: 150px">
            <textarea
              v-model="text"
              placeholder="请输入消息，点击发送按钮发送"
              style="
                resize : none;
                height: 150px;
                width: 100%;
                padding: 20px;
                border: none;
                border-top: 1px solid #ccc;
                outline: none;
                background: rgb(240, 242, 245);
              "
            ></textarea>
            <div style="text-align: right; padding-right: 10px">
              <el-button type="primary" size="mini" @click="send"
                >发送</el-button
              >
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from "@/utils/request";
import userApi from "@/api/userManage";
let socket;

export default {
  name: "Im",
  data() {
    return {
      showtext: "",
      hereavatar: "",
      avatar: "",
      user: {},
      isCollapse: false,
      users: [],
      chatUser: "",
      text: "",
      messages: [],
      content: "",
    };
  },
  created() {
    this.init();
  },
  methods: {
    getUserAvatarByName(username) {
      userApi.getUserAvatarByName(username).then((response) => {
        this.hereavatar = response.data;
        this.user.avatar = response.data;
        console.log(response.data);
      });
    },
    send() {
      if (!this.chatUser) {
        this.$message({ type: "warning", message: "请选择聊天对象" });
        return;
      }
      if (!this.text) {
        this.$message({ type: "warning", message: "请输入内容" });
      } else {
        if (typeof WebSocket == "undefined") {
          console.log("您的浏览器不支持WebSocket");
        } else {
          console.log("您的浏览器支持WebSocket");
          // 组装待发送的消息 json
          // {"from": "zhang", "to": "admin", "text": "聊天文本"}
          let message = {
            from: this.user.username,
            to: this.chatUser,
            text: this.text,
            avatar: this.hereavatar,
          };
          socket.send(JSON.stringify(message)); // 将组装好的json发送给服务端，由服务端进行转发
          this.messages.push({
            user: this.user.username,
            text: this.text,
            avatar: this.hereavatar,
          });
          // 构建消息内容，本人消息
          this.createContent(
            null,
            this.user.username,
            this.text,
            this.hereavatar
          );
          this.text = "";
        }
      }
    },
    createContent(remoteUser, nowUser, text, avatar) {
      // 这个方法是用来将 json的聊天消息数据转换成 html的。
      let html;
      // 当前用户消息
      if (nowUser) {
        // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
        html =
          '<div class="el-row" style="padding: 5px 0">\n' +
          '  <div class="el-col el-col-22" style="text-align: right; padding-right: 10px">\n' +
          '    <div class="tip left">' +
          text +
          "</div>\n" +
          "  </div>\n" +
          '  <div class="el-col el-col-2">\n' +
          '  <span class="el-avatar el-avatar--circle" style="height: 40px; width: 40px; line-height: 40px;">\n' +
          '    <img src="' +
          this.hereavatar +
          '" style="object-fit: cover;">\n' +
          "  </span>\n" +
          "  </div>\n" +
          "</div>";
      } else if (remoteUser) {
        // remoteUser表示远程用户聊天消息，蓝色的气泡
        html =
          '<div class="el-row" style="padding: 5px 0">\n' +
          '  <div class="el-col el-col-2" style="text-align: right">\n' +
          '  <span class="el-avatar el-avatar--circle" style="height: 40px; width: 40px; line-height: 40px;">\n' +
          '    <img src="' +
          avatar +
          '" style="object-fit: cover;">\n' +
          "  </span>\n" +
          "  </div>\n" +
          '  <div class="el-col el-col-22" style="text-align: left; padding-left: 10px">\n' +
          '    <div class="tip right">' +
          text +
          "</div>\n" +
          "  </div>\n" +
          "</div>";
      }
      console.log(html);
      this.content += html;
    },
    init() {
      this.getUserAvatarByName(this.$store.getters.name);
      this.user = {
        username: this.$store.getters.name,
        text: "",
      };
      let username = this.user.username;
      console.log(username);
      let _this = this;
      if (typeof WebSocket == "undefined") {
        console.log("您的浏览器不支持WebSocket");
      } else {
        console.log("您的浏览器支持WebSocket");
        let socketUrl = "ws://localhost:9999/imserver/" + username;
        if (socket != null) {
          socket.close();
          socket = null;
        }
        // 开启一个websocket服务
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = function () {
          console.log("websocket已打开");
        };
        //  浏览器端收消息，获得从服务端发送过来的文本消息
        socket.onmessage = function (msg) {
          console.log("收到数据====" + msg.data);
          let data = JSON.parse(msg.data); // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}
          if (data.users) {
            // 获取在线人员信息
            _this.users = data.users.filter(
              (user) => user.username !== username
            ); // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
          } else {
            // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
            //  // {"from": "zhang", "text": "hello"}
            if (data.from === _this.chatUser) {
              _this.messages.push(data);
              // 构建消息内容
              _this.createContent(data.from, null, data.text, data.avatar);
            }
          }
        };
        //关闭事件
        socket.onclose = function () {
          console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
          console.log("websocket发生了错误");
        };
      }
    },
  },
};
</script>

<style>
.el-divider--horizontal {
  margin: 10px 0 10px 0;
}
.messagecard {
  height: 600px;
  margin: 0 auto;
  background-color: white;
  box-shadow: 0 2px 2px #ccc;
  border-radius: 0px 5px 5px 0px;
  background: rgb(240, 242, 245);
}
.tip {
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding: 10px;
  width: auto;
  display: inline-block !important;
  display: inline;
}

.right {
  background-color: deepskyblue;
}
.left {
  background-color: forestgreen;
}
</style>
