package com.bottle.mina.service;

public interface IClientDataSender {
	public abstract void pushDataToQueue(String json);
	public int getToBeSentQueuesize();

}