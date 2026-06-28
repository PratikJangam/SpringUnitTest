package com.springpractice.UnitTesting.UnitTestApplication;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@Slf4j
class UnitTestApplicationTests {


	@BeforeEach
	void setUp() {
		log.info("Calling Before Each method will call before every test method");
	}


	@AfterEach
	void tearDown(){
		log.info("Calling Tear down method will call after every test method");
	}

	@BeforeAll
	static void setUpOnce(){
		log.info("Calling set up once method, will call once before all test cases");
	}

	@AfterAll
	static void tearDownOnce(){
		log.info("Calling Tear down method will call once after all test case");
	}

	@Test
	//@DisplayName("TestCaseNumberOne")
	void testNumberOne(){
//		log.info("Running test case one");
		int a = 5;
		int b = 3;
		int result = addTwoNumber(a, b);

//		Assertions.assertEquals(8, result); // from JUnit API
		// From AssertJ
		Assertions.assertThat(result).isEqualTo(8)
				.isCloseTo(9, Offset.offset(1));
	}

	@Test
	@DisplayName("Test case Number two")
	void testNumberTwo(){
		log.info("Running test case two");
	}

	@Test
	void testDivideTwoNumbers_whenDenominatorIsZero_ThenArithmeticException(){
		int a = 5;
		int b = 0;
		//divideTwoNumber(a, b);

		Assertions.assertThatThrownBy(() -> divideTwoNumber(a, b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("/ by zero");
	}

	int addTwoNumber(int a, int b){
		return a+b;
	}

	double divideTwoNumber(int a, int b){
		try {
			return a/b;
		} catch (Exception e) {
			log.error("Arithmetic Exception occurred: "+ e.getLocalizedMessage());
			throw new ArithmeticException(e.getLocalizedMessage());
		}
	}

}









