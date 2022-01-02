package com.ywook.calendar.scheduler;

import com.ywook.calendar.jobs.JobConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class JobScheduler {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobConfiguration jobConfiguration;

    @Scheduled(fixedDelay = 50000)
    public void run() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        LocalDate now = LocalDate.now();

        JobParameters parameters = new JobParametersBuilder()
                                .addString("year", String.valueOf(now.getYear()))
                                .addString("month", String.valueOf(now.getMonthValue()))
                                .addLong("uniqueness", System.nanoTime())
                                .toJobParameters();

        //jobLauncher.run(createConcertScheduleJob, parameters);

        jobLauncher.run(jobConfiguration.createScheduleJob(), parameters);


    }

}
