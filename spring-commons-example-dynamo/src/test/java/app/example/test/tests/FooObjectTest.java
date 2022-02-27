package app.example.test.tests;

import app.example.test.utils.AbstractTest;
import com.app.example.model.FooObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static app.example.test.utils.JsonUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FooObjectTest extends AbstractTest {

	@Test
	public void createFooObject() throws Exception {
		FooObject request = new FooObject();
		request.setValue("value");
		mockMvc.perform(post("/fooObjects")
				.header("X-TRACE-ID", UUID.randomUUID().toString())
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.value").value("value"))
				.andExpect(status().isCreated());
	}

	@Test
	public void selectObjects() throws Exception {
		createFooObject();
		mockMvc.perform(get("/fooObjects")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
				.andExpect(status().isOk());
	}
}
