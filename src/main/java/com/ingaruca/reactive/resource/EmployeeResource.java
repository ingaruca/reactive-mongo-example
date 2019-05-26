package com.ingaruca.reactive.resource;

import com.ingaruca.reactive.model.Employee;
import com.ingaruca.reactive.model.EmployeeEvent;
import com.ingaruca.reactive.repository.EmployeeRepository;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;



@RestController
@RequestMapping("/employees")
public class EmployeeResource {

  private EmployeeRepository employeeRepository;

  public EmployeeResource(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @GetMapping
  public Flux<Employee> getAll() {
    return employeeRepository
            .findAll();
  }

  @GetMapping("/{id}")
  public Mono<Employee> findById(@PathVariable("id") final String id) {
    return employeeRepository.findById(id);
  }

  /**
   * Method that return a list of employee events find by EmployeeId.
   * @param id - Id of employee.
   * @return List of employee event.
   */
  @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<EmployeeEvent> getEvents(@PathVariable("id") final String id) {
    return employeeRepository.findById(id)
            .flatMapMany(employee -> {
              Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
              Flux<EmployeeEvent> employeeEventFlux =
                      Flux.fromStream(
                              Stream.generate(() ->
                                      new EmployeeEvent(employee, new Date()))
                      );

              return Flux.zip(interval, employeeEventFlux)
                      .map(Tuple2::getT2);
            });
  }

}
