# 邻里租租

> 这是一个基于springboot+vue开发的的物品租借平台，旨在服务邻里间的物品租赁，使大家的邻里资源能够得到合理运用。

功能模块:
1. 用户管理模块
2. 物品管理模块
3. 订单管理模块
4. 后台管理模块


# 进入项目目录
cd vue-admin-template

# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装以来，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 启动服务
npm run dev
```

浏览器访问 [http://localhost:8888](http://localhost:8888)

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```

## 其它

```bash
# 预览发布环境效果
npm run preview

# 预览发布环境效果 + 静态资源分析
npm run preview -- --report

# 代码格式检查
npm run lint

# 代码格式检查并自动修复
npm run lint -- --fix
```

