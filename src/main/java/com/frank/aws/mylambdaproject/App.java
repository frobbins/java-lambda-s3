package com.frank.aws.mylambdaproject;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.io.StringWriter;

public class App implements RequestHandler<S3Event, String> {

    private AmazonS3 amazonS3;
    private String s3BucketName;
    private LambdaLogger logger;
    private String s3FileName;

    public String handleRequest(S3Event event, Context context) {
        Dessert dessert = null;
        String response = null;
        initialize(event, context);
        try {
            String jsonString = getStringFromS3Bucket();
            Gson g = new Gson();
            dessert = g.fromJson(jsonString, Dessert.class);
            response = g.toJson(dessert);
        } catch ( Exception ex ) {
            logStackTrace(ex);
            log("Error happened: " + ex.getMessage());
        }
        log(dessert.getName());
        log(dessert.getPhone());
        log(dessert.toString());
        initStepFunction(dessert, "arn:aws:states:us-east-1:827646740775:stateMachine:MyIceCreamStateMachine", context);
        return response;
    }

    private void initialize(S3Event event, Context context) {
        logger = context.getLogger();
        amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        S3EventNotification.S3EventNotificationRecord record=event.getRecords().get(0);
        s3BucketName = record.getS3().getBucket().getName();
        s3FileName = record.getS3().getObject().getKey();
    }

    protected String getStringFromS3Bucket() {
        log("Fetching file contents!");
        String contents = "None found.";
        log("Lambda triggered with bucket: " + s3BucketName + " and filename: " + s3FileName);
        if (amazonS3.doesObjectExist(s3BucketName, s3FileName)) {
            log(" S3 file " + s3FileName + " found.");
            contents = amazonS3.getObjectAsString(s3BucketName, s3FileName);
        } else {
            throw new AmazonS3Exception(" Failed to get string from S3 bucket: " + s3BucketName + " File Key: " + s3FileName);
        }
        return contents;
    }

    protected void log(String value) {
        logger.log(value);
    }

    protected void logStackTrace(Exception ex) {
        StringWriter swriter = new StringWriter();
        PrintWriter writer = new PrintWriter(swriter);
        ex.printStackTrace(writer);
        log(swriter.toString());
    }

    protected void initStepFunction(Dessert dessert, String stepFunctionId, Context context) {
        AWSStepFunctions client = AWSStepFunctionsClientBuilder.defaultClient();

        ObjectMapper jsonMapper = new ObjectMapper();

        StartExecutionRequest request = new StartExecutionRequest();
        request.setStateMachineArn(stepFunctionId);

        try {
            request.setInput(jsonMapper.writeValueAsString(dessert));
        } catch (JsonProcessingException ex) {
            logStackTrace(ex);
            throw new AmazonServiceException("Error in [" + context.getFunctionName() + "]", ex);
        }

        logger.log("INFO: Step Function [" + request.getStateMachineArn() + "] will be called with [" + request.getInput() + "]");

        StartExecutionResult result = client.startExecution(request);

        logger.log("INFO: Output Function [" + context.getFunctionName() + "], Result [" + result.toString() + "]");
    }

}
