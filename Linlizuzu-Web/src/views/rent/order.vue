<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.orderNo"
            placeholder="订单编号"
            clearable
          />
          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getorderList(1)"
            >查询出租</el-button
          >
          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getorderList(2)"
            >查询租借</el-button
          >
        </el-col>
      </el-row>
    </el-card>

    <el-dialog
      @close="clearForm"
      title="交易评价"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="commentForm" ref="commentFormRef" :rules="rules">
        <el-form-item
          label="评价内容"
          :label-width="formLabelWidth"
          prop="content"
        >
          <textarea
            type="text"
            class="commentarea"
            v-model="commentForm.content"
          />
        </el-form-item>
        <el-form-item
          label="点个赞吧"
          :label-width="formLabelWidth"
          prop="reputation"
        >
          <div class="block">
            <el-rate style="margin-top:12px" v-model="score"></el-rate>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savecomment">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 查询结果栏 -->
    <el-card>
      <template>
        <el-table :data="orderList" style="width: 100%" height="450">
          <el-table-column type="index" label="序号" width="50">
            <!-- 计算序列号 -->
            <template slot-scope="scope">
              {{
                (searchModel.pageNo - 1) * searchModel.pageSize +
                scope.$index +
                1
              }}
            </template>
          </el-table-column>
          <el-table-column prop="orderId" label="订单ID" width="80" />
          <el-table-column prop="orderNo" label="订单编号" width="120" />
          <el-table-column prop="ownerName" label="出租人" width="100" />
          <el-table-column prop="renterName" label="租借人" width="100" />
          <el-table-column prop="goodName" label="租借物品" width="140" />
          <el-table-column prop="rentTime" label="租借起始时间" width="180" />
          <el-table-column prop="overTime" label="租借结束时间" width="180" />
          <el-table-column prop="orderStatus" label="订单状态" width="130">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.orderStatus == 1">租借中</el-tag>
              <el-tag v-else-if="scope.row.orderStatus == 2" type="warning"
                >正在归还</el-tag
              >
              <el-tag v-else-if="scope.row.orderStatus == 3" type="danger"
                >订单超时未归还</el-tag
              >
              <el-tag v-else-if="scope.row.orderStatus == 5" type="success"
                >订单评价已完成</el-tag
              >
              <el-tag v-else type="success">订单已完成</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="
                  flag == 0 &&
                  (scope.row.orderStatus == 1 || scope.row.orderStatus == 3)
                "
                @click="backto(scope.row)"
                type="primary"
                icon="el-icon-s-promotion"
                size="mini"
                >归还商品</el-button
              >
              <el-button
                v-if="flag == 1 && scope.row.orderStatus == 2"
                @click="ensure(scope.row)"
                type="primary"
                icon="el-icon-check"
                size="mini"
                >确认归还</el-button
              >
              <el-button
                v-if="flag == 0 && scope.row.orderStatus == 4"
                @click="openEditUi(scope.row)"
                type="primary"
                icon="el-icon-edit-outline"
                size="mini"
                >交易评价</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-card>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </div>
</template>

<script>
import orderApi from "@/api/orderManage";
import commentApi from "@/api/commentManage";
export default {
  inject: ["reload"],
  data() {
    return {
      score:0,
      flag: 1,
      currentPage: 1,
      formLabelWidth: "130px",
      orderForm: {},
      commentForm: {
        goodId: 0,
      },
      dialogFormVisible: false,
      total: 0,
      title: "",
      searchModel: {
        pageNo: 1,
        pageSize: 10,
        ownerName: this.$store.getters.name,
        renterName: this.$store.getters.name,
      },
      orderList: [],
      rules: {
        content:[
          { required: true, message: "请输入评价内容", trigger: "blur" },
          {
            min: 15,
            message: "长度在15个字以上",
            trigger: "blur",
          },
        ]
      },
      pickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
      value: "",
    };
  },
  methods: {
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
    // 归还商品
    backto(order) {
      orderApi.changeOrderStatus(2, order.orderId).then((res) => {
        this.$message({
          type: "success",
          message: res.message,
        });
        setTimeout(this.reload(), 3000);
      });
      
    },
    // 商家确认订单
    ensure(order) {
      orderApi.changeOrderStatus(4, order.orderId).then((res) => {
        this.$message({
          type: "success",
          message: res.message,
        });
        setTimeout(this.reload(), 3000);
      });
      
    },
    deleteorder(order) {
      this.$confirm(
        `您确认删除订单 ${order.orderNo + "," + order.goodName} ?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          orderApi.deleteorderById(order.orderId).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getorderList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    savecomment() {
      // 触发表单验证
      this.$refs.commentFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          this.commentForm.score = this.score;
          commentApi.addComment(this.commentForm).then((res) => {
            this.$message({
              type: "success",
              message: res.message,
            });
            this.dialogFormVisible = false;
          });
          orderApi.changeOrderStatus(5, this.commentForm.orderId);
          commentApi.addReputation(this.score,this.commentForm.ownerId);
        } else {
          console.log("error submit!!");
          return false;
        }
      });
      setTimeout(this.reload(), 3000);
    },
    clearForm() {
      this.commentForm = {};
      // 移除表单校验结果
      this.$refs.commentFormRef.clearValidate();
    },
    async openEditUi(order) {
      await commentApi
        .getCommentInfo(order.goodName, order.ownerName, order.renterName)
        .then((res) => {
          this.commentForm = res.data;
        });
      this.commentForm.orderId = order.orderId;
      this.commentForm.publishTime = this.getDateTime();
      this.dialogFormVisible = true;
    },
    // 文件页数刷新
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getorderList();
    },
    // 文件页码刷新
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getorderList();
    },
    getorderList(id) {
      if (id == 1) {
        orderApi.getorderListByOwner(this.searchModel).then((response) => {
          this.orderList = response.data.rows;
          this.total = response.data.total;
          this.flag = 1;
        });
      } else {
        orderApi.getorderListByRenter(this.searchModel).then((response) => {
          this.orderList = response.data.rows;
          this.total = response.data.total;
          this.flag = 0;
        });
      }
    },
  },
  created() {
    this.getorderList();
  },
};
</script>

<style>
.commentarea {
  margin-top: 10px;
  height: 200px;
  width: 80%;
  background: #ffffff;
  border-bottom-color: #000000;
  border-bottom-width: 1px;
  border-top-width: 1px;
  border-left-width: 1px;
  border-right-width: 1px;
  color: #000000;
}
#search .el-input {
  width: 200px;
  margin-right: 10px;
}
.el-dialog .el-input {
  width: 80%;
}
</style>
