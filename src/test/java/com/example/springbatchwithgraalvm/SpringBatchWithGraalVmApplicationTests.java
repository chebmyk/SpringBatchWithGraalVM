package com.example.springbatchwithgraalvm;

import com.example.springbatchwithgraalvm.job.config.ProcessItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@SpringBootTest
class SpringBatchWithGraalVmApplicationTests {

    @Test
    void contextLoads() {
        Method m = ReflectionUtils.findMethod(ProcessItem.class, "showValue");
        System.out.printf("m");
    }

}
