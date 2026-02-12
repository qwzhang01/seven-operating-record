# 贡献指南

感谢您对Seven Operating Record项目的关注！我们欢迎任何形式的贡献，包括但不限于代码提交、文档改进、问题报告等。

## 开始贡献

### 报告问题

如果您发现了bug或有功能建议，请先检查是否已有相关Issue：

1. 在[Issues页面](https://github.com/qwzhang01/seven-operating-record/issues)搜索相关问题
2. 如果不存在相关Issue，请创建新的Issue并包含以下信息：
   - 清晰的问题描述
   - 重现步骤
   - 期望的行为
   - 实际的行为
   - 相关日志或截图

### 代码贡献

#### 开发环境设置

1. 克隆项目：
```bash
git clone https://github.com/qwzhang01/seven-operating-record.git
cd seven-operating-record
```

2. 确保使用Java 17+和Maven 3.6+

3. 导入到IDE（推荐IntelliJ IDEA或Eclipse）

#### 开发流程

1. Fork项目到您的GitHub账户
2. 创建功能分支：
```bash
git checkout -b feature/your-feature-name
```

3. 进行代码修改并确保通过测试：
```bash
mvn clean test
```

4. 提交代码并推送到您的分支：
```bash
git add .
git commit -m "feat: 添加新功能描述"
git push origin feature/your-feature-name
```

5. 创建Pull Request到主仓库的main分支

### 提交规范

我们遵循[约定式提交](https://www.conventionalcommits.org/zh-hans/)规范：

- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式调整（不影响功能）
- `refactor`: 代码重构
- `test`: 测试相关
- `chore`: 构建过程或辅助工具变动

示例：
```
feat: 新增自定义策略支持
fix: 修复参数提取的NPE问题
docs: 更新使用示例文档
```

## 代码规范

### Java代码规范

- 遵循Google Java Style Guide
- 使用4个空格缩进
- 类和方法必须有Javadoc注释
- 变量和方法名使用驼峰命名法
- 常量使用全大写加下划线

### 测试要求

- 新功能必须包含单元测试
- 测试覆盖率不低于80%
- 使用JUnit 5进行测试
- 测试类名以`Test`结尾

### 文档要求

- 公共API必须有完整的Javadoc
- 新增功能需要更新README.md
- 重大变更需要更新CHANGELOG.md

## 审查流程

1. Pull Request创建后会自动触发CI检查
2. 项目维护者会进行代码审查
3. 可能需要根据反馈进行修改
4. 通过审查后会被合并到main分支

## 行为准则

我们遵循[贡献者公约](https://www.contributor-covenant.org/version/2/0/code_of_conduct/)，请确保：

- 使用友好和尊重的语言
- 尊重不同的观点和经验
- 建设性地接受批评
- 关注社区的整体利益

## 联系方式

如有问题可以通过以下方式联系：

- GitHub Issues: [项目Issues页面](https://github.com/qwzhang01/seven-operating-record/issues)
- 邮箱: avinzhang@tencent.com

再次感谢您的贡献！