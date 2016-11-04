package com.bottle.business.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Service;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.AbstractBaseBean;

@Service
public class MessageQueueManager extends AbstractBaseBean implements IMessageQueueManager {
	private LinkedBlockingQueue<MessageVO> queue = new LinkedBlockingQueue<MessageVO>();
	private List<IMessageListener> listenerList = new ArrayList<IMessageListener>();
	
	@Override
	public void initialize() {
		super.initialize();
		initExecutor();
	}
	
	public void initExecutor() {
		ExecutorService pool = Executors.newSingleThreadExecutor();
		pool.submit(new Runnable(){
			@Override
			public void run() {				
				while (true){
					try {
						MessageVO vo = queue.take();
						
						for (IMessageListener listener : listenerList) {
							listener.process(vo);
						}
					} catch (Exception e) {
						logErrorAndStack(e, e.getMessage());
					}
				}
			}		
		});
	}
	
	@Override
	public void addListener(IMessageListener listener) {
		if (null == listener) {
			throw new NullPointerException("listener is null.");
		}
		
		listenerList.add(listener);
	}

	protected void logErrorAndStack(final Throwable e, final String errorMessage){
		super.logErrorAndStack(e, errorMessage);
	}

	@Override
	public void push(MessageVO vo) {
		queue.add(vo);
	}
}
