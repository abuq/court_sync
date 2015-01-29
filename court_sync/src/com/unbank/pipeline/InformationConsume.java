package com.unbank.pipeline;

import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.action.ArticleInfoFiller;
import com.unbank.mybatis.entity.CourtInfo;

public class InformationConsume extends BaseQueue implements Runnable {
	protected LinkedBlockingQueue<Object> informationQueue;

	public InformationConsume(LinkedBlockingQueue<Object> informationQueue) {
		this.informationQueue = informationQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (informationQueue.size() <= 10000
						&& informationQueue.size() > 0) {

					CourtInfo information = null;

					information = (CourtInfo) take(informationQueue);

					if (information != null) {
						consumeInformation(information);
					}
				}
				sleeping(5000);
			} catch (Exception e) {
				logger.info("", e);
				continue;
			}

		}
	}

	private void consumeInformation(CourtInfo information) {

		new ArticleInfoFiller().consumeInformation(information);

	}

}
