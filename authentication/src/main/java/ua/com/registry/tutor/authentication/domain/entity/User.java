package ua.com.registry.tutor.authentication.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;
import ua.com.registry.tutor.common.domain.enums.UserRole;

@Data
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String username;

  @Column
  @JsonIgnore
  private String password;

  @Column(name = "is_enabled")
  private boolean enabled;

  @Column
  private String roles = Strings.EMPTY;

  public Set<UserRole> getRoles() {
    return StringUtils.hasText(roles)
        ? Arrays.stream(roles.split(" ")).map(UserRole::of).collect(Collectors.toSet())
        : Collections.emptySet();
  }

  public void setRoles(UserRole ...roles) {
    this.roles = Objects.nonNull(roles)
        ? Stream.of(roles).map(UserRole::getValue).collect(Collectors.joining(" "))
        : "";
  }
}
