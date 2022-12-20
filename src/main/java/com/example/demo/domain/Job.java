package com.example.demo.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Job {
    private JobDefinition jobDefinition;
    private String jobName;
    private String jobData;
}
