package com.ywook.calendar.jobs;

import com.ywook.calendar.tasklet.loadScheduleTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    public Job createScheduleJob(){
        return jobBuilderFactory.get("createScheduleJob")
                                .start(loadScheduleStep())
                                .build();
    }

    @Bean
    @JobScope
    public Step loadScheduleStep( ) {
        return stepBuilderFactory.get("loadScheduleStep")
                                .tasklet(new loadScheduleTasklet())
                                .build();
    }


}
