package com.example.springbatchwithgraalvm.job.config;

import lombok.Data;

@Data
public class ProcessItem {
    String value="";

    public String showValue() {
        return value;
    }
}
