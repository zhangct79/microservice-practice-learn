package com.zhangct.example;

import com.zhangct.example.util.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ExampleApplicationTests {

	@Test
	void contextLoads() throws IOException {
		String json = "{\"systems\":[{\"uuid\":\"35eb67e4-f7e0-11ea-92dd-def018a6f9ef\",\"systemId\":\"IPDO\",\"moduleId\":\"agile\",\"deploymentEnv\":\"TRA\",\"deploymentType\":0,\"rollback\":0,\"order\":1},{\"uuid\":\"4d364022-f7e0-11ea-92dd-def018a6f9ef\",\"systemId\":\"PLMP\",\"moduleId\":\"web\",\"deploymentEnv\":\"TRA\",\"deploymentType\":0,\"rollback\":0,\"order\":2}],\"batch\":\"aaa\",\"type\":331}\n";
		HttpUtil.doPost("http://localhost:8080/deployments",json);
	}

}
