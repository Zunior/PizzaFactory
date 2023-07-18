package com.example.PizzaHut;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

class JsonPathTest {

	@Test
	void test() {
		String responseFromService = "[\r\n"
				+ "{\"id\":1001, \"name\":\"Pencil\", \"quantity\":5},\r\n"
				+ "{\"id\":1002, \"name\":\"Pen\", \"quantity\":15},\r\n"
				+ "{\"id\":1003, \"name\":\"Eraser\", \"quantity\":10}\r\n"
				+ "]";
		
		DocumentContext context = JsonPath.parse(responseFromService);
		int length = context.read("$.length()");
		assertThat(length).isEqualTo(3);
		
		List<Integer> ids = context.read("$..id");
		assertThat(ids).containsExactly(1001, 1002, 1003);
		
		System.out.println(context.read("$.[1]").toString());
		System.out.println(context.read("$.[0:1]").toString());
//		System.out.println(context.read("$.[?(@.name='Eraser')]").toString());
//		System.out.println(context.read("$.[?(@.quantity=5)]").toString());
	}

}
