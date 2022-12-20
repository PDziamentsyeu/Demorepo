package com.example.demo.domain;

import lombok.Data;

@Data
public class JobDefinition {
    String jobType;
    String cronExpression;
}
