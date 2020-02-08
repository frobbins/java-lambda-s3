package com.frank.aws.mylambdaproject;

public enum ExecutionStatus {

    SUCCESS(200),
    FAILURE(500);
    Integer status = 200;
    ExecutionStatus(Integer value) {
        status = value;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(ExecutionStatus value) {
        status = value.getStatus();
    }
    public boolean isSuccess() {
        return status.compareTo(SUCCESS.getStatus()) == 0;
    }

}
