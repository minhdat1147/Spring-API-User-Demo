package spring.user.demo.springuserdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.user.demo.springuserdemo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
