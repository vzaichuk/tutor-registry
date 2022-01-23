package ua.com.registry.tutor.registration.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.registration.service.AssignmentService;

@RestController
@RequiredArgsConstructor
public class RegistrationRestController {

  private final AssignmentService assignmentService;

  @PostMapping("/assign")
  public ResponseEntity<Boolean> assignStudentToTutor(
      @RequestParam int tutorId, @RequestParam int studentId
  ) {
    return ResponseEntity.ok(assignmentService.assignStudent(tutorId, studentId));
  }
}
