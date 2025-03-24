package io.linhhn.suc.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.linhhn.suc.core.dto.UrlDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class SucCoreApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testPing() throws Exception {
		mockMvc.perform(get("/api/ping"))
				.andExpect(status().isOk())
				.andExpect(content().string("pong"));
	}

	@Test
	public void testShortenUrl_WithAlias_OK() throws Exception {
		UrlDto urlDto = new UrlDto("https://hnl.com", "thisisalias");

		mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(urlDto)))
						.andExpect(status().isOk())
						.andExpect(content().string("thisisalias"));
	}

	@Test
	public void testLongUrl_WithoutAlias_OK() throws Exception {
		UrlDto urlDto = new UrlDto();
		urlDto.setLongUrl("https://hnl.com");

		mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(urlDto)))
						.andExpect(status().isOk());
	}
}
