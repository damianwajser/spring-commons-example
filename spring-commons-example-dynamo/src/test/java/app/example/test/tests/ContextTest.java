package app.example.test.tests;

import app.example.test.utils.AbstractTest;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContextTest extends AbstractTest {

	@Test
	public void registrationWorksThroughAllLayers() throws Exception {

		mockMvc.perform(get("/dynamo-tables")
				.contentType("application/json")).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.table_names").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.table_names").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.table_names").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.table_names").value("FooObjectTable"))
				.andExpect(status().isOk());

	}

}
