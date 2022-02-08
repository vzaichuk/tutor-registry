package ua.com.registry.tutor.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequestDto {

  int tutorId;
  int studentId;
}
