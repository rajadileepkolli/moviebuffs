package com.sivalabs.moviebuffs.web.api;

import com.sivalabs.moviebuffs.common.AbstractIntegrationTest;
import com.sivalabs.moviebuffs.core.entity.Order;
import com.sivalabs.moviebuffs.core.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.sivalabs.moviebuffs.common.TestConstants.ORDERS_COLLECTION_BASE_PATH;
import static com.sivalabs.moviebuffs.common.TestConstants.ORDERS_SINGLE_BASE_PATH;
import static com.sivalabs.moviebuffs.datafactory.TestDataFactory.createOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = { "application.import-tmdb-data=false" })
class OrderRestControllerIT extends AbstractIntegrationTest {

	@Autowired
	private OrderRepository orderRepository;

	private List<Order> orderList = null;

	@BeforeEach
	void setUp() {
		// Clear existing orders
		orderRepository.deleteAll();
		orderList = new ArrayList<>();
		orderList.add(createOrder());
		orderList.add(createOrder());
		orderList.add(createOrder());

		orderList = orderRepository.saveAll(orderList);
	}

	@Test
	@WithMockUser(value = "admin@gmail.com", roles = { "USER", "ADMIN" })
	void should_fetch_all_orders() throws Exception {
		this.mockMvc.perform(get(ORDERS_COLLECTION_BASE_PATH))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(orderList.size())));
	}

	@Test
	@WithMockUser(value = "admin@gmail.com")
	void should_fetch_order_by_id() throws Exception {
		Order order = this.orderList.get(0);

		this.mockMvc.perform(get(ORDERS_SINGLE_BASE_PATH, order.getOrderId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.orderId", is(order.getOrderId())));
	}

	@Test
	@WithMockUser
	void should_create_new_order() throws Exception {
		Order order = this.orderList.get(0);

		this.mockMvc
			.perform(post(ORDERS_COLLECTION_BASE_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(order)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.orderId", notNullValue()))
			.andExpect(jsonPath("$.orderStatus", is(Order.OrderStatus.NEW.name())));

	}

	@Test
	@WithMockUser(value = "admin@gmail.com")
	void should_cancel_order() throws Exception {
		Order order = orderList.get(0);
		// order.getCreatedBy().setId(1L);
		String orderId = order.getOrderId();

		this.mockMvc.perform(delete(ORDERS_SINGLE_BASE_PATH, orderId)).andExpect(status().isOk());
	}

}
