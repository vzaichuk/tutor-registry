package ua.com.registry.tutor.registration.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ua.com.registry.tutor.registration.domain.entity.Assignment;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {

  Optional<Assignment> findByTutorAccountId(int tutorAccountId);

  @Query("{studentAccountIds: {$elemMatch: {$eq: ?0}}}")
  List<Assignment> findAllByStudentAccountId(int studentAccountId);
}
