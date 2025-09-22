package com.scm.batch;

import com.scm.entities.Contact;
import com.scm.repsitories.ContactRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private final ContactRepo contactRepository;

    public BatchConfig(ContactRepo contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Contact> reader(@Value("#{jobParameters['filePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<Contact>()
                .name("contactItemReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names("name", "email","phoneNumber","address","description","websiteLink","linkedInLink","favorite")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Contact.class);
                }})
                .build();
    }

    @Bean
    public ContactItemProcessor processor() {
        return new ContactItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<Contact> writer() {
        RepositoryItemWriter<Contact> writer = new RepositoryItemWriter<>();
        writer.setRepository(contactRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step importStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                           FlatFileItemReader<Contact> reader) {
        return new StepBuilder("importStep", jobRepository)
                .<Contact, Contact>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job importContactJob(JobRepository jobRepository, Step importStep) {
        return new JobBuilder("importContactJob", jobRepository)
                .start(importStep)
                .build();
    }
}
