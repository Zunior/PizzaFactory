package com.example.PizzaHut;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.startsWith;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.Test;

class HamcrestAsserts {

	@Test
	void test() {
		List<Integer> numbers = Arrays.asList(12, 15, 45);
		
		assertThat(numbers, hasSize(3));
		assertThat(numbers, hasItems(12, 45));
		assertThat(numbers, everyItem(greaterThan(10)));
		assertThat("", isEmptyString());
		assertThat("ABCD", containsString("BC"));
		assertThat("ABCD", startsWith("ABC"));
	}

}
