package com.zhangct.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivitiApplicationTests {

    @Test
    void deployProcess() {

        RepositoryService repositoryService = ProcessEngines.getDefaultProcessEngine().getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().name("请假流程001")
                .addClasspathResource("process/leave.bpmn")
                .addClasspathResource("process/leave.png")
                .deploy();
        System.out.println("部署成功："+deploy.getId());
    }

}
