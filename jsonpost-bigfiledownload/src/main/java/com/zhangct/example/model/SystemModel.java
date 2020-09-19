package com.zhangct.example.model;

public class SystemModel {
    private String uuid;
    private String systemId;
    private String moduleId;
    private String deploymentEnv;
    private Integer deploymentType;
    private Integer rollback;
    private Integer order;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getDeploymentEnv() {
        return deploymentEnv;
    }

    public void setDeploymentEnv(String deploymentEnv) {
        this.deploymentEnv = deploymentEnv;
    }

    public Integer getDeploymentType() {
        return deploymentType;
    }

    public void setDeploymentType(Integer deploymentType) {
        this.deploymentType = deploymentType;
    }

    public Integer getRollback() {
        return rollback;
    }

    public void setRollback(Integer rollback) {
        this.rollback = rollback;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SystemModel{" +
                "uuid='" + uuid + '\'' +
                ", systemId='" + systemId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", deploymentEnv='" + deploymentEnv + '\'' +
                ", deploymentType=" + deploymentType +
                ", rollback=" + rollback +
                ", order=" + order +
                '}';
    }
}
