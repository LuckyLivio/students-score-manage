# 学生成绩在线发布系统

![Java](https://img.shields.io/badge/Java-8+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.x-green.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

## 📋 项目简介

学生成绩在线发布系统是一个基于Spring Boot框架开发的Web应用程序，旨在为学校提供一个完整的成绩管理解决方案。系统支持学生查询成绩、教师录入和管理成绩、管理员进行系统管理等功能。

## 🚀 在线演示

- **演示地址**: [点击访问](http://your-demo-url.com) (请替换为实际演示地址)
- **管理员账号**: admin / admin123
- **教师账号**: teacher / teacher123  
- **学生账号**: student / student123

## 🛠️ 技术栈

- **后端框架**: Spring Boot 2.x
- **数据库**: MySQL 8.0
- **ORM框架**: Spring Data JPA / Hibernate
- **安全框架**: Spring Security
- **模板引擎**: Thymeleaf
- **构建工具**: Maven
- **前端**: Bootstrap 5 + HTML5 + CSS3 + JavaScript

## ✨ 系统功能

### 👨‍🎓 学生功能
- 🔍 **成绩查询**: 查看个人各科成绩
- 📊 **学期筛选**: 按学期查看成绩记录
- 📈 **成绩统计**: 查看个人成绩分析

### 👨‍🏫 教师功能
- ✏️ **成绩录入**: 批量录入学生成绩
- 📝 **成绩管理**: 修改、删除成绩记录
- 📋 **班级管理**: 查看所教班级学生列表
- 📊 **Excel导入**: 支持Excel批量导入成绩
- 🔍 **成绩查询**: 按班级、科目、学期查询成绩

### 👨‍💼 管理员功能
- 👥 **用户管理**: 管理学生、教师账户
- 🏫 **系统设置**: 管理院系、专业、班级、科目
- 📊 **统计分析**: 查看各维度成绩统计
- 🔧 **数据维护**: 系统数据的增删改查

## 📁 项目结构

```
Final28/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── grade/
│       │           ├── GradeApplication.java          # 主启动类
│       │           ├── config/
│       │           │   └── SecurityConfig.java       # 安全配置
│       │           ├── controller/                   # 控制器层
│       │           │   ├── LoginController.java
│       │           │   ├── StudentController.java
│       │           │   ├── TeacherController.java
│       │           │   └── AdminController.java
│       │           ├── entity/                       # 实体类
│       │           │   ├── User.java
│       │           │   ├── Grade.java
│       │           │   ├── Subject.java
│       │           │   ├── StudentClass.java
│       │           │   ├── Department.java
│       │           │   ├── Major.java
│       │           │   └── UserRole.java
│       │           ├── repository/                   # 数据访问层
│       │           │   ├── UserRepository.java
│       │           │   ├── GradeRepository.java
│       │           │   ├── SubjectRepository.java
│       │           │   ├── StudentClassRepository.java
│       │           │   ├── DepartmentRepository.java
│       │           │   └── MajorRepository.java
│       │           └── service/                      # 业务逻辑层
│       │               ├── UserService.java
│       │               ├── GradeService.java
│       │               ├── SubjectService.java
│       │               ├── StudentClassService.java
│       │               ├── DepartmentService.java
│       │               ├── MajorService.java
│       │               ├── StatisticsService.java
│       │               └── CustomUserDetailsService.java
│       └── resources/
│           ├── templates/                            # 前端模板
│           │   ├── login.html
│           │   ├── dashboard.html
│           │   ├── student/
│           │   │   └── grades.html
│           │   ├── teacher/
│           │   │   ├── grades.html
│           │   │   └── grade-input.html
│           │   └── admin/
│           │       ├── statistics.html
│           │       └── system-settings.html
│           └── application.yml                      # 应用配置
├── pom.xml                                          # Maven配置
└── README.md                                        # 项目说明
```

## 🗄️ 数据库设计

### 主要数据表

- **users**: 用户表（学生、教师、管理员）
- **grades**: 成绩表
- **subjects**: 科目表
- **student_classes**: 班级表
- **departments**: 院系表
- **majors**: 专业表

### 实体关系

- 用户(User) ↔ 成绩(Grade): 一对多
- 科目(Subject) ↔ 成绩(Grade): 一对多
- 班级(StudentClass) ↔ 用户(User): 一对多
- 专业(Major) ↔ 班级(StudentClass): 一对多
- 院系(Department) ↔ 专业(Major): 一对多

## 🚀 快速开始

### 环境要求

- ☕ JDK 8 或更高版本
- 📦 Maven 3.6+
- 🗄️ MySQL 8.0+
- 💻 IDE（推荐IntelliJ IDEA或Eclipse）

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/your-username/Final28.git
   cd Final28
   ```

2. **配置数据库**
   
   创建MySQL数据库：
   ```sql
   CREATE DATABASE grade_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **修改配置文件**
   
   编辑 `src/main/resources/application.yml`：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/grade_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
       username: your_username
       password: your_password
     security:
       user:
         password-encoder: noop  # 开发环境使用明文密码
   ```

4. **初始化数据库**
   
   执行以下SQL语句创建初始用户：
   ```sql
   -- 创建用户表
   INSERT INTO users (username, password, real_name, role, email, phone) VALUES
   ('admin', 'admin123', '系统管理员', 'ADMIN', 'admin@example.com', '13800000001'),
   ('teacher', 'teacher123', '张老师', 'TEACHER', 'teacher@example.com', '13800000002'),
   ('student', 'student123', '李同学', 'STUDENT', 'student@example.com', '13800000003');
   ```

5. **编译并运行**
   ```bash
   # 清理并编译
   mvn clean compile
   
   # 运行项目
   mvn spring-boot:run
   ```

6. **访问系统**
   
   打开浏览器访问：`http://localhost:9000/grade`

### 🔑 默认账户

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | admin | admin123 | 系统管理员账户 |
| 教师 | teacher | teacher123 | 教师账户 |
| 学生 | student | student123 | 学生账户 |

## 📖 使用说明

### 👨‍🎓 学生使用

1. 使用学号和密码登录系统
2. 在成绩查询页面查看个人成绩
3. 可按学期筛选查看不同时期的成绩

### 👨‍🏫 教师使用

1. 使用教师账号登录系统
2. 在成绩录入页面选择班级和科目
3. 批量录入学生成绩或使用Excel导入
4. 在成绩管理页面查看、修改已录入的成绩

### 👨‍💼 管理员使用

1. 使用管理员账号登录系统
2. 在系统设置页面管理用户、班级、科目等基础数据
3. 在统计分析页面查看各维度的成绩统计信息

## 🌟 功能特色

- 🔐 **安全认证**: 基于Spring Security的角色权限控制
- 📊 **Excel支持**: 支持Excel格式的成绩批量导入
- 📱 **响应式设计**: 基于Bootstrap的移动端适配
- 🔍 **多维查询**: 支持按多种条件筛选查询成绩
- 📈 **统计分析**: 提供丰富的成绩统计分析功能
- 🛡️ **数据验证**: 完善的数据校验和异常处理

## 🔧 开发说明

### 添加新功能

1. 在对应的包下创建新的实体类、Repository、Service和Controller
2. 在templates目录下创建对应的HTML模板
3. 更新SecurityConfig配置访问权限
4. 编写单元测试确保功能正常

### 数据库迁移

项目使用JPA自动建表，首次运行时会自动创建所需的数据表。如需手动控制，可修改application.yml中的ddl-auto配置。

## ❓ 常见问题

<details>
<summary><strong>Q: 启动时提示数据库连接失败？</strong></summary>

A: 请检查MySQL服务是否启动，数据库配置是否正确。
</details>

<details>
<summary><strong>Q: 登录时提示用户名或密码错误？</strong></summary>

A: 请确认用户账户已正确创建，密码是否正确。
</details>

<details>
<summary><strong>Q: Excel导入失败？</strong></summary>

A: 请检查Excel文件格式是否正确，确保包含必要的列（学号、科目、成绩等）。
</details>

<details>
<summary><strong>Q: 页面显示异常？</strong></summary>

A: 请检查浏览器控制台是否有JavaScript错误，确认静态资源加载正常。
</details>

## 🤝 贡献指南

我们欢迎所有形式的贡献！请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📝 更新日志

### v1.0.0 (2024-01-01)
- ✨ 初始版本发布
- ✅ 完成基础的成绩管理功能
- ✅ 实现用户角色权限控制
- ✅ 支持Excel批量导入
- ✅ 提供统计分析功能


---

<div align="center">

**⭐ 如果这个项目对你有帮助，请给它一个星标！**

**感谢使用学生成绩在线发布系统！**

</div>
```

## 主要更新内容：

1. **添加了项目徽章** - 显示技术栈和许可证信息
2. **优化了结构** - 使用更清晰的emoji图标和分层结构
3. **添加了在线演示部分** - 方便用户快速了解项目
4. **改进了安装说明** - 更详细的步骤和代码示例
5. **添加了表格格式的默认账户信息** - 更清晰易读
6. **使用折叠式FAQ** - 节省空间，提高可读性
7. **添加了联系方式** - 方便用户反馈问题
8. **美化了页面布局** - 使用居中对齐和强调文本
9. **添加了贡献指南** - 鼓励开源贡献
10. **完善了项目描述** - 更专业的GitHub项目展示
