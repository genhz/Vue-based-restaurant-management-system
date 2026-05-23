# 荣成餐饮店管理系统

基于 Vue 3 + Spring Boot 的全栈餐饮管理系统，支持后台管理、前台收银和客户自助点餐。

## 功能模块

| 模块 | 功能 | 角色 |
|------|------|------|
| **后台管理** | 数据汇总、菜品管理、员工管理、考勤管理、营业统计、库存管理 | admin |
| **前台收银** | 订单管理、未完成订单、历史订单、营业汇总 | counter |
| **采购管理** | 采购计划、入库管理、库存管理、历史采购、数据汇总 | procure |
| **客户点餐** | 菜单浏览、分类筛选、购物车、在线下单、订单查看 | user |

## 技术栈

### 前端
- **Vue 3** (Composition API + `<script setup>`)
- **Vue Router** (Hash 模式)
- **Vuex** (状态管理)
- **Vite** (构建工具)
- **Vant 4** (移动端 UI - 客户点餐页面)
- **Element Plus** + **Layui Vue** (桌面端 UI - 后台管理页面)
- **ECharts 5** (数据可视化)
- **Axios** (HTTP 请求)

### 后端
- **Spring Boot 2.7**
- **Spring Data JPA** (ORM)
- **SQLite** (嵌入式数据库，零配置)
- **Token 认证** (拦截器验证)

## 项目结构

```
restaurant-management-system/
├── start.sh                 # 一键启动脚本
├── README.md
├── frontend/                # Vue 3 前端项目
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   ├── public/
│   │   └── img/             # 静态图片资源
│   │       ├── dishes/      # 菜品图片
│   │       └── lunbo/       # 轮播图
│   └── src/
│       ├── main.js          # 入口文件
│       ├── App.vue
│       ├── router/          # 路由配置
│       ├── store/           # Vuex 状态管理
│       ├── utils/           # 工具函数
│       │   ├── axios.ts     # HTTP 封装
│       │   ├── auth.js      # 认证工具
│       │   └── service/     # API 服务层
│       ├── components/      # 公共组件
│       └── views/           # 页面组件
│           ├── login.vue
│           ├── admin/       # 后台管理页面
│           ├── procure/     # 采购管理页面
│           └── reception/   # 前台 & 客户点餐页面
│               ├── counter/ # 前台管理
│               └── menu/    # 客户点餐
└── backend/                 # Spring Boot 后端项目
    ├── pom.xml
    └── src/main/
        ├── resources/
        │   └── application.properties
        └── java/com/restaurant/
            ├── RestaurantApplication.java
            ├── config/      # 配置 (CORS, Auth, 数据初始化)
            ├── entity/      # 数据实体
            ├── repository/  # 数据访问层
            ├── service/     # 业务逻辑层
            └── controller/  # REST API 控制器
```

## 快速开始

### 环境要求

- **JDK 8+**
- **Maven 3.6+**
- **Node.js 16+**
- **npm 8+**

### 一键启动

```bash
chmod +x start.sh
./start.sh
```

启动后访问：
- 前端页面: http://localhost:5173
- 后端接口: http://localhost:8080

### 分别启动

**后端：**
```bash
cd backend
mvn spring-boot:run
# 服务运行在 http://localhost:8080
# SQLite 数据库自动创建 (restaurant.db)
```

**前端：**
```bash
cd frontend
npm install
npx vite
# 开发服务器运行在 http://localhost:5173
```

### 演示账号

| 角色 | 用户名 | 密码 | 权限 |
|------|--------|------|------|
| 管理员 | `admin` | `admin` | 后台管理全部功能 |
| 前台 | `counter` | `counter` | 订单管理、收银 |
| 采购 | `procure` | `procure` | 库存、采购管理 |
| 服务员 | `waiter` | `waiter` | 客户点餐 |

数据库首次运行时自动初始化 8 个菜品、8 种食材和 4 个演示员工。

## API 接口

所有接口返回 JSON 格式数据。除 `/login` 和 `/addDishes` 外，所有接口需要在请求头携带 `token` 字段。

### 认证接口

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/login` | 用户登录，请求体 `{username, password}` |

### 人事管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | `/getAllPersonnel` | 获取所有员工 |
| POST | `/getPersonnelById` | 根据 ID 获取员工 |
| POST | `/addPersonnel` | 添加员工 |
| POST | `/editPersonnel` | 编辑员工信息 |
| POST | `/removePersonnel` | 删除员工 |
| POST | `/searchPersonnelName` | 按姓名搜索 |
| POST | `/searchPersonnelType` | 按职位搜索 |
| POST | `/Attendance` | 考勤签到 |

### 菜品管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | `/getAllDishes` | 获取所有菜品 |
| POST | `/getDishesById` | 根据 ID 获取菜品 (含食材列表) |
| POST | `/addDishes` | 添加菜品 (支持图片上传) |
| POST | `/editDishes` | 编辑菜品 |
| POST | `/removeDishes` | 删除菜品 |
| POST | `/updateDishesState` | 更新菜品状态 (上架/下架) |
| POST | `/searchDishesName` | 按菜名搜索 |
| POST | `/searchDishesType` | 按品类搜索 |

### 订单管理

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/addOrder` | 创建订单 |
| POST | `/addMenuOrder` | 客户自助下单 |
| GET | `/getAllOrder` | 获取所有订单 |
| GET | `/getfinOrder` | 获取已完成订单 |
| GET | `/getunOrder` | 获取未完成订单 |
| POST | `/getOrderById` | 根据 ID 获取订单 |
| POST | `/editOrder` | 编辑订单 |
| POST | `/deleteOrder` | 删除订单 |
| POST | `/updateOrderStatus` | 更新订单状态 |
| POST | `/getOrderByDesk` | 根据桌号获取订单 |

### 食材/库存管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | `/getAllMaterial` | 获取所有食材 |
| POST | `/addMaterial` | 添加食材 |
| POST | `/editMaterial` | 编辑食材 |
| POST | `/removeMaterial` | 删除食材 |
| GET | `/getPlanning` | 获取采购计划 (低于安全库存) |
| POST | `/commitWarehousing` | 提交入库 |
| GET | `/getProcurelist` | 历史采购汇总 |
| POST | `/getProcurelistmonth` | 按月份查询采购明细 |

### 财务统计

| 方法 | URL | 说明 |
|------|-----|------|
| GET | `/getAllPay` | 支出汇总 |
| GET | `/getAllProfit` | 收入汇总 |
| POST | `/getProfitId` | 收入订单明细 |
| POST | `/getPayIdprocure` | 支出采购明细 |
| POST | `/getPayIdsalary` | 支出薪资明细 |

### 数据看板

| 方法 | URL | 说明 |
|------|-----|------|
| GET | `/getWeekIncome` | 近 7 天收入 |
| GET | `/getWeekOrder` | 近 7 天订单量 |
| GET | `/getInfor` | 今日/本周概览 |
| GET | `/getMonthprofit` | 近 6 月收入 |
| GET | `/getMonthpay` | 近 6 月支出 |
| GET | `/getmonthall` | 本月汇总 |
| GET | `/getMateialNamePidservice` | 食材库存饼图 |
| GET | `/getMateialSuppliedPidservice` | 供应商统计饼图 |

## 配置说明

### 后端配置 (`backend/src/main/resources/application.properties`)

```properties
server.port=8080                # 服务端口
spring.datasource.url=jdbc:sqlite:restaurant.db  # SQLite 数据库文件
spring.jpa.hibernate.ddl-auto=update             # 自动建表
app.upload-dir=uploads          # 图片上传目录
```

### 前端配置 (`frontend/vite.config.js`)

开发环境下 API 请求通过 Vite 代理转发到后端：

```js
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080/',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, '')
    }
  }
}
```

## License

MIT
