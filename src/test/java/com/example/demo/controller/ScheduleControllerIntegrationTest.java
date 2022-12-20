package com.example.demo.controller;

import com.example.demo.domain.Job;
import com.example.demo.domain.JobDefinition;
import com.example.demo.service.JobTypesService;
import com.example.demo.service.ScheduleTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerIntegrationTest {

    @Autowired
    private JobTypesService jobTypesService;

    @Autowired
    private ScheduleTaskService service;


    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyBody() throws Exception {
        this.mockMvc.perform(get("/schedules")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void shouldSetJobToRun() throws Exception {

        var job = new Job();
        job.setJobDefinition(jobTypesService.getDefinitions().get(0));
        job.setJobData("data");
        job.setJobName("test");


        this.mockMvc.perform(post("/schedules/schedule-job")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(job))).andDo(print()).andExpect(status().isOk());

        var jobs = service.getAllScheduledJobs();
        assertThat(jobs.size()).isEqualTo(1);
    }

}