package com.frank.aws.mylambdaproject;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class App extends BaseApp implements RequestHandler<S3Event, OutputBean> {

    @Override
    public OutputBean doWork() {
        OutputBean outputBean;
        try {
            int fileCount = getListOfFilesFromS3().size();
            outputBean = buildSuccessOutputBean(fileCount);
        } catch ( Exception ex ) {
            outputBean = buildFailureOutputBean(ex);
        }
        return outputBean;
    }

    protected OutputBean buildFailureOutputBean(Exception ex) {
        OutputBean outputBean = new OutputBean();
        logStackTrace(ex);
        outputBean.setStatus(ExecutionStatus.FAILURE);
        return outputBean;
    }

    protected OutputBean buildSuccessOutputBean(int fileCount) {
        OutputBean outputBean = new OutputBean();
        outputBean.setStatus(ExecutionStatus.SUCCESS);
        outputBean.setBucketName(getS3BucketName());
        outputBean.setFileCount(fileCount);
        return outputBean;
    }


}
