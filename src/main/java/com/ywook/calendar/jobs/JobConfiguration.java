package com.ywook.calendar.jobs;

import com.ywook.calendar.domain.dto.base.ConcertBaseDto;
import com.ywook.calendar.domain.dto.enums.ConcertType;
import com.ywook.calendar.domain.entity.ConcertEntity;
import com.ywook.calendar.repository.mapper.ConcertMapper;
import com.ywook.calendar.tasklet.loadScheduleTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
@Slf4j
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory em;

    @Autowired
    ConcertMapper concertMapper;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory em) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.em = em;
    }

    public Job createScheduleJob(){
        return jobBuilderFactory.get("createScheduleJob")
                                .start(loadScheduleStep())
                                .next(csvFileToDatabaseStep())
                                .build();
    }

    @Bean
    @JobScope
    public Step loadScheduleStep( ) {
        return stepBuilderFactory.get("loadScheduleStep")
                                .tasklet(new loadScheduleTasklet())
                                .build();
    }

    @Bean
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("htmlFileToDatabaseStep")
                .<ConcertBaseDto, ConcertEntity>chunk(10)
                .reader(htmlFileReader())
                .processor(itemProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<ConcertBaseDto> htmlFileReader() {
        FlatFileItemReader<ConcertBaseDto> reader = new FlatFileItemReader<>();
        DefaultLineMapper<ConcertBaseDto> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter("#");

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSet -> {
            ConcertBaseDto concert = new ConcertBaseDto();

            concert.setConcertVenue(fieldSet.readString(0));
            concert.setConcertType(ConcertType.fromString(fieldSet.readString(1)));
            concert.setConcertName(fieldSet.readString(2));
            concert.setConcertDate(LocalDate.parse(fieldSet.readString(3), DateTimeFormatter.ISO_DATE));
            concert.setConcertHall(fieldSet.readString(4));


            return concert;
        });

        reader.setResource(new ClassPathResource("data/monthSchedule.csv"));
        reader.setLineMapper(defaultLineMapper);


        return reader;
    }

    @Bean
    ItemProcessor<ConcertBaseDto, ConcertEntity> itemProcessor() {
        return dto -> concertMapper.toEntity(dto);
    }
    @Bean
    @Transactional
    public JpaItemWriter<ConcertEntity> jpaItemWriter() {
        JpaItemWriter<ConcertEntity> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(em);
        return jpaItemWriter;

    }
}
