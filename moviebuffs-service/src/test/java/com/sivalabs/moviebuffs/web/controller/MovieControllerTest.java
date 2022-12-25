package com.sivalabs.moviebuffs.web.controller;

import com.sivalabs.moviebuffs.common.AbstractMvcUnitTest;
import com.sivalabs.moviebuffs.core.entity.Movie;
import com.sivalabs.moviebuffs.core.service.MovieService;
import com.sivalabs.moviebuffs.web.mappers.MovieDTOMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = MovieController.class)
class MovieControllerTest extends AbstractMvcUnitTest {

	@MockBean
	private MovieService movieService;

	@SpyBean
	private MovieDTOMapper movieDTOMapper;

	@Test
	@WithMockUser
	void shouldShowHomePage() throws Exception {
		Page<Movie> page = new PageImpl<>(new ArrayList<>());
		given(movieService.findMovies(any(Pageable.class))).willReturn(page);
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

}
