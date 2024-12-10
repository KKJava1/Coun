package com.jiawa.wiki.websocket;

import com.alibaba.fastjson.JSONObject;
import com.kangjunjie.netty.websocket.starter.annotations.OnClose;
import com.kangjunjie.netty.websocket.starter.annotations.OnOpen;
import com.kangjunjie.netty.websocket.starter.annotations.PathParam;
import com.kangjunjie.netty.websocket.starter.annotations.WsServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.kangjunjie.netty.websocket.starter.socket.Session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@WsServerEndpoint(value = "/ws/{token}")
@Component
@Slf4j
public class WebSocketServer {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);

	private static ConcurrentMap<String,Session> sessionPool = new ConcurrentHashMap<>();
	private static ConcurrentMap<String,String> sessionIds = new ConcurrentHashMap<>();

	@OnOpen
	public void open(Session session,@PathParam(value="token") String token){
		log.info("client【{}】连接成功",token);
		sessionPool.put(token, session);
		sessionIds.put(session.getId(), token);
	}

	/**
	 * 连接关闭触发
	 */
	@OnClose
	public void onClose(Session session,@PathParam String token){
		sessionPool.remove(sessionIds.get(session.getId()));
		sessionIds.remove(session.getId());
		log.info("client【{}】断开连接",token);
	}


//	/**
//	 * 收到信息时触发
//	 * @param message
//	 */
//	@OnMessage
//	public void onMessage(Session session,String message){
//		String msg = String.format("%s  <-- %s",sessionIds.get(session.getId()),message);
//		sendMessage(msg,ADMIN);
//		log.info("client {} send to ADMIN  message : {} ",sessionIds.get(session.getId()),message);
//	}


	/**
	 * 群发消息
	 */
	public void sendInfo(String type, String message) {
		JSONObject json = new JSONObject();
		json.put("type", type); // 消息类型
		json.put("content", message); // 消息内容

		for (String token : sessionPool.keySet()) {
			Session session = sessionPool.get(token);
			session.sendText(json.toString());
		}
	}

}
