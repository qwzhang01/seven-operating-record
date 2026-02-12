# Seven Operating Record

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.qwzhang01/seven-operating-record.svg)](https://search.maven.org/artifact/io.github.qwzhang01/seven-operating-record)

一个基于Spring Boot AOP的操作记录库，支持自定义记录策略。

## 功能特性

- 🎯 **注解驱动**：通过`@Op`注解轻松标记需要记录操作的方法
- 🔧 **策略可扩展**：支持自定义操作记录策略
- 📊 **前后数据对比**：支持方法执行前后的数据对比记录
- 🚀 **零侵入**：基于AOP实现，对业务代码无侵入
- 📝 **灵活配置**：支持参数提取、返回值处理等多样化配置

## 快速开始

### 添加依赖

```xml
<dependency>
    <groupId>io.github.qwzhang01</groupId>
    <artifactId>seven-operating-record</artifactId>
    <version>1.0.4</version>
</dependency>
```

### 基本使用

1. 在Spring Boot配置类中启用操作记录：

```java
@Configuration
@EnableOpRecord
public class AppConfig {
}
```

2. 在需要记录操作的方法上添加`@Op`注解：

```java
@Service
public class UserService {
    
    @Op(args = User.class)
    public User createUser(User user) {
        // 业务逻辑
        return userService.save(user);
    }
    
    @Op(args = Long.class, strategy = UserUpdateStrategy.class)
    public User updateUser(Long userId, User user) {
        // 业务逻辑
        return userService.update(userId, user);
    }
}
```

## 核心概念

### @Op注解

`@Op`注解是操作记录的核心，支持以下配置：

- `strategy`: 指定操作记录策略类（默认：`DefaultOpStrategy`）
- `args`: 指定要从方法参数中提取的参数类型

### 操作记录策略

通过实现`OpStrategy`接口来自定义操作记录逻辑：

```java
@Component
public class CustomOpStrategy implements OpStrategy {
    
    @Override
    public void record(String className, String methodName, Op op, 
                      Object oldData, Object newData, Object result) {
        // 自定义记录逻辑
        OperationRecord record = new OperationRecord();
        record.setClassName(className);
        record.setMethodName(methodName);
        record.setOldData(oldData);
        record.setNewData(newData);
        record.setResult(result);
        
        // 保存到数据库或发送到消息队列等
        operationRecordService.save(record);
    }
}
```

## 配置说明

### 自动配置

项目基于Spring Boot自动配置，默认情况下会自动启用。如需自定义配置，可以在`application.yml`中配置：

```yaml
op-record:
  enabled: true
  # 其他配置项
```

### 处理器配置

- `BeforeProcessor`: 方法执行前数据捕获处理器
- `AfterProcessor`: 方法执行后操作记录处理器
- `ArgsProcessor`: 方法参数提取处理器

## 进阶使用

### 自定义策略

实现`OpStrategy`接口并注册为Spring Bean即可使用自定义策略：

```java
@Component
public class AuditOpStrategy implements OpStrategy {
    // 实现审计特定的记录逻辑
}

// 使用自定义策略
@Op(strategy = AuditOpStrategy.class, args = AuditData.class)
public void auditOperation(AuditData data) {
    // 审计操作
}
```

### 数据对比

支持方法执行前后的数据对比，便于记录变更详情：

```java
@Op(args = Product.class)
public Product updateProduct(Product product) {
    Product oldProduct = productService.findById(product.getId());
    // 执行更新
    return productService.update(product);
}
```

## 版本信息

- 当前版本：1.0.4
- Java版本要求：17+
- Spring Boot版本要求：3.1.5+

## 许可证

本项目基于 [Apache License 2.0](LICENSE) 开源。

## 贡献

欢迎提交Issue和Pull Request！详见[贡献指南](CONTRIBUTING.md)。

## 联系方式

- 作者：avinzhang
- 邮箱：avinzhang@tencent.com
- GitHub: [https://github.com/qwzhang01/seven-operating-record](https://github.com/qwzhang01/seven-operating-record)