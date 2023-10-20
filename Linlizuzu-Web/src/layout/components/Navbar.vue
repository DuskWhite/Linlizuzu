<template>
  <div class="navbar">
    <hamburger
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />

    <breadcrumb class="breadcrumb-container" />

    <el-popover ref="popover" placement="top-start" width="350" trigger="hover">
      <el-card
        class="box-card-component"
        style="margin-left: 8px"
        :body-style="{ padding: '10px' }"
      >
        <div slot="header" class="box-card-header">
          <img :src="bgurl" />
        </div>
        <div style="position: relative">
          <img :src="avatarurl" class="panThumb" />
          <div style="padding-top: 0px; padding-left: 80px; font-size: 30px">
            {{ this.$store.getters.name }}
          </div>
        </div>
        <el-divider></el-divider>
        <div style="position: relative; background: #ffffff">
          <div style="font-size: 15px; text-align: center; margin-bottom: 10px">
            <span>余额</span>
            <span style="color: #eea20e; font-size: 20px"
              >￥{{ this.balance }}</span
            >
            <span>
              <el-button
                type="text"
                size="mini"
                style="margin-left: 40px;font-size:14px"
                @click="$router.push('/zone/wallet')"
                >充值</el-button
              >
            </span>
          </div>
        </div>
        <router-link to="/zone/userinfo">
          <el-dropdown-item>
            <span>个人中心</span>
            <svg-icon
              icon-class="jiantouyou"
              style="float: right; margin-top: 12px"
            />
          </el-dropdown-item>
        </router-link>
        <router-link to="/rent/order">
          <el-dropdown-item
            ><span>订单管理</span>
            <svg-icon
              icon-class="jiantouyou"
              style="float: right; margin-top: 12px"
            />
          </el-dropdown-item>
        </router-link>
        <el-dropdown-item divided @click.native="logout">
          <span>退出登录</span>
          <svg-icon icon-class="退出" style="float: right; margin-top: 11px" />
        </el-dropdown-item>
      </el-card>
    </el-popover>
    <div class="right-menu">
      <div class="avatar-container" @click="$router.push('/zone/userinfo')">
        <div class="avatar-wrapper">
          <img
            :src="avatarurl + '?imageView2/1/w/80/h/80'"
            class="user-avatar"
            @error="setDefaultImage"
            v-popover:popover
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import userApi from "@/api/userManage";
export default {
  data() {
    return {
      bgurl: "http://localhost:9999/api/uploadFile/infobg.jpg",
      avatarurl: "",
      balance: 0,
      id: this.$store.getters.id,
    };
  },
  components: {
    Breadcrumb,
    Hamburger,
  },

  computed: {
    ...mapGetters([
      "sidebar",
      // 'avatar'
    ]),
  },
  methods: {
    setDefaultImage(e) {
      e.target.src = "http://localhost:9999/api/uploadFile/avatar.jpg";
    },
    getUserById(id) {
      userApi.getUserById(id).then((response) => {
        this.avatarurl = response.data.avatar;
        this.balance = response.data.balance;
      });
    },
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      await this.$store.dispatch("user/logout");
      // 注销时删除所有tagview
      await this.$store.dispatch("tagsView/delAllViews");
      sessionStorage.removeItem("tabViews");

      this.$router.push(`/login`);
    },
  },
  created() {
    this.getUserById(this.id);
  },
};
</script>

<style lang="scss" scoped>
.box-card-component {
  .el-card__header {
    padding: 0px !important;
  }
}
</style>
<style lang="scss" scoped>
.box-card-component {
  .box-card-header {
    position: relative;
    height: 220px;
    img {
      width: 100%;
      height: 100%;
      transition: all 0.2s linear;
      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(130%);
      }
    }
  }
  .mallki-text {
    position: absolute;
    top: 0px;
    right: 0px;
    font-size: 20px;
    font-weight: bold;
  }
  .panThumb {
    z-index: 100;
    height: 70px !important;
    width: 70px !important;
    position: absolute !important;
    top: -45px;
    left: 0px;
    border: 5px solid #ffffff;
    background-color: #fff;
    margin: auto;
    box-shadow: none !important;
    ::v-deep .pan-info {
      box-shadow: none !important;
    }
  }
  .progress-item {
    margin-bottom: 10px;
    font-size: 14px;
  }
  @media only screen and (max-width: 1510px) {
    .mallki-text {
      display: none;
    }
  }
}
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
