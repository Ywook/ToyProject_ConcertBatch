package com.ywook.calendar.jobs;

import com.ywook.calendar.domain.dto.base.ConcertBaseDto;
import com.ywook.calendar.domain.dto.enums.ConcertType;
import com.ywook.calendar.domain.mapper.ScheduleExcelRowMapper;
import com.ywook.calendar.tasklet.loadScheduleTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;

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
                .<ConcertBaseDto, ConcertBaseDto>chunk(10).reader(htmlFileReader())
                .writer(customItemWriter()).build();
    }

    @Bean
    public FlatFileItemReader<ConcertBaseDto> htmlFileReader() {
        FlatFileItemReader<ConcertBaseDto> reader = new FlatFileItemReader<>();
        //reader.setLinesToSkip(1);
        DefaultLineMapper<ConcertBaseDto> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter("#");

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new FieldSetMapper<ConcertBaseDto>() {
            @Override
            public ConcertBaseDto mapFieldSet(FieldSet fieldSet) throws BindException {
                ConcertBaseDto concert = new ConcertBaseDto();

                concert.setConcertVenue(fieldSet.readString(0));
                //concert.setConcertType(ConcertType.valueOf(fieldSet.readString(1)));
                concert.setConcertName(fieldSet.readString(2));
                //concert.setConcertDate(LocalDateTime.parse(fieldSet.readString(3)));
                concert.setConcertHall(fieldSet.readString(4));


                return concert;
            }
        });

        reader.setResource(new ClassPathResource("data/monthSchedule.csv"));
        reader.setLineMapper(defaultLineMapper);


        return reader;
    }

    /*
    @Bean
    public Step htmlFileToDatabaseStep() {
        return stepBuilderFactory.get("htmlFileToDatabaseStep")
                .<ConcertBaseDto, ConcertBaseDto>chunk(10).reader(htmlFileReader())
                .writer(customItemWriter()).build();
    }

*/
    @Bean
    public ItemWriter<ConcertBaseDto> customItemWriter() {
        return items -> {
            items.forEach(System.out::println);
        };
    }
}
