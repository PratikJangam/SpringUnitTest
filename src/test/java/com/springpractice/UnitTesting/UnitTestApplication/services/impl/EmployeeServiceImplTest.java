package com.springpractice.UnitTesting.UnitTestApplication.services.impl;

import com.springpractice.UnitTesting.UnitTestApplication.TestContainerConfiguration;
import com.springpractice.UnitTesting.UnitTestApplication.dto.EmployeeDto;
import com.springpractice.UnitTesting.UnitTestApplication.entities.Employee;
import com.springpractice.UnitTesting.UnitTestApplication.repositories.EmployeeRepository;
import com.springpractice.UnitTesting.UnitTestApplication.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setUp(){
        mockEmployee = Employee.builder()
                .id(1L)
                .email("pratik@gmail.com")
                .name("pratik")
                .salary(100L)
                .build();

        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto(){
//        assign
        Long id = mockEmployee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.ofNullable(mockEmployee));
//        act

        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

//        assert
        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository).findById(id);
    }

    @Test
    void testCreateNewEmployee_whenValidEmployee_thenCreateNewEmployee(){
//        assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
//        act
        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);
//        assert


        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());

        Employee capEmployee = employeeArgumentCaptor.getValue();
        assertThat(capEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());

    }


}