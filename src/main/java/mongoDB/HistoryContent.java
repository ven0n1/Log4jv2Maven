package mongoDB;

import org.example.Constants;

import java.time.format.DateTimeFormatter;
import java.util.Date;
public class HistoryContent {
    String className;
    String createdDate;
    String actor = Constants.ACTOR;
    String methodName;
    public enum Status {SUCCESS, FAULT}

    Status status;
    String jsonEntity;

    public HistoryContent(String className, String createdDate, String actor, String methodName, Status status, String jsonEntity) {
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
        this.methodName = methodName;
        this.status = status;
        this.jsonEntity = jsonEntity;
    }

    public HistoryContent(String className, String createdDate, String methodName, Status status, String jsonEntity) {
        this.className = className;
        this.createdDate = createdDate;
        this.methodName = methodName;
        this.status = status;
        this.jsonEntity = jsonEntity;
    }

    public HistoryContent() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getJsonEntity() {
        return jsonEntity;
    }

    public void setJsonEntity(String jsonEntity) {
        this.jsonEntity = jsonEntity;
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "className='" + className + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                ", status=" + status +
                '}';
    }

    public HistoryContent fromJson(String json){
        return fromJson(json);
    }
}

