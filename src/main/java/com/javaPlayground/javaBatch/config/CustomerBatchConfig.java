//package com.javaPlayground.javaBatch.config;
//
//import com.javaPlayground.javaBatch.entity.Customer;
//import com.javaPlayground.javaBatch.repository.CustomerRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.LineMapper;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//public class CustomerBatchConfig {
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    private LineMapper<Customer> lineMapper() {
//        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
//
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames("id", "first_name", "last_name", "email", "gender", "contact_number", "dob", "country");
//
//        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(Customer.class);
//
//        lineMapper.setLineTokenizer(lineTokenizer);
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//
//        return lineMapper;
//    }
//
//    @Bean
//    public FlatFileItemReader<Customer> reader(){
//        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new FileSystemResource("src/main/resources/employee_large_dataset.csv"));
//        itemReader.setName("csv-reader");
//        itemReader.setLinesToSkip(1);
//        itemReader.setLineMapper(lineMapper());
//        return itemReader;
//    }
//
//    @Bean
//    public CustomerProcessor processor(){
//        return new CustomerProcessor();
//    }
//
//    @Bean
//    public RepositoryItemWriter<Customer> writer(){
//        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
//        writer.setRepository(customerRepository);
//        writer.setMethodName("save");
//
//        return writer;
//    }
//
//    @Bean
//    public Step stepOne(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        return new StepBuilder("csv-step", jobRepository)
//                .<Customer, Customer>chunk(1000, transactionManager)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public Job runJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        return new JobBuilder("import-customers", jobRepository)
//                .flow(stepOne(jobRepository, transactionManager))
//                .end()
//                .build();
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor(){
//        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
//        taskExecutor.setConcurrencyLimit(10);
//        return taskExecutor;
//    }
//
//}
