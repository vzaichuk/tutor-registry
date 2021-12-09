package ua.com.registry.tutor.authentication.domain.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import ua.com.registry.tutor.authentication.domain.enums.GrantType;

@Data
@Entity
@Table(name = "auth_client")
public class AuthClient {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String client;

  @Column
  private String secret;

  @Column(name = "grant_types")
  private String grantTypes;

  @Column(name = "access_token_duration")
  private Integer accessTokenValidityDuration;

  @Column(name = "refresh_token_duration")
  private Integer refreshTokenValidityDuration;

  @Column(name = "is_enabled")
  private boolean enabled;

  public Set<GrantType> getGrantTypes() {
    return Objects.nonNull(grantTypes)
        ? Arrays.stream(grantTypes.split(" "))
            .map(GrantType::of).collect(Collectors.toSet())
        : Collections.emptySet();
  }

  public void setGrantTypes(GrantType... grantTypes) {
    this.grantTypes = Objects.nonNull(grantTypes)
        ? Arrays.stream(grantTypes).map(GrantType::getValue).collect(Collectors.joining(" "))
        : "";
  }
}
