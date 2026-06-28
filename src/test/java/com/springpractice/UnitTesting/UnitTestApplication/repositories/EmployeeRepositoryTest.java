package com.springpractice.UnitTesting.UnitTestApplication.repositories;

import com.springpractice.UnitTesting.UnitTestApplication.TestContainerConfiguration;
import com.springpractice.UnitTesting.UnitTestApplication.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@Import(TestContainerConfiguration.class)
@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .name("Pratik")
                .email("pratik@gmail.com")
                .salary(100L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {
//        Arrange, given
        employeeRepository.save(employee);

//        Act, When
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

//        Assert, Then
        assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList(){

//        Given
        String email = "notPresnt@gmail.com";
//        when
        List<Employee> employeeList = employeeRepository.findByEmail(email);
//        then
        assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isEmpty();
    }




}