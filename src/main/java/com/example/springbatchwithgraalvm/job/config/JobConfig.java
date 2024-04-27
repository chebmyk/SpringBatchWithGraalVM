package com.example.springbatchwithgraalvm.job.config;

import com.example.springbatchwithgraalvm.job.repositories.UsersRepository;
import com.example.springbatchwithgraalvm.model.UserDto;
import com.example.springbatchwithgraalvm.model.Users;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;


@Configuration
@Slf4j
public class JobConfig {

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public Job importVistorsJob(final JobRepository jobRepository, Step step) {
        return  new JobBuilder("importVisitorsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step importVisitorsStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager, FlatFileItemReader flatFileItemReader, final UsersItemWriter itemWriter) throws IOException {
        return new StepBuilder("importVisitorsStep", jobRepository)
                .<ProcessItem, ProcessItem>chunk(100, transactionManager)
                .reader(flatFileItemReader)
                .processor(itemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public ItemProcessor<UserDto, Users> itemProcessor() {
        return new ObjectItemProcessor();
    }

    @Bean
    public UsersItemWriter visitorsItemWriter(UsersRepository usersRepository) {
        return new UsersItemWriter(usersRepository);
    }

//    @Bean
//    public Resource getInputCsvFile(@Value("#{jobParameters['inputFile']}") {
//        Assert.notNull(filePath, "Job Parameter inputCsv is missing");
//        if (filePath == null) {
//            filePath="users.csv";
//        }
//        return resourceLoader.getResource(filePath);
//    }


    @Bean
    @StepScope
    public FlatFileItemReader<UserDto> flatFileItemReader() throws IOException {
        Resource file = new ClassPathResource("users.csv");
        log.info("+++Input file {}", file);
        val flatFileItemReader = new FlatFileItemReader<UserDto>();
        flatFileItemReader.setName("VISITORS_READER");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        flatFileItemReader.setStrict(false);
        flatFileItemReader.setResource(file);
        return flatFileItemReader;
    }


    public LineMapper<UserDto> lineMapper() {
        val defaultLineMapper = new DefaultLineMapper<UserDto>();
        val lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("userId", "firstName", "lastName", "emailAddress", "phoneNumber", "address", "birthDate");
        lineTokenizer.setStrict(false);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new UserFieldSetMapper());
        return defaultLineMapper;
    }

    /*
Workaround for issue https://github.com/spring-projects/spring-batch/issues/4519
Should be fixed in SpringBatch 5.2
*/
//    @Bean
//    public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
//        return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
//    }





}
