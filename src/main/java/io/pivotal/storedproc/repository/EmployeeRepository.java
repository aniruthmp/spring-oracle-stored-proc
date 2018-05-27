package io.pivotal.storedproc.repository;

import io.pivotal.storedproc.domain.Employee;
import io.pivotal.storedproc.model.ProcedureResult;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
//    @Procedure(name = "addEmployeeThroughNamedStoredProcedureQuery")
//    ProcedureResult addEmployeeThroughNamedStoredProcedureQuery(@Param("FIRST_NAME") String firstName,
//                                                                @Param("LAST_NAME") String lastName,
//                                                                @Param("EMAIL") String email);
}
