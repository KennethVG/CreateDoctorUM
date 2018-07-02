package be.createdoctor.jparepo;

import be.createdoctor.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findBySsin(String ssin);
}
