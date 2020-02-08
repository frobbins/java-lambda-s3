package com.frank.aws.mylambdaproject;

public class OutputBean {

    private ExecutionStatus status;
    private String bucketName;
    private int fileCount;

    public OutputBean() {
        status = ExecutionStatus.SUCCESS;
    }



    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OutputBean{" +
                "status=" + status +
                ", bucketName='" + bucketName + '\'' +
                ", fileCount=" + fileCount +
                '}';
    }
}
