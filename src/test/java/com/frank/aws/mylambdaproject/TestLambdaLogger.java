package com.frank.aws.mylambdaproject;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class TestLambdaLogger implements LambdaLogger {
    @Override
    public void log(String s) {
        System.out.println(s);
    }

    @Override
    public void log(byte[] bytes) {
        System.out.println(new String(bytes));
    }
}
