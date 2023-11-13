<template>
    <div class="navigation_bar">
        <div class="navigation_title">
            <el-row>
                <el-col :span="1" :offset="2">
                    <el-icon color="rgb(38, 203, 193)">
                        <LocationFilled />
                    </el-icon>
                    <span>&nbsp;杭州</span>
                </el-col>
                <el-col :span="3"><span>您好！欢迎来到邻里租租</span></el-col>
                <el-col :span="1">
                    <div class="header-line"></div>
                </el-col>
                <el-col :span="2"><span>服务中心</span></el-col>
                <el-col :span="1">
                    <div class="header-line"></div>
                </el-col>
                <el-col :span="4"><span style="color:rgba(255,255,255,0.65);">服务热线: 0111-5468-4586</span></el-col>
                <el-col :span="4" :offset="6">
                    <div>
                        <span>注册&nbsp;&nbsp;</span>
                        <div class="header-line"></div>
                        <span style="color: #00c1b6;">&nbsp;&nbsp;您好,请登录</span>
                    </div>
                </el-col>

            </el-row>
        </div>
        <div class="navigation_search">
            <el-row style="height: 100%;">
                <el-col :span="3">
                    <h1 class="logo">邻里租租</h1>
                </el-col>
                <el-col :span="6" :offset="14">
                    <el-input v-model="input3" placeholder="笔记本" class="searchInput">
                        <template #append>
                            <el-button icon="Search" />
                        </template>
                    </el-input>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script setup>

</script>

<style scope>
.navigation_bar {
    position: absolute;
    background-color: black;
    width: 100%;
    top: 0;
    height: 36px;
    left: 0;
    color: white;
    font-size: small;
}

.navigation_title {
    position: relative;
    top: 5px;
}

.header-line {
    display: inline-block;
    vertical-align: top;
    width: 1px;
    height: 16px;
    background-color: rgba(255, 255, 255, 0.25);
    margin: 5px 0;
}

.navigation_search {
    background-color: rgb(255, 255, 255);
    height: 98px;
    color: #000;
}

.logo {
    position: relative;
    top: 25%;
}
.searchInput{
    position: relative;
    top: 25%;
    transform: translateY(25%);
}
</style>