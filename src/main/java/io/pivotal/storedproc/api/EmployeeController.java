package io.pivotal.storedproc.api;

import com.github.javafaker.Faker;
import io.pivotal.storedproc.domain.Employee;
import io.pivotal.storedproc.model.FunctionResult;
import io.pivotal.storedproc.model.ProcedureResult;
import io.pivotal.storedproc.repository.EmployeeRepository;
import io.pivotal.storedproc.repository.FunctionRepository;
import io.pivotal.storedproc.repository.ProcedureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private FunctionRepository functionRepository;

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

    @PutMapping(path = "/procedure", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(path = "/function", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee functionEmployee() {
        log.info("Came inside functionEmployee");
        Employee employee = generateEmployee();
        FunctionResult functionResult = functionRepository.addEmployeeThroughFunction(
                employee.getFirstName(), employee.getLastName(), employee.getEmail());
        employee.setId(functionResult.getId());
        employee.setEmail(functionResult.getEmail());
        employee.setCreatedAt(functionResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/null", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(path = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Employee> getAllEmployees() {
        log.info("Came inside getAllEmployees");
        return employeeRepository.findAll();
    }

    @DeleteMapping(path = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAllEmployees() {
        log.info("Came inside deleteAllEmployees");
        this.employeeRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        log.info("Came inside getEmployee");
        Employee employee = employeeRepository.findOne(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping(path = "/employees/{id}")
    public ResponseEntity<Void> removeEmployee(@PathVariable Integer id) {
        log.info("Came inside removeEmployee");
        try {
            employeeRepository.delete(id);
        } catch (Exception ex) {
            log.warn("delete failure", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/named/procedure", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee namedProcedureEmployee() {
        log.info("Came inside namedProcedureEmployee");
        Employee employee = generateEmployee();
        ProcedureResult procedureResult = procedureRepository.addEmployeeThroughNamedStoredProcedureQuery(
                employee.getFirstName(), employee.getLastName(), employee.getEmail());
        employee.setId(procedureResult.getId());
        employee.setEmail(procedureResult.getEmail());
        employee.setCreatedAt(procedureResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/named/null", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee namedProcedureNullValueEmployee() {
        log.info("Came inside namedProcedureNullValueEmployee");
        Employee employee = generateEmployee();
        employee.setLastName(null);
        ProcedureResult procedureResult = procedureRepository.addEmployeeThroughNamedStoredProcedureQuery(
                employee.getFirstName(), employee.getLastName(), employee.getEmail());
        employee.setId(procedureResult.getId());
        employee.setEmail(procedureResult.getEmail());
        employee.setCreatedAt(procedureResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }
/*
    @PutMapping(path = "/spring/procedure", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee springProcedureEmployee() {
        log.info("Came inside springProcedureEmployee");
        Employee employee = generateEmployee();
        ProcedureResult procedureResult = employeeRepository.addEmployeeThroughNamedStoredProcedureQuery(
                employee.getFirstName(), employee.getLastName(), employee.getEmail());
        employee.setId(procedureResult.getId());
        employee.setEmail(procedureResult.getEmail());
        employee.setCreatedAt(procedureResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/spring/null", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee springProcedureNullValueEmployee() {
        log.info("Came inside springProcedureNullValueEmployee");
        Employee employee = generateEmployee();
        employee.setLastName(null);
        ProcedureResult procedureResult = employeeRepository.addEmployeeThroughNamedStoredProcedureQuery(
                employee.getFirstName(), employee.getLastName(), employee.getEmail());
        employee.setId(procedureResult.getId());
        employee.setEmail(procedureResult.getEmail());
        employee.setCreatedAt(procedureResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }
*/
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
