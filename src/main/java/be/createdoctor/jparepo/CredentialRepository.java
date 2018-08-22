package be.createdoctor.jparepo;

import be.createdoctor.entities.CredentialEntity;
import be.createdoctor.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {

    CredentialEntity findByUserEntity(UserEntity userEntity);
}
