package com.sivalabs.moviebuffs.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.moviebuffs.MovieBuffsApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.sivalabs.moviebuffs.core.utils.Constants.PROFILE_IT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles({ PROFILE_IT })
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = { MovieBuffsApplication.class })
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest extends AbstractPostGresContainer {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

}
