package com.italo.TaskBrother.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_FAMILY")
public class FamilyModel {
    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "family_ID")
    private UUID familyID;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @OneToMany(mappedBy = "familyFK")
    private List<UserModel> userModelList;

    public UUID getFamilyID() {
        return familyID;
    }

    public void setFamilyID(UUID familyID) {
        this.familyID = familyID;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<UserModel> getUserModelList() {
        return userModelList;
    }

    public void setUserModelList(List<UserModel> userModelList) {
        this.userModelList = userModelList;
    }
}
