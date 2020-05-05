package com.frank.aws.mylambdaproject.employees;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.frank.aws.mylambdaproject.Dessert;
import com.frank.aws.mylambdaproject.Teenager;

public class ParlorHost extends Teenager implements RequestHandler<String, Dessert> {

    @Override
    public Dessert handleRequest(String order, Context context) {
        return new Dessert(order);
    }

}
