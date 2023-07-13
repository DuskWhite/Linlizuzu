<template>
  <div>
    <!-- <panel-group /> -->
    <el-card class="wallet">
      <el-row :gutter="20" style="padding:20px">
        <el-col class="mainbalance">资金总览</el-col>
        <el-col class="ablebalance">可用余额(元)</el-col>
        <el-col class="balance">{{ this.userForm.balance }}</el-col>
      </el-row>
      <span style="margin: 10px">充值金额</span>
      <el-input
        placeholder="请输入充值金额"
        v-model="addbalance"
        style="width: 20%; margin: 10px"
      ></el-input>
      <el-radio v-model="addbalance" label="100" border style="margin: 10px"
        >￥100</el-radio
      >
      <el-radio v-model="addbalance" label="500" border style="margin: 10px"
        >￥500</el-radio
      >
      <el-radio v-model="addbalance" label="1000" border style="margin: 10px"
        >￥1000</el-radio
      >
      <div>
        <el-button type="primary" @click="saveBalance" style="margin-left: 95px"
          >确认充值</el-button
        >
      </div>
    </el-card>
  </div>
</template>

<script>
import userApi from "@/api/userManage";
import PanelGroup from "./components/PanelGroup";
export default {
  inject: ["reload"],
  data() {
    return {
      addbalance: "",
      formLabelWidth: "130px",
      dialogFormVisible: false,
      title: "充值余额",
      userForm: {
        id: this.$store.getters.id,
        balance: 0,
      },
    };
  },
  components: {
    PanelGroup,
  },
  methods: {
    saveBalance() {
      if (this.addbalance != 0) {
        // 提交请求给后台
        userApi
          .updateUserBalance(
            this.userForm.username,
            parseFloat(this.addbalance)
          )
          .then((response) => {
            this.$message({
              message: response.message,
              type: "success",
            });
          });
        this.getUserBalance(this.userForm.id);
        setTimeout(this.reload(), 3000);
      } else {
        this.$message({
          message: "请输入正确的金额",
          type: "error",
        });
        return false;
      }
    },
    clearForm() {
      this.userForm = {
        id: this.$store.getters.id,
        balance: 0,
      };
      this.$refs.userFormRef.clearValidate();
      this.getUserBalance(this.userForm.id);
    },
    openBalanceUI() {
      this.dialogFormVisible = true;
    },
    getUserBalance(id) {
      userApi.getUserById(id).then((response) => {
        this.userForm = response.data;
        console.log(response);
      });
    },
  },
  created() {
    this.getUserBalance(this.userForm.id);
  },
};
</script>

<style lang="scss" scoped>
.wallet{
  background-image: url(https://gw.alipayobjects.com/mdn/rms_f36444/afts/img/A*pN9LTLKWgboAAAAAAAAAAAAAARQnAQ);
  
  height: 358px;
  width: 100%;
}
.mainbalance {
  font-size: 18px;
  line-height: 25px;
}
.ablebalance {
  font-size: 12px;
  padding-top: 40px;
  align-items: center;
  color: #8b93a7;
  display: flex;
}
.balance {
  color: #ff7a00;
  font-size: 48px;
  font-weight: 700;
  line-height: 61px;
  margin-top:20px;
}
</style>