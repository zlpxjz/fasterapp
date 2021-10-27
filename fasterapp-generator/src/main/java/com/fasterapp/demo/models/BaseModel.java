package com.fasterapp.demo.models;

import javax.persistence.Column;
import javax.persistence.Id;

public class BaseModel<T> {
    @Id
    @Column(name="id", columnDefinition = "Integer(11) not null")
    private T id;
}
