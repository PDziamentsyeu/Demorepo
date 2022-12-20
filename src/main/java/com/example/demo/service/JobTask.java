package com.example.demo.service;

import com.example.demo.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Setter
@Getter
@AllArgsConstructor
public class JobTask implements Runnable {

    private Job job;

    @Override
    public void run() {
        log.info("Running job: {} with name: {} and data: {}", job.getJobDefinition().getJobType(), job.getJobName(), job.getJobData());
    }
}
