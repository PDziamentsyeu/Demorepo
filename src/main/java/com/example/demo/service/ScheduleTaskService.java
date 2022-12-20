package com.example.demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
@Log4j2
@AllArgsConstructor
public class ScheduleTaskService {

    private Map<String, ScheduledFuture> schedulerMap;

    private Map<String, JobTask> jobTaskMap;

    private ThreadPoolTaskScheduler taskScheduler;

    public void scheduleAsyncJob(JobTask jobTask) {
        var job = jobTask.getJob();
        log.info("scheduling job {}:", job.toString());
        String jobId = UUID.randomUUID().toString();
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(jobTask,
                new CronTrigger(job.getJobDefinition().getCronExpression())
        );
        schedulerMap.put(jobId, scheduledTask);
        jobTaskMap.put(jobId, jobTask);
    }

    public void executeSelectedJob(String jobId){
        var jobTask = getJobTaskById(jobId);
        if (jobTask != null) {
            taskScheduler.schedule(jobTask, new Date());
        }
    }

    public Map<String, JobTask> getAllScheduledJobs() {
       return jobTaskMap;
    }

    public Map<String, ScheduledFuture> getJobStates() {
        return schedulerMap;
    }

    public void removeScheduledJob(String jobId) {
        var scheduledTask = getScheduledFeatureById(jobId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            schedulerMap.remove(jobId);
            jobTaskMap.remove(jobId);
        }
    }

    private ScheduledFuture getScheduledFeatureById(String jobId){
        return schedulerMap.get(jobId);
    }

    private JobTask getJobTaskById(String jobId){
        return jobTaskMap.get(jobId);
    }

}
