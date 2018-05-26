package io.pivotal.storedproc.api;

import com.github.javafaker.Faker;
import io.pivotal.storedproc.domain.Employee;
import io.pivotal.storedproc.model.ProcedureResult;
import io.pivotal.storedproc.repository.EmployeeRepository;
import io.pivotal.storedproc.repository.ProcedureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @PutMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee addEmployee(@RequestBody Employee employee) {
        log.info("Came inside addEmployee");

        Random random = new Random();
        employee.setId(random.nextInt());
        employeeRepository.save(employee);
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/random", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee randomEmployee() {
        log.info("Came inside randomEmployee");
        Employee employee = generateEmployee();
        employee.setCreatedAt(new Date());
        employeeRepository.save(employee);
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/procedure", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee procedureEmployee() {
        log.info("Came inside procedureEmployee");
        Employee employee = generateEmployee();
        ProcedureResult procedureResult = procedureRepository.addEmployeeThroughProcedure(employee.getFirstName(),
                employee.getLastName(), employee.getEmail());
        employee.setId(procedureResult.getId());
        employee.setEmail(procedureResult.getEmail());
        employee.setCreatedAt(procedureResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/null", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee procedureNullValueEmployee() {
        log.info("Came inside procedureNullValueEmployee");
        Employee employee = generateEmployee();
        employee.setLastName(null);
        ProcedureResult procedureResult = procedureRepository.addEmployeeThroughProcedure(employee.getFirstName(),
                employee.getLastName(), employee.getEmail());
        employee.setId(procedureResult.getId());
        employee.setEmail(procedureResult.getEmail());
        employee.setCreatedAt(procedureResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Employee> getAllEmployees() {
        log.info("Came inside getAllEmployees");
        return employeeRepository.findAll();
    }

    @DeleteMapping(path = "/remove/{id}")
    public @ResponseBody
    String removeEmployee(@PathVariable Integer id) {
        log.info("Came inside removeEmployee");
        try {
            employeeRepository.delete(id);
            return "Success";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Failure";
        }
    }

    private Employee generateEmployee() {
        Faker faker = new Faker();
        return Employee.builder()
                .id(faker.number().numberBetween(100, 9999))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();
    }
}
