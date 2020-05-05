package com.frank.aws.mylambdaproject;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Teenager {

    private static final String ENV_BUCKET_NAME = "BUCKET_NAME";
    private static final String ENV_INPUT_FILE_NAME = "INPUT_FILE";
    private static final Regions ENV_REGION = Regions.US_EAST_2;
    private static final String ENV_NUMBER_OF_INGREDIENTS_TO_ADD = "NUMBER_OF_ITEMS_TO_ADD";
    private Context context;

    protected List<String> randomizeListAndReturnSubset(Context context, List<String> items) {

        List<String> subList = new LinkedList<>();
        int itemCount = getNumberOfItemsToAdd(context);
        if (itemCount > 0 &&
                CollectionUtils.isNotEmpty(items) &&
                items.size() >= itemCount ) {
            Collections.shuffle(items);
            subList = items.subList(0, itemCount);
        }
        return subList;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    protected String getBucketName() {
        return System.getenv(ENV_BUCKET_NAME);
    }

    protected Regions getRegion() {
        return ENV_REGION;
    }

    protected String getInputFileName() {
        return System.getenv(ENV_INPUT_FILE_NAME);
    }

    protected int getNumberOfItemsToAdd(Context context) {

        int result = 1;

        getLogger().log("Getting number of items to add.");


        String value = System.getenv(ENV_NUMBER_OF_INGREDIENTS_TO_ADD);
        if ( value != null && NumberUtils.isNumber(value) ) {
            result = Integer.valueOf(value);
        }

        getLogger().log("Number of items = " + result);
        return result;
    }

    protected List<String> readFromS3() throws Exception {

        String bucket = getBucketName();
        String filename = getInputFileName();

        getLogger().log("Getting from s3 bucket: " + bucket + " and file name: " + filename );

        AmazonS3 s3client = AmazonS3ClientBuilder.standard().
                withRegion(getRegion()).
                build();

        S3Object file = s3client.getObject(new GetObjectRequest(bucket, filename));
        return getContents(file.getObjectContent());
    }

    protected List<String> getContents(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        List<String> list = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    protected void writeToS3(String outputFileName, String contents) {
        AmazonS3 s3client = AmazonS3ClientBuilder.standard().
                withRegion(getRegion()).
                build();
        getLogger().log("Writing to file: " + outputFileName);
        s3client.putObject(getBucketName(), outputFileName, contents);
    }

    protected LambdaLogger getLogger() {
        return getContext().getLogger();
    }


    public static void main(String[] args) {
        List<String> items = new LinkedList<>();
        items.add("zero");
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        System.out.println(items);

        List<String> sublist = items.subList(0, 2);
        System.out.println(sublist);

    }
}
