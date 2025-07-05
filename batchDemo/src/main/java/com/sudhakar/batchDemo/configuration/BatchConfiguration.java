package com.sudhakar.batchDemo.configuration;

import com.sudhakar.batchDemo.decider.MyJobExecutionDecider;
import com.sudhakar.batchDemo.listener.MyStepExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {
    @Bean
    public StepExecutionListener myStepExecutionListener(){
        return new MyStepExecutionListener();
    }
    @Bean
    public JobExecutionDecider myJobExecutionDecider(){
       return new MyJobExecutionDecider();
    }
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Step1", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hi First Step1");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }
    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Step2", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        boolean isSuccess=false;
                        if(isSuccess) {
                            throw new Exception("Test Exception");
                        }
                        System.out.println("Hi First Step2");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }
    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Step3", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hi First Step3");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

    @Bean
    public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Step4", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hi First Step4");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }
    @Bean
    public Job firstJob(JobRepository jobRepository, Step step1, Step step2,Step step3) {
        return new JobBuilder("job1", jobRepository)
                .start(step1)
                .on("COMPLETED").to(myJobExecutionDecider()).on("TEST_STATUS1").
                to(step2)
                .from(myJobExecutionDecider()).on("*").to(step3)
                .end()
                .build();
    }

}
