package com.frank.aws.mylambdaproject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

public class AppTest {

//    private App app;
//
//    @Mock
//    S3Event s3Event;
//
//    @Mock
//    Context context;
//
//    @Mock
//    AmazonS3 s3Client;
//
//    @Mock
//    ListObjectsV2Result s3Results;
//
//    @Mock
//    S3EventNotification.S3Entity s3Entity;
//
//    @Mock
//    S3EventNotification.S3EventNotificationRecord notificationRecord;
//
//    @Mock
//    S3EventNotification.S3BucketEntity bucket;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        app = new App();
//        app.setS3Client(s3Client);
//        app.setContext(context);
//        app.setS3BucketName("SandBucket");
//    }
//
//    @Test
//    public void testHandleRequest() {
//        prepareContext();
//
//        OutputBean outputBean = app.handleRequest(s3Event, context);
//        System.out.println(outputBean);
//
//    }
//
//    private void prepareContext() {
//
//        List<S3EventNotification.S3EventNotificationRecord> records = new LinkedList<>();
//        records.add(notificationRecord);
//        when(context.getLogger()).thenReturn(new TestLambdaLogger());
//        when(s3Event.getRecords()).thenReturn(records);
//        when(bucket.getName()).thenReturn("SandBucket");
//        when(s3Entity.getBucket()).thenReturn(bucket);
//        when(notificationRecord.getS3()).thenReturn(s3Entity);
//        when(s3Client.listObjectsV2("SandBucket")).thenReturn(s3Results);
//        when(s3Results.getObjectSummaries()).thenReturn(getS3ObjectSummaries());
//    }
//
//    private List<S3ObjectSummary> getS3ObjectSummaries() {
//        List<S3ObjectSummary> summaries = new LinkedList<>();
//        S3ObjectSummary summary = new S3ObjectSummary();
//        summary.setBucketName("SandBucket");
//        summary.setKey("MyFileName");
//        summaries.add(summary);
//        return summaries;
//    }
}