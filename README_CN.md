# Seven Operating Record

[English Documentation](README.md)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.qwzhang01/seven-operating-record.svg)](https://search.maven.org/artifact/io.github.qwzhang01/seven-operating-record)

## 概述

Seven Operating Record 是一个轻量级的、基于 AOP 的 Spring Boot 操作记录库。它提供了一种灵活且可自定义的方式来跟踪业务操作、捕获数据变更并维护审计日志，无需侵入式的代码修改。

## 特性

- 🎯 **基于 AOP**：使用 Spring AOP 实现非侵入式操作记录
- 🔄 **数据对比**：跟踪方法执行前后的数据变化
- 🎨 **可自定义策略**：灵活的策略模式适配不同的记录场景
- 📝 **多种策略类型**：内置支持基于参数、查询和返回值的记录方式
- 🚀 **易于集成**：Spring Boot 自动配置，最小化配置
- 🔧 **类型安全**：完整的泛型支持确保类型安全
- ⚡ **轻量级**：最少依赖和开销

## 环境要求

- Java 17+
- Spring Boot 3.1.5+

## 安装

### Maven

```xml
<dependency>
    <groupId>io.github.qwzhang01</groupId>
    <artifactId>seven-operating-record</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.github.qwzhang01:seven-operating-record:1.0.1'
```

## 快速开始

### 1. 基本使用

只需在服务方法上添加 `@Op` 注解：

```java
@Service
public class UserService {
    
    @Op(strategy = UserOpStrategy.class, args = UserDto.class)
    public void updateUser(UserDto userDto) {
        // 你的业务逻辑
        userRepository.save(userDto);
    }
}
```

### 2. 创建策略

实现自定义的记录策略：

```java
@Component
public class UserOpStrategy implements OpStrategy<UserDto, User, Void> {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OperationLogService logService;
    
    @Override
    public User beforeAction(UserDto args) {
        // 在操作前捕获旧数据
        return userRepository.findById(args.getId()).orElse(null);
    }
    
    @Override
    public void afterAction(User oldData, UserDto newData) {
        // 对比并记录变更
        List<String> changes = compareData(oldData, newData);
        logService.save("用户更新", changes);
    }
    
    private List<String> compareData(User oldData, UserDto newData) {
        List<String> changes = new ArrayList<>();
        if (!Objects.equals(oldData.getName(), newData.getName())) {
            changes.add("姓名: " + oldData.getName() + " -> " + newData.getName());
        }
        // 对比其他字段...
        return changes;
    }
}
```

## 策略类型

Seven Operating Record 提供了三种内置策略接口：

### 1. OpStrategy<N, O, R>

用于自定义操作记录的基础策略接口。

**使用场景**：需要完全控制操作前后处理逻辑

```java
@Component
public class CustomStrategy implements OpStrategy<InputDto, Entity, Boolean> {
    @Override
    public Entity beforeAction(InputDto args) {
        // 在操作前捕获状态
        return repository.findById(args.getId()).orElse(null);
    }
    
    @Override
    public void afterAction(Entity oldData, InputDto newData) {
        // 记录操作
        logService.recordChange(oldData, newData);
    }
}
```

### 2. OpNeedQueryStrategy<N, O, Void>

用于需要在执行前查询数据的操作策略。

**使用场景**：需要获取现有数据进行对比

```java
@Component
public class QueryStrategy implements OpNeedQueryStrategy<UserDto, User, Void> {
    @Override
    public User beforeAction(UserDto args) {
        // 查询现有数据
        return userRepository.findById(args.getId()).orElse(null);
    }
    
    @Override
    public void afterAction(User dbData, UserDto args) {
        // 将数据库数据与新数据对比
        if (dbData != null) {
            List<String> changes = findDifferences(dbData, args);
            auditService.log(changes);
        }
    }
}
```

### 3. OpParamStrategy<P, Void>

使用方法参数本身进行记录的策略。

**使用场景**：参数本身包含所有必要信息

```java
@Component
public class ParamStrategy implements OpParamStrategy<LogDto, Void> {
    @Override
    public void afterAction(LogDto args) {
        // 直接使用参数数据记录
        operationLogService.save(args);
    }
}
```

### 4. OpReturnStrategy<Void, R>

使用方法返回值进行记录的策略。

**使用场景**：需要根据操作结果进行记录

```java
@Component
public class ReturnStrategy implements OpReturnStrategy<Void, OperationResult> {
    @Override
    public void afterReturn(OperationResult returnData) {
        // 根据返回值记录
        if (returnData.isSuccess()) {
            logService.recordSuccess(returnData);
        } else {
            logService.recordFailure(returnData);
        }
    }
}
```

## 注解属性

### @Op 注解

| 属性 | 类型 | 默认值 | 描述 |
|-----|------|-------|------|
| `strategy` | `Class<? extends OpStrategy>` | `DefaultOpStrategy.class` | 处理操作记录的策略类 |
| `args` | `Class<?>` | `Object.class` | 从方法参数中提取的参数类型 |
| `comparable` | `boolean` | `false` | 是否对比新旧数据 |
| `removed` | `boolean` | `false` | 是否为删除操作 |

## 高级用法

### 数据对比

启用对比功能跟踪数据变化：

```java
@Op(strategy = CompareStrategy.class, args = UserDto.class, comparable = true)
public void updateUser(UserDto userDto) {
    userRepository.update(userDto);
}
```

### 删除跟踪

标记删除操作以在删除前捕获数据：

```java
@Op(strategy = DeleteStrategy.class, args = Long.class, removed = true)
public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
}
```

### 上下文感知记录

在策略中访问类和方法信息：

```java
@Component
public class ContextStrategy implements OpStrategy<UserDto, User, Void> {
    @Override
    public void afterAction(String clazz, String method, User oldData, UserDto newData) {
        // clazz: 完全限定类名
        // method: 方法名
        logService.record(clazz + "." + method, oldData, newData);
    }
}
```

### 返回值处理

基于返回值记录操作：

```java
@Op(strategy = ResultStrategy.class)
public Result createOrder(OrderDto orderDto) {
    return orderService.create(orderDto);
}

@Component
public class ResultStrategy implements OpReturnStrategy<Void, Result> {
    @Override
    public void afterReturn(String clazz, String method, Result returnData) {
        if (returnData.isSuccess()) {
            auditService.recordOrderCreation(returnData.getData());
        }
    }
}
```

## 配置

该库使用 Spring Boot 自动配置。你可以覆盖默认的 Bean：

```java
@Configuration
public class OperatingRecordConfiguration {
    
    @Bean
    public OpStrategy customDefaultStrategy() {
        return new CustomDefaultStrategy();
    }
}
```

## 架构

```
┌─────────────────────────────────────────────────────────┐
│                     @Op 注解                             │
│  标记需要记录操作的方法                                   │
└────────────────┬────────────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────────────┐
│                      OpAspect                            │
│  拦截带有 @Op 注解的方法                                  │
└────┬────────────────────────────────────────────────┬───┘
     │                                                 │
     ▼                                                 ▼
┌────────────────┐                            ┌──────────────┐
│BeforeProcessor │                            │AfterProcessor│
│捕获旧数据状态  │                            │记录操作      │
└────────────────┘                            └──────────────┘
     │                                                 │
     └──────────────────┬──────────────────────────────┘
                        ▼
            ┌───────────────────────┐
            │   OpStrategy          │
            │   (用户实现)          │
            └───────────────────────┘
```

## 最佳实践

1. **策略作为 Spring Bean**：始终将策略注册为 Spring Bean（`@Component`、`@Service` 等）
2. **类型安全**：在策略中使用具体的泛型类型以确保类型安全
3. **轻量级的前置操作**：保持 `beforeAction` 方法高效以最小化性能影响
4. **异步日志记录**：考虑对重量级的日志操作使用异步处理
5. **异常处理**：在策略中处理异常以防止中断业务逻辑

## 示例

查看 [示例目录](./examples) 获取完整的工作示例：

- 带审计日志的用户管理
- 带变更跟踪的订单处理
- 带恢复信息的数据删除

## 贡献

欢迎贡献！请随时提交 Pull Request。

## 许可证

本项目采用 Apache License 2.0 许可证 - 详见 [LICENSE](LICENSE) 文件。

## 作者

**avinzhang**
- 邮箱：avinzhang@tencent.com
- GitHub: [@qwzhang01](https://github.com/qwzhang01)

## 支持

如果你有任何问题或遇到问题，请：

1. 查看[文档](./docs)
2. 搜索[现有问题](https://github.com/qwzhang01/seven-operating-record/issues)
3. 创建[新问题](https://github.com/qwzhang01/seven-operating-record/issues/new)

## 更新日志

### 版本 1.0.1
- 初始版本发布
- 基础操作记录功能
- 支持可自定义策略
- Spring Boot 自动配置
