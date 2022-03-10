package com.example.study;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    // private static final long THRESHOLD = 1000L;
    private long THRESHOLD;

    public FindSlowTestExtension(long threshold){
        this.THRESHOLD = threshold;
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        ExtensionContext.Store store = getStore(extensionContext);
        store.put("START_TIME", System.currentTimeMillis());
    }

    private ExtensionContext.Store getStore(ExtensionContext extensionContext) {
        String testClassName = extensionContext.getRequiredTestClass().getName();
        String testMethodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
        return store;
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Method requiredTestMethod = extensionContext.getRequiredTestMethod();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);
        String testMethodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = getStore(extensionContext);
        long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if(duration > THRESHOLD && annotation == null){
            System.out.printf("Please consider mark method [%s] width @SlowTest.\n", testMethodName);
        }
    }
}
