package com.ingaruca.reactive;

import com.ingaruca.reactive.model.Employee;
import com.ingaruca.reactive.repository.EmployeeRepository;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactiveMongoExample1Application {

  @Bean
  CommandLineRunner employees(EmployeeRepository employeeRepository) {
    return  args -> {
      employeeRepository
              .deleteAll()
              .subscribe(null, null, () -> {
                Stream.of(
                        new Employee(UUID.randomUUID().toString(),"Peter", 23000L),
                        new Employee(UUID.randomUUID().toString(),"Sam", 16000L),
                        new Employee(UUID.randomUUID().toString(),"Ryan", 45000L),
                        new Employee(UUID.randomUUID().toString(),"Chris", 18000L),
                        new Employee(UUID.randomUUID().toString(),"Richard", 35000L))
                        .forEach(employee -> {
                          employeeRepository
                                  .save(employee)
                                  .subscribe(System.out::println);
                        });
              });
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(ReactiveMongoExample1Application.class, args);
  }

}
