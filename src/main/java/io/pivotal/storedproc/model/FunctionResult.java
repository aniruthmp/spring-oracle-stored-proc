package io.pivotal.storedproc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunctionResult implements Serializable {
    private Integer id;
    private String email;
    private Date createdAt;
}
