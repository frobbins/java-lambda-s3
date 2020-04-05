package com.frank.aws.mylambdaproject;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseApp {

    public static final String ENV_S3_BUCKET_NAME = "S3_BUCKET_NAME";

    private AmazonS3 amazonS3 = null;
    private LambdaLogger logger;
    private Context context;

    private String s3BucketName;
    private String s3FileName;

    abstract OutputBean doWork();

    public OutputBean handleRequest(S3Event event, Context context) {
//        s3BucketName = System.getenv(ENV_S3_BUCKET_NAME);
        setContext(context);
        setLogger(context.getLogger());
        S3EventNotification.S3EventNotificationRecord record=event.getRecords().get(0);
        setS3BucketName(record.getS3().getBucket().getName());
        setS3FileName(record.getS3().getObject().getKey());
        listFiles(getListOfFilesFromS3());
        OutputBean outputBean = doWork();
        return outputBean;
    }

    protected void setS3BucketName(String name) {
        s3BucketName = name;
    }

    protected void setS3FileName(String fileName) {
        s3FileName = fileName;
    }

    protected String getS3BucketName() {
        return s3BucketName;
    }

    protected String getS3FileName() {
        return s3FileName;
    }

    protected LambdaLogger getLambdaLogger() {
        return logger;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected Collection<String> getListOfFilesFromS3() {
        AmazonS3 s3Client = getS3Client();
        ListObjectsV2Result result = s3Client.listObjectsV2(getS3BucketName());

        List<String> payloads = new LinkedList<>();
        for( S3ObjectSummary summary : result.getObjectSummaries()) {
            payloads.add(summary.getKey());
        }
        return payloads;
    }

    protected void listFiles(Collection<String> filenames) {
        for( String name : filenames ) {
            log("File found: " + name);
        }
    }

    protected void setLogger(LambdaLogger value) {
        logger = value;
    }

    protected AmazonS3 getS3Client() {
        if (amazonS3 == null ) {
            amazonS3 = AmazonS3ClientBuilder.standard().
                    withRegion(Regions.US_EAST_2).
                    build();
        }
        return amazonS3;
    }

    protected void setS3Client(AmazonS3 value) {
        amazonS3 = value;
    }

    protected void log(String value) {
        logger.log(value);
    }

    protected String getStringFromS3Bucket(String filename) {
        log("Fetching file contents!");
        AmazonS3 s3Client = getS3Client();
        String contents = null;
        String bucket = getS3BucketName();
        log("Lambda triggered with bucket: " + bucket + " and filename: " + filename);
        if (s3Client.doesObjectExist(bucket, filename)) {
            log(" S3 file " + filename + " found.");
            contents = s3Client.getObjectAsString(bucket, filename);
        } else {
            throw new AmazonS3Exception(" Failed to get string from S3 bucket: " + bucket + " File Key: " + filename);
        }
        return contents;
    }

    protected void logStackTrace(Exception ex) {
        StringWriter swriter = new StringWriter();
        PrintWriter writer = new PrintWriter(swriter);
        ex.printStackTrace(writer);
        log(swriter.toString());
    }
}
