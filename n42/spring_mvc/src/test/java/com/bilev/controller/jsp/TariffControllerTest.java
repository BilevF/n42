package com.bilev.controller.jsp;

import com.bilev.configuration.AppConfig;
import com.bilev.configuration.HibernateConfiguration;
import com.bilev.dao.api.TariffDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicTariffDto;
import com.bilev.model.Tariff;
import com.bilev.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, HibernateConfiguration.class})
@WebAppConfiguration
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:04-insert-test-data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:05-clear.sql")
})
@Transactional
public class TariffControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;


    private UsernamePasswordAuthenticationToken getPrincipal(String username) {

        UserDetails user = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());

        return authentication;
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }

    @Test
    public void testNewTariffPage() throws Exception {

        mockMvc.perform(get("/tariff/new").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("editTariff"));
    }

    @Test
    public void testTariffPage() throws Exception {
        Tariff tariff = tariffDao.getByKey(1);

        mockMvc.perform(get("/tariff").param("tariffId", "1").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tariff", hasProperty("name", is(tariff.getName()))))
                .andExpect(model().attribute("tariff", hasProperty("price", is(tariff.getPrice()))))
                .andExpect(model().attribute("tariff", hasProperty("info", is(tariff.getInfo()))))
                .andExpect(view().name("tariff"));
    }

    @Test
    public void testTariffPage_Exception() throws Exception {

        mockMvc.perform(get("/tariff").param("tariffId", "0").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("serverError"));
    }


    @Test
    public void testTariffListPage() throws Exception {
        BasicTariffDto tariff1 = modelMapper.map(
                tariffDao.getByKey(1),
                BasicTariffDto.class);
        BasicTariffDto tariff2 = modelMapper.map(
                tariffDao.getByKey(2),
                BasicTariffDto.class);

        BasicTariffDto tariff3 = modelMapper.map(
                tariffDao.getByKey(3),
                BasicTariffDto.class);



        mockMvc.perform(get("/tariff/list").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tariffs", hasItem(tariff1)))
                .andExpect(model().attribute("tariffs", hasItem(tariff2)))
                .andExpect(model().attribute("tariffs", hasItem(tariff3)))
                .andExpect(model().attribute("path", is("tariff")))
                .andExpect(view().name("tariffs"));
    }

    @Test
    public void testAvailableTariffsPage() throws Exception {
        BasicTariffDto tariff1 = modelMapper.map(
                tariffDao.getByKey(1),
                BasicTariffDto.class);
        BasicTariffDto tariff2 = modelMapper.map(
                tariffDao.getByKey(2),
                BasicTariffDto.class);



        mockMvc.perform(get("/tariff/list/available").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tariffs", hasItem(tariff1)))
                .andExpect(model().attribute("tariffs", hasItem(tariff2)))
                .andExpect(model().attribute("path", is("")))
                .andExpect(view().name("tariffs"));
    }


    @Test
    public void testNewOptionPage() throws Exception {

        mockMvc.perform(get("/tariff/option/new").param("tariffId", "1").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("option", hasProperty("tariffId", is(1))))
                .andExpect(view().name("editOption"));
    }

    @Test
    public void testNewOptionPage_Exception() throws Exception {

        mockMvc.perform(get("/tariff/option/new").param("tariffId", "0").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("serverError"));
    }


    @Test
    public void testReplaceTariffPage() throws Exception {
        BasicTariffDto tariff1 = modelMapper.map(
                tariffDao.getByKey(1),
                BasicTariffDto.class);
        BasicTariffDto tariff2 = modelMapper.map(
                tariffDao.getByKey(2),
                BasicTariffDto.class);



        mockMvc.perform(get("/tariff/replace").param("tariffId", "3").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tariffs", hasItem(tariff1)))
                .andExpect(model().attribute("tariffs", hasItem(tariff2)))
                .andExpect(model().attribute("path", is("/replaceTariff")))
                .andExpect(view().name("tariffs"));
    }
}





















