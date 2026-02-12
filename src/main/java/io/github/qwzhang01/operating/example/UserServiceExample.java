package io.github.qwzhang01.operating.example;

import io.github.qwzhang01.operating.anno.Op;
import org.springframework.stereotype.Service;

/**
 * 用户服务示例类
 * 
 * <p>展示如何使用@Op注解进行操作记录</p>
 * 
 * @author avinzhang
 */
@Service
public class UserServiceExample {

    /**
     * 创建用户操作记录示例
     * 
     * <p>使用默认策略记录用户创建操作</p>
     * 
     * @param user 用户信息
     * @return 创建的用户
     */
    @Op(args = User.class)
    public User createUser(User user) {
        // 模拟用户创建逻辑
        System.out.println("创建用户: " + user.getName());
        return user;
    }

    /**
     * 更新用户操作记录示例
     * 
     * <p>使用默认策略记录用户更新操作</p>
     * 
     * @param userId 用户ID
     * @param user 更新后的用户信息
     * @return 更新后的用户
     */
    @Op(args = User.class)
    public User updateUser(Long userId, User user) {
        // 模拟用户更新逻辑
        System.out.println("更新用户ID: " + userId + ", 新信息: " + user.getName());
        return user;
    }

    /**
     * 删除用户操作记录示例
     * 
     * <p>使用默认策略记录用户删除操作</p>
     * 
     * @param userId 用户ID
     */
    @Op(args = Long.class)
    public void deleteUser(Long userId) {
        // 模拟用户删除逻辑
        System.out.println("删除用户ID: " + userId);
    }

    /**
     * 查询用户操作记录示例
     * 
     * <p>使用默认策略记录用户查询操作</p>
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    @Op(args = Long.class)
    public User getUser(Long userId) {
        // 模拟用户查询逻辑
        System.out.println("查询用户ID: " + userId);
        return new User(userId, "示例用户");
    }

    /**
     * 用户信息类
     */
    public static class User {
        private Long id;
        private String name;
        private String email;

        public User() {
        }

        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getter和Setter方法
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}