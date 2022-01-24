package ua.com.registry.tutor.common.domain.dto;

import lombok.Value;

@Value
public class AssignmentRequestDto {

  int tutorId;
  int studentId;
}
