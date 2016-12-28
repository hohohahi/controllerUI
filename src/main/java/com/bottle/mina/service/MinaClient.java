package com.bottle.mina.service;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.common.AbstractBaseBean;
import com.bottle.mina.constants.MinaConstants;
import com.bottle.mina.vo.SubscriptionVO;

@Service
public final class MinaClient extends AbstractBaseBean implements IMinaClient {
	private IoSession session;
	
	@Autowired
	private IoHandler ioHandler;
	
	@Override
	public void initialize() {
		super.initialize();
		initClientThread();
		super.debugLog("initialize: mina client socket initialized.");
	}
	
	private void initClientThread(){
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new Runnable(){

			@Override
			public void run() {
				runClient();
			}			
		});
	}
	
	public void runClient() {
		try {
			NioSocketConnector connector = new NioSocketConnector();  
			connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
			connector.getSessionConfig().setReadBufferSize( MinaConstants.BUFFER_SIZE );
			connector.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, MinaConstants.BOTH_IDLE_TIME );
			connector.setHandler(ioHandler);
			
			final int socketPort = super.configurationManager.getConfigurationVO().getServerSocketPort();
			final String serverIP = super.configurationManager.getConfigurationVO().getServerIP();
			
			System.out.println("server IP:" + serverIP);
			System.out.println("socket port:" + socketPort);
			ConnectFuture connectFuture = connector.connect(new InetSocketAddress(serverIP, socketPort));  
			connectFuture.awaitUninterruptibly();            
			session = connectFuture.getSession();     
			
			subscribe();

			session.getCloseFuture().awaitUninterruptibly();
			connector.dispose() ;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public void subscribe() {
		super.validateObject(session);
		final SubscriptionVO subscriptionVO = new SubscriptionVO();
		final String identifier = super.configurationManager.getConfigurationVO().getIdentifier();
		subscriptionVO.setIdentifier(identifier);
		session.write(JSONObject.toJSONString(subscriptionVO));
	}
	
	public static void main(String [] args) {
		try {
			InetAddress address = InetAddress.getByName("core-om-dev-socket.everymatrix.com");
			System.out.println(address.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ret = "";
		for (int i=0; i<65537; i++){
			ret += i + ",";
		}
		System.out.println(ret);
	}
}
