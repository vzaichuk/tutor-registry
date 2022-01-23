package ua.com.registry.tutor.registration.domain.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

  @Id
  private String id;
  private int studentsLimit;
  private int tutorAccountId;
  private Set<Integer> studentAccountIds;
}
