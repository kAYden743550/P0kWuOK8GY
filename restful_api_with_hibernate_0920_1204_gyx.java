// 代码生成时间: 2025-09-20 12:04:37
// 使用Spring Boot和Hibernate实现RESTful API的Java程序
// 引入必要的依赖包
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

// 定义实体类User
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    // Getters and Setters
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
}

// RESTful API控制器
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository; // 依赖注入UserRepository

    // 获取所有用户信息
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 根据ID获取单个用户信息
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(user1 -> ResponseEntity.ok().body(user1))
            .orElse(ResponseEntity.notFound().build());
    }

    // 创建新用户
    @PostMapping
    @Transactional
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // 更新用户信息
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userDetails.setId(id); // 更新id为旧的id
        return ResponseEntity.ok(userRepository.save(userDetails));
    }

    // 删除用户
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(existingUser.get());
        return ResponseEntity.ok().build();
    }
}

// UserRepository接口继承JpaRepository
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

// 主程序类
@SpringBootApplication
public class RestfulApiWithHibernate {

    public static void main(String[] args) {
        SpringApplication.run(RestfulApiWithHibernate.class, args);
    }
}
