package com.HieuVo.BookStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

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

        public Role(int roleId, String roleName, String description, List<User> listUsers) {
                this.roleId = roleId;
                this.roleName = roleName;
                this.description = description;
                this.listUsers = listUsers;
        }

        public int getRoleId() {
                return roleId;
        }

        public void setRoleId(int roleId) {
                this.roleId = roleId;
        }

        public String getRoleName() {
                return roleName;
        }

        public void setRoleName(String roleName) {
                this.roleName = roleName;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public List<User> getListUsers() {
                return listUsers;
        }

        public void setListUsers(List<User> listUsers) {
                this.listUsers = listUsers;
        }

        @Override
        public String toString() {
                return "Role{id=" + roleId + ", name='" + roleName + "'}"; // Don't include listUsers here
        }
}
