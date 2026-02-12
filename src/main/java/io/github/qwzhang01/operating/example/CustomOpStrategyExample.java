package io.github.qwzhang01.operating.example;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.strategy.OpStrategy;
import org.springframework.stereotype.Component;

/**
 * 自定义操作记录策略示例
 * 
 * <p>展示如何实现自定义的操作记录策略</p>
 * 
 * @author avinzhang
 */
@Component
public class CustomOpStrategyExample implements OpStrategy {

    /**
     * 自定义操作记录逻辑
     * 
     * <p>这个示例展示了如何实现一个审计专用的记录策略，
     * 可以将操作记录发送到审计系统或消息队列</p>
     * 
     * @param className 类名
     * @param methodName 方法名
     * @param op 操作注解
     * @param oldData 旧数据（方法执行前）
     * @param newData 新数据（方法参数）
     * @param result 方法执行结果
     */
    public void record(String className, String methodName, Op op,
                      Object oldData, Object newData, Object result) {
        
        // 构建操作记录信息
        OperationRecord record = new OperationRecord();
        record.setClassName(className);
        record.setMethodName(methodName);
        record.setTimestamp(System.currentTimeMillis());
        record.setOldData(oldData);
        record.setNewData(newData);
        record.setResult(result);
        
        // 这里可以添加自定义的记录逻辑，例如：
        // 1. 保存到数据库
        // 2. 发送到消息队列
        // 3. 写入审计日志
        // 4. 调用外部审计系统
        
        System.out.println("=== 自定义操作记录 ===");
        System.out.println("类名: " + record.getClassName());
        System.out.println("方法名: " + record.getMethodName());
        System.out.println("时间戳: " + record.getTimestamp());
        System.out.println("旧数据: " + record.getOldData());
        System.out.println("新数据: " + record.getNewData());
        System.out.println("执行结果: " + record.getResult());
        System.out.println("===================");
        
        // 实际应用中，这里应该调用相应的服务进行记录
        // auditService.recordOperation(record);
    }

    /**
     * 操作记录实体类
     */
    public static class OperationRecord {
        private String className;
        private String methodName;
        private long timestamp;
        private Object oldData;
        private Object newData;
        private Object result;

        // Getter和Setter方法
        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public Object getOldData() {
            return oldData;
        }

        public void setOldData(Object oldData) {
            this.oldData = oldData;
        }

        public Object getNewData() {
            return newData;
        }

        public void setNewData(Object newData) {
            this.newData = newData;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "OperationRecord{" +
                    "className='" + className + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", timestamp=" + timestamp +
                    ", oldData=" + oldData +
                    ", newData=" + newData +
                    ", result=" + result +
                    '}';
        }
    }
}

/**
 * 使用自定义策略的示例服务
 */
class AuditServiceExample {
    
    /**
     * 使用自定义策略进行审计记录
     * 
     * <p>通过指定strategy参数使用自定义的记录策略</p>
     * 
     * @param user 用户信息
     * @return 操作结果
     */
    @Op(strategy = CustomOpStrategyExample.class, args = UserServiceExample.User.class)
    public UserServiceExample.User auditCreateUser(UserServiceExample.User user) {
        // 审计相关的业务逻辑
        System.out.println("执行审计用户创建操作");
        return user;
    }
    
    /**
     * 敏感操作审计记录
     * 
     * <p>对敏感操作使用专门的审计策略</p>
     * 
     * @param userId 用户ID
     */
    @Op(strategy = CustomOpStrategyExample.class, args = Long.class)
    public void auditDeleteUser(Long userId) {
        // 敏感操作业务逻辑
        System.out.println("执行审计用户删除操作，用户ID: " + userId);
    }
}