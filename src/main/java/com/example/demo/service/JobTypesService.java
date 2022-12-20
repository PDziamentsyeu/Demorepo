package com.example.demo.service;

import com.example.demo.domain.JobDefinition;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class JobTypesService {

    private List<JobDefinition> definitions;
}
