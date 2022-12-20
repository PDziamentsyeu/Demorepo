package com.example.demo.controller;

import com.example.demo.domain.Job;
import com.example.demo.service.JobTask;
import com.example.demo.service.JobTypesService;
import com.example.demo.service.ScheduleTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("schedules")
@AllArgsConstructor
@Log4j2
public class ScheduleController {

    private ScheduleTaskService schedulerService;
    private JobTypesService jobTypesService;

    @PostMapping("/schedule-job")
    public void scheduleJob(@RequestBody Job job){
        if(null!=job){
            var jobTask =  new JobTask(job);
            schedulerService.scheduleAsyncJob(jobTask);
        }
    }

    @PostMapping("/execute-job/{id}")
    public void scheduleJob(@PathVariable String id){
        schedulerService.executeSelectedJob(id);
    }

    @GetMapping
    public ResponseEntity getAllScheduledJobs(){
        log.info("retrieving list of all jobs");
        return new ResponseEntity(schedulerService.getAllScheduledJobs(), HttpStatus.OK);
    }

    @GetMapping("/job-definitions")
    public ResponseEntity getAllJobDefinitions(){
        log.info("retrieving list of all job definitions");
        return new ResponseEntity(jobTypesService.getDefinitions(), HttpStatus.OK);
    }

    @GetMapping("/jobs-state")
    public ResponseEntity getAllScheduledJobsState(){
        log.info("retrieving state list for all jobs");
        return new ResponseEntity(schedulerService.getJobStates(), HttpStatus.OK);
    }

    @DeleteMapping(path="/remove/{id}")
    public void removeJob(@PathVariable String id) {
        schedulerService.removeScheduledJob(id);
    }
}
