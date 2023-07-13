<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.goodname"
            placeholder="商品名"
            clearable
          />
          <el-input
            v-model="searchModel.username"
            placeholder="商品所有者"
            clearable
          />
          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getGoodList"
            >查询</el-button
          >
        </el-col>
        <el-col :span="4" align="right">
          <el-button
            type="primary"
            icon="el-icon-circle-plus"
            circle
            @click="openEditUi(null)"
          />
        </el-col>
      </el-row>
    </el-card>

    <!-- 查询结果栏 -->
    <el-card>
      <template>
        <el-table :data="goodList" style="width: 100%" height="450">
          <el-table-column type="index" label="序号" width="80">
            <!-- 计算序列号 -->
            <template slot-scope="scope">
              {{
                (searchModel.pageNo - 1) * searchModel.pageSize +
                scope.$index +
                1
              }}
            </template>
          </el-table-column>
          <el-table-column prop="id" label="商品ID" width="180" />
          <el-table-column prop="goodname" label="商品名" width="180" />
          <el-table-column prop="price" label="商品价格" width="180" />
          <el-table-column prop="username" label="商品所有者" width="180" />
          <el-table-column prop="status" label="商品状态" width="180">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status == 1">上架</el-tag>
              <el-tag v-else type="danger">下架</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                @click="openEditUi(scope.row.id)"
                type="primary"
                icon="el-icon-edit"
                circle
                size="mini"
              ></el-button>
              <el-button
                @click="deleteGood(scope.row)"
                type="danger"
                icon="el-icon-delete"
                circle
                size="mini"
              ></el-button>
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

    <!-- 商品信息编辑对话框 -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="goodForm" ref="goodFormRef" :rules="rules">
        <el-form-item
          label="商品名"
          :label-width="formLabelWidth"
          prop="goodname"
        >
          <el-input v-model="goodForm.goodname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="商品价格"
          :label-width="formLabelWidth"
          prop="price"
        >
          <el-input v-model="goodForm.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="商品所有者"
          :label-width="formLabelWidth"
          prop="username"
        >
          <el-input v-model="goodForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="商品状态"
          :label-width="formLabelWidth"
          prop="status"
        >
          <el-switch
            v-model="goodForm.status"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>
        <el-form-item label="商品图片" :label-width="formLabelWidth" prop="img"
          ><el-upload
            class="avatar-uploader"
            action="http://localhost:9999/zone/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savegood">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import goodApi from "@/api/goodManage";

export default {
  data() {
    return {
      backimageUrl: "",
      imageUrl: "",
      currentPage: 1,
      formLabelWidth: "130px",
      goodForm: {},
      dialogFormVisible: false,
      total: 0,
      title: "",
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      goodList: [],
      rules: {
        goodname: [
          { required: true, message: "请输入商品名", trigger: "blur" },
          {
            min: 2,
            max: 40,
            message: "长度在 2 到 40 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = file.response;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    deleteGood(good) {
      this.$confirm(`您确认删除商品 ${good.goodname} ?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          goodApi.deleteGoodById(good.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getGoodList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    savegood() {
      // 触发表单验证
      this.$refs.goodFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          this.goodForm.img = this.imageUrl;
          goodApi.saveGood(this.goodForm).then((response) => {
            this.$message({
              message: response.message,
              type: "success",
            });
            this.dialogFormVisible = false;
            this.getGoodList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    clearForm() {
      this.goodForm = {};
      // 清除错误矫正
      this.$refs.goodFormRef.clearValidate();
    },
    openEditUi(id) {
      if (id == null) {
        this.title = "新增商品";
      } else {
        this.title = "修改商品信息";
        goodApi.getGoodById(id).then((response) => {
          this.goodForm = response.data;
          this.imageUrl = response.data.img;
        });
      }

      this.dialogFormVisible = true;
    },
    // 文件页数刷新
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getGoodList();
    },
    // 文件页码刷新
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getGoodList();
    },
    getGoodList() {
      goodApi.getGoodList(this.searchModel).then((response) => {
        this.goodList = response.data.rows;
        this.total = response.data.total;
      });
    },
  },
  created() {
    this.getGoodList();
  },
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
#search .el-input {
  width: 200px;
  margin-right: 10px;
}
.el-dialog .el-input {
  width: 80%;
}
</style>
