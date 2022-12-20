package com.example.demo.service;

import com.example.demo.domain.Job;
import com.example.demo.domain.JobDefinition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ScheduleTaskServiceUnitTest {

    @Mock
    private ThreadPoolTaskScheduler taskScheduler;

    private ScheduleTaskService service;

    @BeforeEach
    void setUp() {
        service = new ScheduleTaskService(new HashMap<>(), new HashMap<>(), taskScheduler);

    }

    @Test
    void testScheduleJobSuccess() {
        var jobDef = new JobDefinition();
        jobDef.setJobType("TEST_1");
        jobDef.setCronExpression("0 0 * ? * *");
        var job = new Job();
        job.setJobDefinition(jobDef);
        job.setJobData("data");
        job.setJobName("test");

        var jobTask = new JobTask(job);

        var scheduledTask = mock(ScheduledFuture.class);

        var jobTaskCaptor = ArgumentCaptor.forClass(JobTask.class);
        var cronCaptor = ArgumentCaptor.forClass(CronTrigger.class);

        when(taskScheduler.schedule(jobTaskCaptor.capture(), cronCaptor.capture())).thenReturn(scheduledTask);

        service.scheduleAsyncJob(jobTask);

        assertThat(jobTask).isEqualTo(jobTaskCaptor.getValue());

        assertThat(cronCaptor.getValue().getExpression()).isEqualTo(job.getJobDefinition().getCronExpression());
    }

}