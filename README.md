# Seven Operating Record

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.qwzhang01/seven-operating-record.svg)](https://search.maven.org/artifact/io.github.qwzhang01/seven-operating-record)

An AOP-based operation recording library for Spring Boot applications with customizable recording strategies.

## Features

- 🎯 **AOP-Based**: Non-invasive operation recording using Spring AOP
- 🔧 **Customizable**: Flexible strategy pattern for custom recording logic
- 📊 **Change Tracking**: Built-in support for comparing old and new data
- 🚀 **Easy Integration**: Auto-configuration with Spring Boot
- 🎨 **Type-Safe**: Enum-based target and action definitions
- 🔌 **Extensible**: Simple interface for implementing custom strategies

## Requirements

- Java 17 or higher
- Spring Boot 3.1.5 or higher
- Maven 3.6+

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.qwzhang01</groupId>
    <artifactId>seven-operating-record</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick Start

### 1. Define Your Enums

Define enums for operation targets and actions:

```java
public enum TargetType {
    USER,
    ORDER,
    PRODUCT
}

public enum ActionType {
    CREATE,
    UPDATE,
    DELETE
}
```

### 2. Implement Custom Strategy

Create a custom strategy by implementing `OpStrategy`:

```java
@Component
public class CustomOpStrategy implements OpStrategy<User> {
    
    @Override
    public User beforeAction() {
        // Query and return old data before method execution
        // This is called only when comparable=true
        return userRepository.findById(currentUserId);
    }
    
    @Override
    public void afterAction(Class<? extends Enum> target, 
                           Class<? extends Enum> action, 
                           Object newData) {
        // Record operation without comparison
        System.out.println("Operation: " + action + " on " + target);
        System.out.println("New Data: " + newData);
    }
    
    @Override
    public void afterAction(Class<? extends Enum> target,
                           Class<? extends Enum> action, 
                           Object oldData, 
                           Object newData) {
        // Record operation with comparison
        System.out.println("Operation: " + action + " on " + target);
        System.out.println("Old Data: " + oldData);
        System.out.println("New Data: " + newData);
        // Implement your logic: save to database, send to MQ, etc.
    }
}
```

### 3. Annotate Your Methods

Use the `@Op` annotation on methods you want to record:

```java
@Service
public class UserService {
    
    // Simple operation recording without comparison
    @Op(target = TargetType.class, 
        action = ActionType.class, 
        strategy = CustomOpStrategy.class,
        args = UserDto.class)
    public void createUser(UserDto userDto) {
        // Your business logic
    }
    
    // Operation recording with before/after comparison
    @Op(target = TargetType.class,
        action = ActionType.class,
        strategy = CustomOpStrategy.class,
        args = UserDto.class,
        comparable = true)
    public void updateUser(UserDto userDto) {
        // Your business logic
    }
}
```

## Configuration

The library provides auto-configuration out of the box. All required beans are automatically registered:

- `SpringKit`: Utility for accessing Spring application context
- `DefaultOpStrategy`: Default no-op strategy implementation
- `OpAspect`: AOP aspect for intercepting annotated methods

You can override any bean by defining your own in the application context:

```java
@Configuration
public class MyConfig {
    
    @Bean
    public OpStrategy myCustomStrategy() {
        return new MyCustomOpStrategy();
    }
}
```

## How It Works

### Workflow

1. **Before Execution** (if `comparable=true`):
   - `BeforeProcessor` invokes the strategy's `beforeAction()` method
   - Old data is captured and stored

2. **Argument Processing**:
   - `ArgsProcessor` extracts the argument matching the type specified in `args`
   - This becomes the new data

3. **Method Execution**:
   - The original method executes normally

4. **After Execution**:
   - `AfterProcessor` invokes the strategy's `afterAction()` method
   - Operation is recorded with old data (if available) and new data

### Architecture

```
@Op Annotation
     |
     v
  OpAspect (Around Advice)
     |
     +-- BeforeProcessor --> Strategy.beforeAction()
     |
     +-- ArgsProcessor --> Extract method args
     |
     +-- Execute original method
     |
     +-- AfterProcessor --> Strategy.afterAction()
```

## Annotation Parameters

### `@Op` Annotation

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `target` | `Class<? extends Enum>` | Yes | - | Target entity type (e.g., USER, ORDER) |
| `action` | `Class<? extends Enum>` | Yes | - | Action type (e.g., CREATE, UPDATE, DELETE) |
| `strategy` | `Class<? extends OpStrategy>` | No | `DefaultOpStrategy.class` | Custom strategy for operation recording |
| `args` | `Class<?>` | No | `Object.class` | Type of argument to extract from method parameters |
| `comparable` | `boolean` | No | `false` | Enable before/after data comparison |

## Advanced Usage

### Multiple Strategies

You can define multiple strategies for different types of operations:

```java
@Component
public class AuditStrategy implements OpStrategy<AuditLog> {
    // Audit-specific implementation
}

@Component
public class NotificationStrategy implements OpStrategy<Void> {
    // Notification-specific implementation
}

// Use different strategies for different operations
@Op(target = TargetType.class, action = ActionType.class, 
    strategy = AuditStrategy.class)
public void importantOperation() { }

@Op(target = TargetType.class, action = ActionType.class,
    strategy = NotificationStrategy.class)
public void notifyOperation() { }
```

### Database Logging Example

```java
@Component
public class DatabaseOpStrategy implements OpStrategy<User> {
    
    @Autowired
    private OperationLogRepository logRepository;
    
    @Override
    public User beforeAction() {
        // Retrieve current user ID from context
        Long userId = SecurityContextHolder.getCurrentUserId();
        return userRepository.findById(userId).orElse(null);
    }
    
    @Override
    public void afterAction(Class<? extends Enum> target,
                           Class<? extends Enum> action,
                           Object oldData,
                           Object newData) {
        OperationLog log = new OperationLog();
        log.setTarget(target.getSimpleName());
        log.setAction(action.getSimpleName());
        log.setOldData(JSON.toJSONString(oldData));
        log.setNewData(JSON.toJSONString(newData));
        log.setOperateTime(LocalDateTime.now());
        
        logRepository.save(log);
    }
}
```

## Best Practices

1. **Keep Strategies Focused**: Each strategy should handle one specific type of recording (e.g., audit logs, notifications, analytics)

2. **Use Enums**: Define clear, type-safe enums for targets and actions instead of using strings

3. **Handle Nulls**: Always check for null values in your strategy implementations

4. **Async Processing**: For performance-critical operations, consider processing logs asynchronously:

```java
@Override
public void afterAction(...) {
    CompletableFuture.runAsync(() -> {
        // Save log asynchronously
    });
}
```

5. **Use `comparable` Wisely**: Only enable it when you actually need before/after comparison, as it adds overhead

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Author

- **avinzhang** - [GitHub](https://github.com/qwzhang01)

## Links

- [GitHub Repository](https://github.com/qwzhang01/seven-operating-record)
- [Issue Tracker](https://github.com/qwzhang01/seven-operating-record/issues)
- [Maven Central](https://search.maven.org/artifact/io.github.qwzhang01/seven-operating-record)

## Changelog

### Version 1.0.0
- Initial release
- AOP-based operation recording
- Customizable strategy pattern
- Before/after data comparison support
- Spring Boot auto-configuration
