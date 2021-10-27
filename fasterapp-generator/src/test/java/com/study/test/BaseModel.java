package com.study.test;

import javax.persistence.Id;
import java.io.Serializable;

public class BaseModel<T extends Serializable> {
    @Id
    private T id;
}
