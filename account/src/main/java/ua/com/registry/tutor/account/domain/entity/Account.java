package ua.com.registry.tutor.account.domain.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;
import ua.com.registry.tutor.common.domain.enums.UserRole;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_id")
  private int userId;

  @Column
  private String username;

  @Column
  private String bio;

  @Column
  private int status;

  @Column
  private String role;

  public UserRole getRole() {
    return StringUtils.hasText(role) ? UserRole.of(role) : null;
  }

  public void setRole(UserRole role) {
    this.role = Objects.nonNull(role) ? role.getValue() : "";
  }
}
