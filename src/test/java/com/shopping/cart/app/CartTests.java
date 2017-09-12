package com.shopping.cart.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.shopping.cart.app.config.HibernateConfig;
import com.shopping.cart.app.dao.CartDao;
import com.shopping.cart.app.dao.CustomerDao;
import com.shopping.cart.app.model.Cart;
import com.shopping.cart.app.model.Customer;
import com.shopping.cart.app.model.Order;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ShoppingCartApplication.class,HibernateConfig.class})
@Transactional
@WebAppConfiguration
public class CartTests {

	private	Customer customer = new Customer();	
	@Autowired
    private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    CartDao cartDao;
    
    @Autowired
    CustomerDao customerDao;
    
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
	            MediaType.APPLICATION_JSON.getSubtype(),
	            Charset.forName("utf8"));

	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
	}
	
	@Test
	public void createCartUnAuthorisedAcesss() throws Exception {	
		customer = customerDao.findBy("user");
		String customerJson = json(customer);
		this.mockMvc.perform(post("/users/carts?idProduct=1&quantity=1")
		            .contentType(contentType)
		            .content(customerJson))
		            .andExpect(status().isUnauthorized());	
		
	}
	
	@Test
	@WithUserDetails("user")
	public void createCart() throws Exception {	
		customer = customerDao.findBy("user");
		Cart cart = new Cart();
		cart.setCustomer(customer);
		String cartJson = json(cart);
		this.mockMvc.perform(post("/users/carts?idProduct=2&quantity=1")
		            .contentType(contentType)
		            .content(cartJson))
		            .andExpect(status().isCreated());	
		
	}
	
	@Test
	@WithUserDetails("user")
	public void updateCart() throws Exception {	
		customer = customerDao.findBy("user");
		
		Cart cart = new Cart();
		cart.setCustomer(customer);
		String cartJson = json(cart);
		MvcResult result = this.mockMvc.perform(post("/users/carts?idProduct=2&quantity=1")
		            .contentType(contentType)
		            .content(cartJson)).andReturn();
		String headerString[] = result.getResponse().getHeader("location").split("/");
		String cartId = headerString[headerString.length-1];
		this.mockMvc.perform(put("/users/"+customer.getIdCustomer()+"/carts/"+cartId +"/?idProduct=1&quantity=1")
	            .contentType(contentType)
	            .content("{}"))
	            .andExpect(status().isCreated());			
	}
	
	
	@Test
	@WithUserDetails("user")
	public void createOrder() throws Exception {	
		customer = customerDao.findBy("user");
		
		Cart cart = new Cart();
		cart.setCustomer(customer);
		String cartJson = json(cart);
		MvcResult result = this.mockMvc.perform(post("/users/carts?idProduct=2&quantity=1")
		            .contentType(contentType)
		            .content(cartJson)).andReturn();
		String headerString[] = result.getResponse().getHeader("location").split("/");
		String cartId = headerString[headerString.length-1];
		MockHttpServletResponse response =  this.mockMvc.perform(post("/users/"+customer.getIdCustomer()+"/carts/"+cartId)
	            .contentType(contentType)
	            .content("{}"))
	            .andExpect(status().isCreated()).andReturn().getResponse();
		
		
		MockHttpInputMessage mock= new MockHttpInputMessage(response.getContentAsByteArray());
		Order order = (Order) this.mappingJackson2HttpMessageConverter.read(Order.class, mock);
		assertNotNull(order);
		assertEquals(new BigInteger("99"), order.getTotal().toBigInteger());
		
	}
		

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
	            o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	    return mockHttpOutputMessage.getBodyAsString();
	}
	
}
