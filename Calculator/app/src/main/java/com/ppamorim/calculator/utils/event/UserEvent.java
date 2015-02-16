package com.ppamorim.calculator.utils.event;

import com.activeandroid.Model;
import com.ppamorim.calculator.core.model.BaseModel;
import java.io.Serializable;
import java.util.List;

public class UserEvent implements Serializable {

  private Long id;
  private int status;
  private List<? extends BaseModel> items;

  private String name;
  private int age;

  UserEvent() {}

  public UserEvent(Long id, int status, List<? extends BaseModel> items) {
    this.id = id;
    this.status = status;
    this.items = items;
  }

  public UserEvent(Long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public Long getId() {
    return id;
  }

  public int getStatus() {
    return status;
  }

  public List<? extends BaseModel> getItems() {
    return items;
  }

  public String getName() {
    return name;
  }
  public int getAge() {
    return age;
  }

}
