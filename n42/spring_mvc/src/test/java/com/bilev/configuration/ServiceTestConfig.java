package com.bilev.configuration;

import com.bilev.dao.api.*;
import com.bilev.dao.impl.UserDaoImpl;
import com.bilev.model.Tariff;
import com.bilev.service.api.MailService;
import com.bilev.service.api.MessageService;
import com.bilev.service.api.UserService;
import com.bilev.service.impl.UserServiceImpl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
        "com.bilev.service",
        "com.bilev.tools"
})
public class ServiceTestConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        return messageSource;
    }

    @Bean
    public ShaPasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }


    @Bean
    public UserDao userDaoService() {
        return Mockito.mock(UserDao.class);
    }

    @Bean
    public RoleDao roleDaoService() {
        return Mockito.mock(RoleDao.class);
    }

    @Bean
    public ContractDao contractDaoService() {
        return Mockito.mock(ContractDao.class/*, Mockito.RETURNS_DEEP_STUBS*/);
    }

    @Bean
    public TariffDao tariffDaoService() {
        return Mockito.mock(TariffDao.class);
    }

    @Bean
    public BlockDao blockDaoService() {
        return Mockito.mock(BlockDao.class);
    }

    @Bean
    public OptionDao optionDaoService() {
        return Mockito.mock(OptionDao.class);
    }

    @Bean
    public HistoryDao historyDaoService() {
        return Mockito.mock(HistoryDao.class);
    }

    @Bean
    public MailService mailService() {
        return Mockito.mock(MailService.class);
    }

    @Bean
    public MessageService messageService() {
        return Mockito.mock(MessageService.class);
    }

}
