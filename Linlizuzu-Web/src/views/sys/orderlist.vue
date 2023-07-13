<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input v-model="searchModel.orderNo" placeholder="订单编号" clearable />
          <el-button type="primary" round icon="el-icon-search" @click="getorderList">查询</el-button>
        </el-col>
        <el-col :span="4" align="right">
          <el-button type="primary" icon="el-icon-circle-plus" circle @click="openEditUi(null)" />
        </el-col>
      </el-row>
    </el-card>

    <!-- 查询结果栏 -->
    <el-card>
      <template>
        <el-table
          :data="orderList"
          style="width: 100%"
          height="450"
        >
          <el-table-column type="index" label="序号" width="50">
            <!-- 计算序列号 -->
            <template slot-scope="scope">
              {{ (searchModel.pageNo-1) * searchModel.pageSize + scope.$index +1 }}
            </template>
          </el-table-column>
          <el-table-column prop="orderId" label="订单ID" width="80" />
          <el-table-column prop="orderNo" label="订单编号" width="120" />
          <el-table-column prop="ownerName" label="出租人" width="100" />
          <el-table-column prop="renterName" label="租借人" width="100" />
          <el-table-column prop="goodName" label="租借物品" width="140" />
          <el-table-column prop="rentTime" label="租借起始时间" width="180" />

          <el-table-column prop="overTime" label="租借结束时间" width="180" />
          <el-table-column prop="orderStatus" label="订单状态" width="130" >
            <template slot-scope="scope">
              <el-tag v-if="scope.row.orderStatus == 1">租借中</el-tag>
              <el-tag v-else-if="scope.row.orderStatus == 2" type="warning"
                >正在归还</el-tag
              >
              <el-tag v-else-if="scope.row.orderStatus == 3" type="danger"
                >订单超时未归还</el-tag
              >
              <el-tag v-else type="success">订单已完成</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="openEditUi(scope.row.orderId)" type="primary" icon="el-icon-edit" circle size="mini"></el-button>
              <el-button @click="deleteorder(scope.row)" type="danger" icon="el-icon-delete" circle size="mini"></el-button>
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
      :total="total">
    </el-pagination>

    <!-- 商品信息编辑对话框 -->
    <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="orderForm" ref="orderFormRef" :rules="rules">
        <el-form-item label="订单编号" :label-width="formLabelWidth" prop="orderNo">
          <el-input v-model="orderForm.orderNo" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出租人" :label-width="formLabelWidth" prop="ownerName">
          <el-input v-model="orderForm.ownerName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="租借人" :label-width="formLabelWidth" prop="renterName">
          <el-input v-model="orderForm.renterName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="租借物品" :label-width="formLabelWidth" prop="goodName">
          <el-input v-model="orderForm.goodName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品状态" :label-width="formLabelWidth" prop="orderStatus">
          <el-select v-model="orderForm.orderStatus" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="租借时间" :label-width="formLabelWidth" prop="rentTime">
            <el-date-picker
              v-model="orderForm.rentTime"
              type="datetime"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              align="right"
              :picker-options="pickerOptions">
            </el-date-picker>
        </el-form-item>
        <el-form-item label="终止时间" :label-width="formLabelWidth" prop="overTime">
          <el-date-picker
            v-model="orderForm.overTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间"
            align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveorder">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import orderApi from '@/api/orderManage'

export default {
  data() {
    return {
      currentPage: 1,
      formLabelWidth: '130px',
      orderForm: {},
      dialogFormVisible: false,
      total: 0,
      title: '',
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      orderList: [],
      rules: {

      },
      options: [{
          value: 1,
          label: '正在出租'
        }, {
          value: 2,
          label: '订单完成'
        }, {
          value: 3,
          label: '下架'
        }],
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      value: ''
    }
  },
  methods: {
    deleteorder(order) {
      this.$confirm(`您确认删除商品 ${order.goodName} ?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        orderApi.deleteorderById(order.orderId).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          });
          this.getorderList()
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
          });          
      });
    },
    saveorder() {
      // 触发表单验证
      this.$refs.orderFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          orderApi.saveorder(this.orderForm).then(response => {
              this.$message({
              message: response.message,
              type: 'success'
            });
            this.dialogFormVisible = false;
            this.getorderList();
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });      
      
    },
    clearForm() {
      this.orderForm = {}
      // 清除错误矫正
      this.$refs.orderFormRef.clearValidate()
    },
    openEditUi(id) {
      if(id ==null){
        this.title = '新增商品'
      }else{
        this.title = '修改订单信息'
        orderApi.getorderById(id).then(response =>{
          this.orderForm = response.data
        })
      }
      
      this.dialogFormVisible = true
    },
    // 文件页数刷新
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getorderList()
    },
    // 文件页码刷新
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getorderList()
    },
    getorderList() {
      orderApi.getorderList(this.searchModel).then(response => {
        this.orderList = response.data.rows
        this.total = response.data.total
      })
    }
  },
  created() {
    this.getorderList()
  }
}
</script>

<style>
#search .el-input{
  width: 200px;
  margin-right:10px ;
}
.el-dialog .el-input{
  width: 80%;
}
</style>
