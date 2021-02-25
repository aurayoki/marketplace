package com.jm.marketplace.config.quartzconfig;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class JobConfiguration {

    /**
     * создание бина для работы с классом и его идентификация
     */

    @Bean
    public JobDetail jobBirthdayBean() {
        return JobBuilder
                .newJob(BirthdayCongratulation.class).withIdentity("BirthdayCongratulation")
                .storeDurably().storeDurably().build();
    }

    /**
     * создание бина планировщика, который запускается в определенное время 12 00 соответствует триггеру "0 0 12 * * ?"
     */

    @Bean
    public Trigger triggerBirthdayBean() {
        return newTrigger().forJob(jobBirthdayBean())
                .withIdentity("BirthdayCongratulationTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobRemovalOfAnAdvertisementFromPublicationBean() {
        return JobBuilder
                .newJob(RemovalOfAnAdvertisementFromPublication.class).withIdentity("RemovalOfAnAdvertisementFromPublication")
                .storeDurably().build();
    }

    @Bean
    public Trigger triggerRemovalOfAnAdvertisementFromPublicationBean() {
        return newTrigger().forJob(jobRemovalOfAnAdvertisementFromPublicationBean())
                .withIdentity("RemovalOfAnAdvertisementFromPublicationTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobDiscountBean() {
        return JobBuilder
                .newJob(OneTimeDiscount.class).withIdentity("OneTimeDiscount")
                .storeDurably().storeDurably().build();
    }

    @Bean
    public SimpleTrigger simpleTriggerForOneTimeDiscount() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.FEBRUARY, 21, 22, 23);
        return (SimpleTrigger) newTrigger()
                .forJob(jobDiscountBean())
                .withIdentity("OneTimeDiscount")
                .startAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .build();
    }
}
