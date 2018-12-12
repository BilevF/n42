package com.bilev.controller.jsp;

import com.bilev.configuration.AppConfig;
import com.bilev.configuration.HibernateConfiguration;
import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.model.Contract;
import com.bilev.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;
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
public class ContractControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ModelMapper modelMapper;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    public static class MockSecurityContext implements SecurityContext {

        private static final long serialVersionUID = -1386535243513362694L;

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }


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
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .addFilters(this.springSecurityFilterChain)
                .build();

    }


    @Test
    public void testNewContractPage() throws Exception {
        BasicTariffDto tariff1 = modelMapper.map(
                tariffDao.getByKey(1),
                BasicTariffDto.class);
        BasicTariffDto tariff2 = modelMapper.map(
                tariffDao.getByKey(2),
                BasicTariffDto.class);


        mockMvc.perform(get("/contract/new").param("userId", "1").principal(getPrincipal("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tariffs", hasItem(tariff1)))
                .andExpect(model().attribute("tariffs", hasItem(tariff2)))
                .andExpect(view().name("createContract"));
    }



    @Test
    public void testContractPage() throws Exception {


        UsernamePasswordAuthenticationToken principal =
                this.getPrincipal("admin");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(principal));

        Contract contract = contractDao.getByKey(1);

        mockMvc.perform(get("/contract").param("contractId", "1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("contract"))
                .andExpect(model().attribute("contract", hasProperty("phoneNumber", is(contract.getPhoneNumber()))));
    }


    @Test
    public void testAddNewOptionPage() throws Exception {


        UsernamePasswordAuthenticationToken principal =
                this.getPrincipal("admin");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(principal));

        Contract contract = contractDao.getByKey(2);

        BasicOptionDto option1 = modelMapper.map(
                optionDao.getByKey(1),
                BasicOptionDto.class);

        mockMvc.perform(get("/contract/basket").param("contractId", "2").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("optionsBasket"))
                .andExpect(model().attribute("contract", hasProperty("phoneNumber", is(contract.getPhoneNumber()))))
                .andExpect(model().attribute("availableOptions", hasItem(option1)));
    }



}
