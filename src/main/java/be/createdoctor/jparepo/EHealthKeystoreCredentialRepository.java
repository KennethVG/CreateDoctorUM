package be.createdoctor.jparepo;

import be.createdoctor.entities.CredentialEntity;
import be.createdoctor.entities.EHealthKeystoreCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EHealthKeystoreCredentialRepository extends JpaRepository<EHealthKeystoreCredentialEntity, Long> {

    //EHealthKeystoreCredentialEntity findByCredentialEntity(CredentialEntity credentialEntity);
}
