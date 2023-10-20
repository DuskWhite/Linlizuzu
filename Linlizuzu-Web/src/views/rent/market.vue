<template>
  <el-container>
    <el-header>
      <el-card id="search">
        <el-row>
          <el-col :xs="24" :sm="22" :lg="16">
            <el-input
              placeholder="商品名"
              v-model="searchModel.goodname"
              clearable
            />
            <el-button
              type="primary"
              round
              icon="el-icon-search"
              @click="getGoodList()"
              >查询</el-button
            >
          </el-col>
        </el-row>
      </el-card>
    </el-header>
    <el-main style="margin-top: 20px">
      <el-card v-loading="loading">
        <el-row :gutter="30">
          <el-col
            :xs="12"
            :sm="6"
            :md="5"
            :lg="4"
            v-for="(go, index) in goodList"
            :key="index"
          >
            <!-- 判断商品是否处于下架状态 v-if="go.status == 1"-->
            <el-card class="cards" :body-style="{ padding: '0px' }">
              <!-- 设置商品图片，如果图片为空则展示默认图片 -->
              <el-image
                :src="go.img ? go.img : ''"
                class="image"
                :preview-src-list="[go.img ? go.img : '']"
                @error="setDefaultImage"
              />
              <div
                class="fonttotal"
                style="cursor: pointer"
                @click="CreateOrder(go.id)"
              >
                <div class="fontarea">{{ go.goodname }}</div>
                <div
                  class="fontarea"
                  style="color: #eea20e; font-size: 17px; font-weight: bold"
                >
                  ¥ {{ go.price }}/天
                </div>
              </div>
              <div class="fonttotal">
                <el-avatar
                  style="float: left"
                  :src="
                    go.useravatar
                      ? go.useravatar
                      : 'http://localhost:9999/api/uploadFile/avatar.jpg'
                  "
                  :size="18"
                  @error="setDefaultImage"
                ></el-avatar>
                <el-button
                  type="text"
                  class="textbutton"
                  @click="openCommentDialog(go.userid)"
                >
                  <span class="fontarea">{{ go.username }}</span>
                </el-button>

                <div class="reputation">信誉:{{ go.userreputation }}</div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-dialog
          style="width: 100%"
          title="商家评价"
          :visible.sync="CommentdialogFormVisible"
        >
          <el-row :gutter="30">
            <el-col
              :xs="24"
              :sm="24"
              :md="24"
              :lg="24"
              v-for="co in commentList"
              :key="co.id"
            >
              <div class="commentcard">
                <el-avatar
                  style="float: left"
                  :src="
                    co.renteravatar
                      ? co.renteravatar
                      : 'http://localhost:9999/api/uploadFile/avatar.jpg'
                  "
                  :size="35"
                  @error="setDefaultImage"
                ></el-avatar>
                <span
                  style="font-size: 17px; padding-left: 10px; font-weight: bold"
                  >{{ co.rentername }}</span
                >

                <br />
                <span style="padding-left: 10px; padding-right: 10px">{{
                  co.publishTime
                }}</span>
                <span>{{ co.goodname }}</span>
                <div style="padding-top: 20px">{{ co.content }}</div>
                <div class="block">
                  <el-rate
                    style="margin-top: 12px"
                    v-model="co.score"
                    disabled
                  ></el-rate>
                </div>
                <el-divider></el-divider>
              </div>
            </el-col>
          </el-row>
        </el-dialog>
        <!-- 商品详情 -->
        <el-dialog
          @close="clearForm"
          :title="title"
          :visible.sync="dialogFormVisible"
        >
          <el-form :model="orderForm" ref="orderFormRef" :rules="rules">
            <el-form-item
              label="商品名称"
              :label-width="formLabelWidth"
              prop="goodname"
              >{{ goodForm.goodname }}
            </el-form-item>
            <el-form-item
              label="租借时间"
              :label-width="formLabelWidth"
              prop="overTime"
            >
              <el-date-picker
                v-model="orderForm.overTime"
                type="datetime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间"
                align="right"
                :default-time="nowTime"
                :picker-options="pickerOptions"
                @change="getTotalPrice"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item
              label="共计:"
              :label-width="formLabelWidth"
              prop="orderAmountTotal"
            >
              {{ orderForm.orderAmountTotal }}</el-form-item
            >
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveorder">确 定</el-button>
          </div>
        </el-dialog>
      </el-card>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[12, 24, 36, 48]"
        :page-size="searchModel.pageSize"
        layout="total, sizes,prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-main>
    <el-backtop></el-backtop>
  </el-container>
</template>

<script>
import goodApi from "@/api/goodManage";
import commentApi from "@/api/commentManage";
import orderApi from "@/api/orderManage";
import userApi from "@/api/userManage";
export default {
  inject: ["reload"],
  data() {
    return {
      loading:false,
      commentList: [],
      nowTime: "",
      owneravatar: "",
      total: 0,
      currentPage: 1,
      searchModel: {
        pageNo: 1,
        pageSize: 12,
        status: 1,
      },
      changeNum: 0,
      dateRange: 1,
      ownerForm: { balance: 0, id: 0 },
      userForm: { id: this.$store.getters.id, balance: 0 },
      goodForm: {},
      orderForm: {
        renterName: this.$store.getters.name,
        orderStatus: 1,
        orderAmountTotal: 0,
        delete: 0,
      },
      orderList: [],
      goodList: [],
      formLabelWidth: "130px",
      dialogFormVisible: false,
      CommentdialogFormVisible: false,
      title: "租借物品",
      rules: {},
      pickerOptions: {
        shortcuts: [
          {
            text: "租一天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "租一周",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
          {
            text: "租一月",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 7 * 30);
              picker.$emit("pick", date);
            },
          },
        ],
      },
    };
  },
  methods: {
    // 获取当前时间
    getNowTime() {
      const date = new Date();
      const Hour = date.getHours(); //获取小时
      const Mintue = date.getMinutes(); //获取分钟
      const Second = date.getSeconds(); //获取秒
      var CurrentDate = "";
      if (Hour >= 10) {
        CurrentDate = Hour;
      } else {
        CurrentDate = "0" + Hour;
      }
      if (Mintue >= 10) {
        CurrentDate = CurrentDate + ":" + Mintue;
      } else {
        CurrentDate = CurrentDate + ":0" + Mintue;
      }
      if (Second >= 10) {
        CurrentDate = CurrentDate + ":" + Second;
      } else {
        CurrentDate = CurrentDate + ":0" + Second;
      }
      this.nowTime = CurrentDate;
      console.log(this.nowTime);
    }, // 文件页数刷新
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getGoodList();
    },
    // 文件页码刷新
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getGoodList();
    },
    openCommentDialog(id) {
      console.log(id);
      this.getCommentList(id);
      this.CommentdialogFormVisible = true;
    },
    getCommentList(id) {
      commentApi.getCommentList(id).then(async (response) => {
        var commentlist = response.data;
        console.log(response.data);
        for (let i = 0; i < response.data.length; i++) {
          await commentApi
            .getGoodNameById(commentlist[i].goodId)
            .then((res) => {
              commentlist[i].goodname = res.data.goodname;
            });
          await commentApi
            .getUserNameById(commentlist[i].ownerId)
            .then((res) => {
              commentlist[i].ownername = res.data.username;
            });
          await commentApi
            .getUserNameById(commentlist[i].renterId)
            .then((res) => {
              commentlist[i].rentername = res.data.username;
              commentlist[i].renteravatar = res.data.avatar;
            });
        }
        this.commentList = commentlist;
      });
    },
    getGoodList() {
      this.loading = true;
      goodApi.getMarketGoodList(this.searchModel).then(async (response) => {
        let length = 0
        let list = response.data.rows;
        this.total = response.data.total;
        console.log(response.data.total);
        // 判断当前页是否为最后一页
        if(this.searchModel.pageNo>(response.data.total/this.searchModel.pageSize)){
          length = response.data.total-(this.searchModel.pageSize*(this.searchModel.pageNo-1))
        }else{
          length = this.searchModel.pageSize
        }
        console.log(length);
        for (let i = 0; i < length; i++) {
          await userApi.getUserByName(list[i].username).then((res) => {
            list[i].userreputation = res.data.reputation;
            list[i].userid = res.data.id;
            list[i].useravatar = res.data.avatar;
          }).catch((error=>{
            console.log(error);
          }));
        }
        this.goodList = list;
        this.loading = false;
      });
    },
    getUserBalance(id) {
      userApi.getUserById(id).then((response) => {
        this.userForm = response.data;
      });
    },
    getTotalPrice() {
      this.getNowTime();
      let date1 = new Date(this.orderForm.overTime);
      let date2 = new Date(this.orderForm.rentTime);
      // 日期校验
      if (date1.getTime() > date2.getTime()) {
        this.orderForm.orderAmountTotal =
          this.goodForm.price *
          this.getDiffDay(this.orderForm.overTime, this.orderForm.rentTime);
        this.getGoodList();
      } else {
        this.$message({
          message: "请选择正确的日期",
          type: "error",
        });
      }
    },
    /**
     * @DateDiff 计算相差的天数
     * @param Date_end 结束时间
     * @param Date_start 开始时间
     * @returns {number} 相差天数
     */
    //计算日期间隔天数
    getDiffDay(date_1, date_2) {
      // 计算两个日期之间的差值
      let totalDays, diffDate;
      let myDate_1 = Date.parse(date_1) + 60000;
      let myDate_2 = Date.parse(date_2);
      // 将两个日期都转换为毫秒格式，然后做差
      diffDate = Math.abs(myDate_1 - myDate_2); // 取相差毫秒数的绝对值
      totalDays = Math.floor(diffDate / (1000 * 3600 * 24)); // 向下取整
      return totalDays; // 相差的天数
    },

    // 设置图片出错时的默认图片
    setDefaultImage(e) {
      e.target.src = "http://localhost:9999/api/uploadFile/avatar.jpg";
    },
    clearForm() {
      this.orderForm = {
        renterName: this.$store.getters.name,
        orderStatus: 1,
        orderAmountTotal: 0,
      };
      this.getUserBalance(this.userForm.id);
      // 清除错误矫正
      this.$refs.orderFormRef.clearValidate();
    },

    saveorder() {
      let that = this;
      that.$refs.orderFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          if (that.userForm.balance >= that.orderForm.orderAmountTotal) {
            // 更新订单表待写入的数据
            that.orderForm.rentTime = that.getDateTime();
            that.orderForm.orderNo =
              that.getProjectNum() + Math.floor(Math.random() * 10000);
            that.orderForm.ownerName = that.goodForm.username;
            that.orderForm.goodName = that.goodForm.goodname;
            // 调用余额改动接口对租借人和出租人的余额进行改动
            that.changeNum = that.orderForm.orderAmountTotal;
            userApi
              .updateGoodBalance(
                that.userForm.username,
                that.goodForm.username,
                that.changeNum
              )
              .then((res) => {})
              .catch((error) => {
                console.log(error);
              });
            //生成订单
            orderApi
              .saveorder(that.orderForm)
              .then((response) => {
                that.$message({
                  message: response.message,
                  type: "success",
                });

                // 触发表单验证
                // 将商品下架
                that.goodForm.status = 0;
                that.orderForm.renterName = that.$store.getters.name;
                goodApi.updateGood(that.goodForm);
                that.dialogFormVisible = false;
                that.getGoodList();

                setTimeout(that.reload(), 3000);
              })
              .catch((error) => {
                console.log("错误1：" + error);
              });
          } else {
            that.$message({
              message: "您的余额不足",
              type: "error",
            });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    CreateOrder(id) {
      this.getNowTime();
      // 通过goodApi获取当前物品的信息
      goodApi.getGoodById(id).then((response) => {
        this.goodForm = response.data;
        if (this.goodForm.username != this.orderForm.renterName) {
          this.orderForm.orderAmountTotal = response.data.price;
          this.orderForm.rentTime = this.getDateTime();
          this.dialogFormVisible = true;
        } else {
          this.$message({
            message: "不能租借自己的商品",
            type: "error",
          });
          return false;
        }
      });
    },
    getUserAvatarByName(goodList) {
      for (var i = 0; i <= goodList.length; i++) {
        console.log(goodList[i].username);
        userApi.getUserAvatarByName(goodList[i].username).then((response) => {
          return response.data;
        });
      }
    },
    getAllGood() {
      goodApi.getAllGood().then((response) => {
        this.goodList = response.data;
      });
    },
    // 获取当前日期来生成随机订单编号
    getProjectNum() {
      const projectTime = new Date(); // 当前中国标准时间
      const Year = projectTime.getFullYear(); // 获取当前年份 支持IE和火狐浏览器.
      const Month = projectTime.getMonth() + 1; // 获取中国区月份
      const Day = projectTime.getDate(); // 获取几号
      var CurrentDate = Year;
      if (Month >= 10) {
        // 判断月份和几号是否大于10或者小于10
        CurrentDate += Month;
      } else {
        CurrentDate += "0" + Month;
      }
      if (Day >= 10) {
        CurrentDate += Day;
      } else {
        CurrentDate += "0" + Day;
      }
      return CurrentDate;
    },
    getDateTime() {
      const projectTime = new Date(); // 当前中国标准时间
      const Year = projectTime.getFullYear(); // 获取当前年份 支持IE和火狐浏览器.
      const Month = projectTime.getMonth() + 1; // 获取中国区月份
      const Day = projectTime.getDate(); // 获取几号
      const Hour = projectTime.getHours(); //获取小时
      const Mintue = projectTime.getMinutes(); //获取分钟
      const Second = projectTime.getSeconds(); //获取秒
      var CurrentDate = Year;
      if (Month >= 10) {
        // 判断月份和几号是否大于10或者小于10
        CurrentDate = CurrentDate + "-" + Month;
      } else {
        CurrentDate = CurrentDate + "-0" + Month;
      }
      if (Day >= 10) {
        CurrentDate = CurrentDate + "-" + Day;
      } else {
        CurrentDate = CurrentDate + "-0" + Day;
      }
      if (Hour >= 10) {
        CurrentDate = CurrentDate + " " + Hour;
      } else {
        CurrentDate = CurrentDate + " 0" + Hour;
      }
      if (Mintue >= 10) {
        CurrentDate = CurrentDate + ":" + Mintue;
      } else {
        CurrentDate = CurrentDate + ":0" + Mintue;
      }
      if (Second >= 10) {
        CurrentDate = CurrentDate + ":" + Second;
      } else {
        CurrentDate = CurrentDate + ":0" + Second;
      }
      return CurrentDate;
    },
  },
  // 页面加载时触发
  created() {
    this.getGoodList();
    this.getUserBalance(this.userForm.id);
  },
  mounted() {
    // this.getGoodList();
  },
};
</script>

<style>
.commentcard {
  padding: 10px;
}
.textbutton {
  padding-top: 0;
  color: black;
  position: absolute;
  z-index: 1;
}
.fontarea {
  width: 11em;
  white-space: nowrap;
  height: 1.2em;
  margin: 4px;
  text-overflow: ellipsis;
  overflow: hidden;
}
.fonttotal {
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 10px;
}

.fonttotal .reputation {
  float: right;
  text-align: center;
  width: 5em;
  font-size: 10px;
  border-radius: 30px;
  background: rgb(238, 245, 255);
  color: rgb(021, 120, 255);
}
.cards {
  border: 1px solid #e4e4e4;
  display: block;
  width: 100%;
  background-size: cover;
  font: 14px/1.2em Microsoft Yahei;
  font-size: 15px;
  height: 250px;
}
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  aspect-ratio: auto 210 / 138;
  width: 100%;
  height: 138px;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>
