package com.example.taskmanagement;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskModel implements Parcelable {

    private String taskName;
    private String taskDate;
    private String taskCategory;

    public TaskModel(String taskName, String taskCategory, String taskDate) {
        this.taskName = taskName;
        this.taskCategory = taskCategory;
        this.taskDate = taskDate;
    }

    public TaskModel(Parcel in){
        taskName = in.readString();
        taskCategory = in.readString();
        taskDate = in.readString();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>(){

        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size){
            return new TaskModel[size];
        }
    };

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(taskName);
        dest.writeString(taskDate);
        dest.writeString(taskCategory);
    }
}
