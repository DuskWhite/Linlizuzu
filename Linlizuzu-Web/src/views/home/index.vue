<template>
  <div class="dashboard-editor-container">
    <!-- 卡片四连 使用panel-group组件实现 -->
    <panel-group />
    <el-button @click="open" type="text" style="width: 100%">
      <div
        style="
          background-image: url('http://localhost:9999/uploadFile/welcome.jpg');
          background-size: cover;
        "
      >
        <div style="height: 200px">
          <span style="position:relative;float:left;color: #ffffff; font-size: 16px; margin: 20px">欢迎来到邻里租租！</span>
        </div>
      </div>
    </el-button>

  </div>
</template>

<script>
import PanelGroup from "./components/PanelGroup";
import LineChart from "./components/LineChart";
import RaddarChart from "./components/RaddarChart";
import PieChart from "./components/PieChart";
import BarChart from "./components/BarChart";
import TransactionTable from "./components/TransactionTable";
import TodoList from "./components/TodoList";
import BoxCard from "./components/BoxCard";

export default {
  name: "DashboardAdmin",
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart,
    TransactionTable,
    TodoList,
    BoxCard,
  },
  data() {
    return {
      
    };
  },
  methods: {
    open() {
      this.$alert("欢迎使用本租赁平台，本平台旨在为广大用户创建一个便捷，安全的闲置物品交易平台", "公告", {
        confirmButtonText: "确定",
        callback: (action) => {
        },
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
