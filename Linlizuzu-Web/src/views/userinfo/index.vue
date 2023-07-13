<template>
  <div>
    <el-card
      style="
        width: 80%;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      "
    >
      <el-form :model="userForm" ref="userFormRef" style="width: 50%">
        <el-form-item
          label="用户名"
          :label-width="formLabelWidth"
          prop="username"
        >
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="用户密码"
          :label-width="formLabelWidth"
          prop="password"
        >
          <el-button size="small" type="primary" @click="openPassWord"
            >修改密码</el-button
          >
        </el-form-item>
        <el-form-item
          label="联系电话"
          :label-width="formLabelWidth"
          prop="phone"
        >
          <el-input
            v-model="userForm.phone"
            autocomplete="off"
            oninput="value=value.replace(/[^\d]/g,'');if(value.length > 11)value = value.slice(0, 11)"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="电子邮件"
          :label-width="formLabelWidth"
          prop="email"
        >
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="修改个人头像" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:9999/zone/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <el-button size="small" type="primary" @click="SaveUserInfo"
            >保存</el-button
          >
        </el-form-item>
      </el-form>
    </el-card>
    <el-dialog
      @close="clearForm"
      title="修 改 密 码"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="passwordparams" ref="passwordparamsRef" :rules="rules">
        <el-form-item
          label="原密码"
          :label-width="formLabelWidth"
          prop="oldPassword"
        >
          <el-input
            v-model="passwordparams.oldPassword"
            autocomplete="off"
            :type="passwordType1"
          ></el-input>
          <span class="show-pwd" @click="showPwd(1)">
            <svg-icon
              :icon-class="passwordType1 === 'password' ? 'eye' : 'eye-open'"
            />
          </span>
        </el-form-item>
        <el-form-item
          label="确认原密码"
          :label-width="formLabelWidth"
          prop="surePassword"
        >
          <el-input
            v-model="passwordparams.surePassword"
            autocomplete="off"
            :type="passwordType2"
          ></el-input>
          <span class="show-pwd" @click="showPwd(2)">
            <svg-icon
              :icon-class="passwordType2 === 'password' ? 'eye' : 'eye-open'"
            />
          </span>
        </el-form-item>
        <el-form-item
          label="新密码"
          :label-width="formLabelWidth"
          prop="newPassword"
        >
          <el-input
            v-model="passwordparams.newPassword"
            autocomplete="off"
            :type="passwordType3"
          ></el-input>
          <span class="show-pwd" @click="showPwd(3)">
            <svg-icon
              :icon-class="passwordType3 === 'password' ? 'eye' : 'eye-open'"
            />
          </span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePassWord">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
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
.show-pwd {
  position: absolute;
  right: 125px;
  top: 3px;
  font-size: 16px;
  cursor: pointer;
  user-select: none;
}
</style>

<script>
import userApi from "@/api/userManage";

export default {
  data() {
    return {
      passwordType1: "password",
      passwordType2: "password",
      passwordType3: "password",
      rules: {},
      passwordparams: {
        oldPassword: "",
        surePassword: "",
        newPassword: "",
      },

      dialogFormVisible: false,
      id: this.$store.getters.id,
      formLabelWidth: "130px",
      imageUrl: "",
      backimageUrl: "",
      fileList: [],
      userForm: {},
    };
  },
  methods: {
    showPwd(id) {
      if (id == 1) {
        if (this.passwordType1 === "password") {
          this.passwordType1 = "";
        } else {
          this.passwordType1 = "password";
        }
        this.$nextTick(() => {
          this.$refs.password.focus();
        });
      } else if (id == 2) {
        if (this.passwordType2 === "password") {
          this.passwordType2 = "";
        } else {
          this.passwordType2 = "password";
        }
        this.$nextTick(() => {
          this.$refs.password.focus();
        });
      } else {
        if (this.passwordType3 === "password") {
          this.passwordType3 = "";
        } else {
          this.passwordType3 = "password";
        }
        this.$nextTick(() => {
          this.$refs.password.focus();
        });
      }
    },
    async logout() {
      await this.$store.dispatch("user/logout");
      // 注销时删除所有tagview
      await this.$store.dispatch("tagsView/delAllViews");
      sessionStorage.removeItem("tabViews");

      this.$router.push(`/login`);
    },
    clearForm() {
      // 清除错误矫正
      this.$refs.userFormRef.clearValidate();
    },
    getUserById(id) {
      userApi.getUserById(id).then((response) => {
        this.userForm = response.data;
        this.imageUrl = response.data.avatar;
      });
    },
    openPassWord() {
      this.dialogFormVisible = true;
    },
    savePassWord() {
      let params = {
        userName: this.$store.getters.name,
        passwordNow: this.userForm.password,
        passwordNew: this.passwordparams.newPassword,
        passwordSure: this.passwordparams.surePassword,
        passwordOld: this.passwordparams.oldPassword,
      };
      console.log(params);
      userApi.updatePassword(params).then((response) => {
        this.$message({
          message: response.message,
          type: "success",
        });
        this.logout();
      });
    },
    SaveUserInfo() {
      if (this.backimageUrl == "") {
        this.userForm.avatar = this.imageUrl;
      } else {
        this.userForm.avatar = this.backimageUrl;
      }
      userApi.updateAvatar(this.userForm).then((response) => {
        this.$message({
          message: "修改个人信息成功",
          type: "success",
        });
        this.$router.go(0);
      });
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.backimageUrl = file.response;
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
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
  },
  created() {
    this.getUserById(this.id);
  },
};
</script>