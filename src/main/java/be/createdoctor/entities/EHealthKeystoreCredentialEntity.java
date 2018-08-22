package be.createdoctor.entities;

import javax.persistence.*;

@Entity
@Table(name = "dbo.EHealthKeystoreCredential")
public class EHealthKeystoreCredentialEntity {

    @Id
    private Long credential_id;

    public Long getCredential_id() {
        return credential_id;
    }

    public void setCredential_id(Long credential_id) {
        this.credential_id = credential_id;
    }

    //    @Id
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId("credential_id")
//    private CredentialEntity credentialEntity;
//
//    public CredentialEntity getCredentialEntity() {
//        return credentialEntity;
//    }
//
//    public void setCredentialEntity(CredentialEntity credentialEntity) {
//        this.credentialEntity = credentialEntity;
//    }
}
