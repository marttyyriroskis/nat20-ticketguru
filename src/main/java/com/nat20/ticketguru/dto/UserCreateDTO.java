package com.nat20.ticketguru.dto;

import com.nat20.ticketguru.domain.User;

public class UserCreateDTO {

        private String email;
        private String firstName;
        private String lastName;
        private Long roleId;
        private String password;

        public UserCreateDTO(String email, String firstName, String lastName, Long roleId, String password) {
                this.email = email;
                this.firstName = firstName;
                this.lastName = lastName;
                this.roleId = roleId;
                this.password = password;
        }

        public UserCreateDTO() {

        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public Long getRoleId() {
                return roleId;
        }

        public void setRoleId(Long roleId) {
                this.roleId = roleId;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public User toUser() {
                return new User(email, firstName, lastName);
        }

}
