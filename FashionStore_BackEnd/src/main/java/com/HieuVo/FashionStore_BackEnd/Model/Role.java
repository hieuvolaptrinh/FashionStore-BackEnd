package com.HieuVo.FashionStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private int roleId;

       
        private String roleName;

      
        @Column(columnDefinition = "NVARCHAR(500)")
        private String description;

        @ManyToMany(cascade = {
                        jakarta.persistence.CascadeType.PERSIST,
                        jakarta.persistence.CascadeType.MERGE,
                        jakarta.persistence.CascadeType.DETACH,
                        jakarta.persistence.CascadeType.REFRESH
        })
        @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<User> listUsers;

        public Role() {
        }

        @Override
        public String toString() {
                return roleName;
        }
}
