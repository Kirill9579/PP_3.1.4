package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinTable(name = "users_roles",
           joinColumns = @JoinColumn(name = "role_id"),
           inverseJoinColumns = @JoinColumn(name = "user_id"))
   private List<User> users;

   public void addUserToRole(User user) {
      if (users == null) {
         users = new ArrayList<>();
      }
      users.add(user);
   }

   @Override
   public String getAuthority() {
      return name;
   }


}
