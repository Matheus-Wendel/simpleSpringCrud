package fatec.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import fatec.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
