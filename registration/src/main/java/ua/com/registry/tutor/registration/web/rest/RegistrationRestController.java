package ua.com.registry.tutor.registration.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.common.domain.dto.AssignmentRequestDto;
import ua.com.registry.tutor.registration.service.AssignmentService;

@RestController
@RequiredArgsConstructor
public class RegistrationRestController {

  private final AssignmentService assignmentService;

  @GetMapping("/assigned/{studentId}")
  public ResponseEntity<Integer> getAssignedTutorAccountId(@PathVariable int studentId) {
    return ResponseEntity.ok(assignmentService.getAssignedTutorAccountId(studentId));
  }

  @PostMapping("/assign")
  public ResponseEntity<Boolean> assignStudentToTutor(@RequestBody AssignmentRequestDto dto) {
    return ResponseEntity.ok(assignmentService.assignStudent(dto.getTutorId(), dto.getStudentId()));
  }
}
