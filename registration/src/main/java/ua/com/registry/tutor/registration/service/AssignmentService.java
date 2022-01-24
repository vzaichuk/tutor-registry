package ua.com.registry.tutor.registration.service;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.registry.tutor.registration.domain.entity.Assignment;
import ua.com.registry.tutor.registration.repository.AssignmentRepository;

@Service
@RequiredArgsConstructor
public class AssignmentService {

  private final static int DEFAULT_STUDENTS_LIMIT = 10;

  private final AssignmentRepository assignmentRepository;

  public int getAssignedTutorAccountId(int studentId) {
    return assignmentRepository.findAllByStudentAccountId(studentId).stream()
        .map(Assignment::getTutorAccountId)
        .findAny().orElse(0);
  }

  @Transactional
  public boolean assignStudent(int tutorAccountId, int studentAccountId) {
    // Remove assignments from other tutors.
    List<Assignment> assignments = assignmentRepository.findAllByStudentAccountId(studentAccountId);
    if (!assignments.isEmpty()) {
      assignments.stream()
          .filter(a -> a.getTutorAccountId() != tutorAccountId)
          .peek(a -> a.getStudentAccountIds().remove(studentAccountId))
          .forEach(assignmentRepository::save);
    }

    Assignment assignment = assignmentRepository.findByTutorAccountId(tutorAccountId).orElseGet(
        () -> new Assignment(null, DEFAULT_STUDENTS_LIMIT, tutorAccountId, new HashSet<>()));

    if (assignment.getStudentAccountIds().size() >= assignment.getStudentsLimit()) {
      throw new IllegalStateException("Tutor reached limit of assigned students");
    }

    if (assignment.getStudentAccountIds().contains(studentAccountId)) {
      throw new IllegalStateException("You are already assigned to this tutor");
    }

    assignment.getStudentAccountIds().add(studentAccountId);

    assignmentRepository.save(assignment);

    return true;
  }
}
