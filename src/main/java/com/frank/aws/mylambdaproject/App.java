package com.frank.aws.mylambdaproject;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class App implements RequestHandler<S3Event, String> {

    private AmazonS3 amazonS3;
    private String s3BucketName;
    private LambdaLogger logger;
    private String s3FileName;

    public String handleRequest(S3Event event, Context context) {
        String response = null;
        initialize(event, context);
        try {
            response = getStringFromS3Bucket();
        } catch ( Exception ex ) {
            logStackTrace(ex);
            log("Error happened: " + ex.getMessage());
        }
        return response;
    }

    private void initialize(S3Event event, Context context) {
        logger = context.getLogger();
        amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
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


}
