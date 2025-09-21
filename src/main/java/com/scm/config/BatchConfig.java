package com.scm.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

//    @Bean
//    public Job jobBean(JobRepository jobRepository){
//        return new JobBuilder("job", jobRepository)
//                .listener(listener)
//                .start(steps)
//                .build();
//    }
}
