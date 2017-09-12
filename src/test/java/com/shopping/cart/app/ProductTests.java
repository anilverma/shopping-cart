package com.shopping.cart.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.shopping.cart.app.config.HibernateConfig;
import com.shopping.cart.app.model.Category;
import com.shopping.cart.app.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringApplicationConfiguration(classes = {ShoppingCartApplication.class,HibernateConfig.class})
@Transactional
@WebAppConfiguration
public class ProductTests {

	private	Product product = new Product();	
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
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
		Category category = new Category();
		category.setIdCategory(1);
		product.setCategory(category);
		product.setDescription("product test");
		product.setPrice(new BigDecimal(500));
	}
	
	@Test
	@Rollback(false)
	public void createProductUnAuthorisedAcesss() throws Exception {	
		
		String productJson = json(product);
		this.mockMvc.perform(post("/products")
		            .contentType(contentType)
		            .content(productJson))
		            .andExpect(status().isUnauthorized());	
		
	}
	
	@Test
	@Rollback(false)
	@WithUserDetails("admin")
	public void createProduct() throws Exception {	
		String productJson = json(product);
		this.mockMvc.perform(post("/products")
		            .contentType(contentType)
		            .content(productJson))
		            .andExpect(status().isCreated());		
		
	}
	
	
		

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
	            o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	    return mockHttpOutputMessage.getBodyAsString();
	}
	
}
