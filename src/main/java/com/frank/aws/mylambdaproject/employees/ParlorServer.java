package com.frank.aws.mylambdaproject.employees;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frank.aws.mylambdaproject.Dessert;
import com.frank.aws.mylambdaproject.Teenager;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;


public class ParlorServer extends Teenager implements RequestHandler<Dessert, Dessert> {

    @Override
    public Dessert handleRequest(Dessert dessert, Context context) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            setContext(context);
            List<String> names = readFromS3();
            if (CollectionUtils.isNotEmpty(names)) {
                String name = names.get(1);
                getLogger().log(dessert.toString());
                //writeToS3("WINNER!!!-"+ name + ".json", mapper.writeValueAsString(dessert));
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
            getLogger().log("Error: " + ex);
        }
        return dessert;
    }

}
