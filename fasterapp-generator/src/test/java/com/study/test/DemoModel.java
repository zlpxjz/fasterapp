package com.study.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Demo")
@Table(name= "t_demo_info")
public class DemoModel extends BaseModel<Integer> {
    @Column(name="name", columnDefinition="varchar(32)")
    private String name;
}
