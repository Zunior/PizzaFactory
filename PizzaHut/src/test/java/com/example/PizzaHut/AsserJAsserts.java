package com.example.PizzaHut;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class AsserJAsserts {

	@Test
	void test() {
		List<Integer> numbers = Arrays.asList(12, 15, 45);
		
		assertThat(numbers)
			.hasSize(3)
			.contains(12, 15)
			.allMatch(x -> x > 10)
			.noneMatch(x -> x < 0);
		
		assertThat("").isEmpty();
		assertThat("ABCD")
			.contains("BC")
			.startsWith("ABC");
		
//		assertThat(numbers, hasSize(3));
//		assertThat(numbers, hasItems(12, 45));
//		assertThat(numbers, everyItem(greaterThan(10)));
//		assertThat("", isEmptyString());
//		assertThat("ABCD", containsString("BC"));
//		assertThat("ABCD", startsWith("ABC"));
	}

}
