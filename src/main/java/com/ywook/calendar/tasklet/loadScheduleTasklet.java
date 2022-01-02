package com.ywook.calendar.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

@Slf4j
public class loadScheduleTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        StepExecution stepExecution = contribution.getStepExecution();
        JobParameters jobParameters = stepExecution.getJobParameters();

        StringBuilder FILE_URL = new StringBuilder();
        String FILE_NAME = "monthSchedule.csv";

        FILE_URL.append("https://www.sac.or.kr/site/main/program/getProgramCalListExcel?searchYear=");
        FILE_URL.append(jobParameters.getString("year"));
        FILE_URL.append("&CATEGORY_PRIMARY=2&searchMonth=");
        FILE_URL.append(jobParameters.getString("month"));

        log.info("URL :  "+ FILE_URL.toString());
        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL.toString()).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            contribution.setExitStatus(ExitStatus.FAILED);
        }

        return RepeatStatus.FINISHED;
    }

}
