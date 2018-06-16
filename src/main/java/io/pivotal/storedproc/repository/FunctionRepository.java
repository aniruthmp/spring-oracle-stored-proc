package io.pivotal.storedproc.repository;

import io.pivotal.storedproc.model.FunctionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Repository
public class FunctionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public FunctionResult addEmployeeThroughFunction(String firstName, String lastName, String email) {

        return jdbcTemplate.execute(
                (Connection c) -> {
                    try {
                        CallableStatement cs = c.prepareCall("{ ? = call EMPLOYEEFUNCTION(?, ?, ?, ?)}");
                        cs.registerOutParameter(1, Types.INTEGER); // or whatever type your function returns.
                        // Set your arguments
                        cs.setString(2, firstName); // first argument
                        cs.setString(3, lastName); // second argument
                        cs.setString(4, email); // third argument
                        cs.registerOutParameter(5, Types.DATE); // first OUT parameter
                        return cs;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                (CallableStatement cs) -> {
                    cs.execute();
                    return FunctionResult.builder()
                            .id(cs.getInt(1))
                            .email(email)
                            .createdAt(cs.getDate(5))
                            .build();
                }
        );
    }
}
