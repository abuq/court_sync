package com.unbank.pipeline;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.dao.CourtReader;
import com.unbank.mybatis.entity.CourtInfo;

public class InformationProduct extends BaseQueue implements Runnable {
	protected LinkedBlockingQueue<Object> informationQueue;

	public InformationProduct(LinkedBlockingQueue<Object> informationQueue) {
		this.informationQueue = informationQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (informationQueue.size() <= 0) {

					fillQueue();
				}
				sleeping((int) Math.random() * 1000);
			} catch (Exception e) {
				logger.info("", e);
				continue;
			}

		}
	}

	private void fillQueue() {
		List<CourtInfo> informations = new CourtReader()
				.readCourtInfos(1, 1000);
		logger.info(informations.size());
		if (informations.size() <= 0) {
			sleeping(1000 * 30);
		}
		for (CourtInfo information : informations) {
			put(informationQueue, information);
		}
		informations.clear();
	}

}
