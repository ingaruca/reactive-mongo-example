package com.ingaruca.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document
@Getter
@NoArgsConstructor
@Setter
@ToString
public class Employee {

  @Id
  private String id;
  private String name;
  private Long salary;

}
