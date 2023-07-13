<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="在租商品" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">在租物品数量</div>
          <count-to
            :start-val="0"
            :end-val="count1"
            :duration="2"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('messages')">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="出租商品" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">借用物品数量</div>
          <count-to
            :start-val="0"
            :end-val="count2"
            :duration="3000"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('purchases')">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="钱包" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">余额</div>
          <!-- 数字滚动 -->
          <count-to
            :start-val="0"
            :end-val="this.userForm.balance"
            :duration="3200"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('shoppings')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="商品管理" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">进行中的订单数量</div>
          <count-to
            :start-val="0"
            :end-val="count3"
            :duration="3600"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from "vue-count-to";
import userApi from "@/api/userManage";
import orderApi from "@/api/orderManage";
export default {
  data() {
    return {
      count1: 0,
      count2: 0,
      count3: 0,
      orderForm: {
        ownerName: this.$store.getters.name,
        renterName: this.$store.getters.name,
      },
      userForm: {
        id: this.$store.getters.id,
        balance: 0,
      },
    };
  },
  components: {
    CountTo,
  },
  methods: {
    // 获取租借与出租的订单数量
    getOrderCount(id, username) {
      if (id == 1) {
        orderApi.getCountByUserName(username).then((response) => {
          console.log(response.data);
          this.count1 = response.data;
          this.count3 += this.count1; 
        });
      } else if (id == 2) {
        orderApi.getCountByRenterName(username).then((response) => {
          this.count2 = response.data;
          this.count3 += this.count2;
        });
      } else {
        return null;
      }
    },
    handleSetLineChartData(type) {
      this.$emit("handleSetLineChartData", type);
    },
    getUserById(id) {
      userApi.getUserById(id).then((response) => {
        this.userForm = response.data;
        console.log(response);
      });
    },
  },
  created() {
    this.getUserById(this.userForm.id);
    this.getOrderCount(1, this.orderForm.ownerName);
    this.getOrderCount(2, this.orderForm.renterName);
  },
};
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3;
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
