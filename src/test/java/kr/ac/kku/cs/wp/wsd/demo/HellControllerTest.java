package kr.ac.kku.cs.wp.wsd.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * HellControllerTest
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/app-context.xml") 
@WebAppConfiguration
public class HellControllerTest {

	private static final Logger logger = LogManager.getLogger(HellControllerTest.class);
	
	private MockMvc mockMvc;
	
	private boolean isDebugEnabled = true;
	
	@BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/hello").queryParam("name", "ks"))
        .andDo(isDebugEnabled ? MockMvcResultHandlers.print() : result -> {})
        .andExpect(status().isOk())
        .andExpect(request().attribute("hello", "hello ks"));
        
        
        mockMvc.perform(post("/hello").param("name", "post"))
        .andDo(isDebugEnabled ? MockMvcResultHandlers.print() : result -> {})
        .andExpect(status().isOk())
        .andExpect(request().attribute("hello", "hello post"));
       
        
        MvcResult mvcResut =  mockMvc.perform(get("/hello"))
        .andDo(isDebugEnabled ? MockMvcResultHandlers.print() : result -> {})
        .andExpect(status().isBadRequest())
        .andReturn();
        
        logger.debug(mvcResut.getResponse().getContentAsString());
        assertEquals(mvcResut.getResponse().getErrorMessage(), "Required parameter 'name' is not present.");
        
    }
}