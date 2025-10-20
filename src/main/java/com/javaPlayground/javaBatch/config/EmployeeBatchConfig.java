package com.javaPlayground.javaBatch.config;

import com.javaPlayground.javaBatch.dto.EmployeeDTO;
import com.javaPlayground.javaBatch.entity.Employee;
import com.javaPlayground.javaBatch.partition.EmployeePartitioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EmployeeBatchConfig {
    @Autowired
    private EmployeeWriter employeeWriter;
    private static final Logger log = LoggerFactory.getLogger(EmployeePartitioner.class);

    private LineMapper<EmployeeDTO> lineMapper() {
        DefaultLineMapper<EmployeeDTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "age", "salary", "department");

        BeanWrapperFieldSetMapper<EmployeeDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EmployeeDTO.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public EmployeePartitioner partitioner() {
        return new EmployeePartitioner(1, 100001);
    }

    @Bean
    public PartitionHandler partitionHandler(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setGridSize(16);
        partitionHandler.setTaskExecutor(taskExecutor());
        partitionHandler.setStep(slaveStep(jobRepository, transactionManager));

        return partitionHandler;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDTO> reader() {
        FlatFileItemReader<EmployeeDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/employee_large_dataset.csv"));
        itemReader.setName("csv-reader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    public EmployeeProcessor processor() {
        return new EmployeeProcessor();
    }

    @Bean
    public Step slaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("slave-step", jobRepository)
                .<EmployeeDTO, Employee>chunk(10000, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(employeeWriter)
                .build();
    }

    @Bean
    public Step masterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("master-step", jobRepository)
                .partitioner(slaveStep(jobRepository, transactionManager).getName(), partitioner())
                .partitionHandler(partitionHandler(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("import-employee", jobRepository)
                .flow(masterStep(jobRepository, transactionManager))
                .end()
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        try {
            taskExecutor.setCorePoolSize(14);
            taskExecutor.setMaxPoolSize(16);
            taskExecutor.setQueueCapacity(32);
            taskExecutor.setThreadNamePrefix("Batch-Task-");
            taskExecutor.initialize();
        } catch (Exception e) {
            log.error("Failed to initialize TaskExecutor", e);
            throw e;
        }
        return taskExecutor;
    }
}

