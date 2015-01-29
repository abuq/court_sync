package com.unbank;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.unbank.pipeline.InformationConsume;
import com.unbank.pipeline.InformationProduct;

public class UnbankConsole {
	private static Log logger = LogFactory.getLog(UnbankConsole.class);

	static {
		// 启动日志
		try {
			PropertyConfigurator.configure(UnbankConsole.class.getClassLoader()
					.getResource("").toURI().getPath()
					+ "log4j.properties");
			logger.info("---日志系统启动成功---");
		} catch (Exception e) {
			logger.error("日志系统启动失败:", e);
		}
	}

	public static void main(String[] args) {

		LinkedBlockingQueue<Object> informationQueue = new LinkedBlockingQueue<Object>();
		ExecutorService executor = Executors.newFixedThreadPool(12);
		executor.execute(new InformationProduct(informationQueue));
		for (int i = 0; i < 5; i++) {
			executor.execute(new InformationConsume(informationQueue));
		}
		executor.shutdown();
	}

}
