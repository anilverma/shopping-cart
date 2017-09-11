package com.shoppingCart.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.shopping.cart.app.ShoppingCartApplication;
import com.shopping.cart.app.config.HibernateConfig;
import com.shopping.cart.app.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringApplicationConfiguration(classes = {ShoppingCartApplication.class,HibernateConfig.class})
@Transactional(transactionManager="txMgr")
@WebAppConfiguration
public class ShoppingCartApplicationTests {

	private	Customer customer = new Customer();	
	@Autowired
    private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
	            MediaType.APPLICATION_JSON.getSubtype(),
	            Charset.forName("utf8"));

	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
		customer.setFirstName("user3");
		customer.setLastName("user3");
		customer.setUsername("user3");
		customer.setPassword("user3");
	}
	
	@Test
	public void addCustomer() {	
		try {
			String customerJson = json(customer);
			this.mockMvc.perform(post("/users")
			            .contentType(contentType)
			            .content(customerJson))
			            .andExpect(status().isCreated());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	    this.mappingJackson2HttpMessageConverter.write(
	            o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	    return mockHttpOutputMessage.getBodyAsString();
	}
	
}
