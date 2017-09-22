package app.dao;

import app.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Me on 2017/9/16.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findAll();
}
