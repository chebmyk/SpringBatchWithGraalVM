package com.example.springbatchwithgraalvm.job.config;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.util.ReflectionUtils;

@ImportRuntimeHints(NativeApplicationRuntimeHints.SpringBatchRegistrar.class)
@Configuration
public class NativeApplicationRuntimeHints {

    static class SpringBatchRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            // Example for File Resources Register hints
            hints.resources()
                    .registerPattern("*.csv");
            // Example for Reflection Register hints
            hints.reflection()
                    .registerConstructor(ProcessItem.class.getDeclaredConstructors()[0], ExecutableMode.INVOKE)
                    .registerMethod(ReflectionUtils.findMethod(ProcessItem.class, "showValue"), ExecutableMode.INVOKE);


//                hints.proxies()
//                        .registerJdkProxy(org.springframework.beans.factory.config.BeanExpressionContext.class);
        }
    }
}
