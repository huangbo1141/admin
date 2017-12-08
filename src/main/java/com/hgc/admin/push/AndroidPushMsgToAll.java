package com.hgc.admin.push;

import java.util.HashMap;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;

public class AndroidPushMsgToAll {
	public void sendPush(HashMap<String,Object> map_msg)
			throws PushClientException,PushServerException,Exception {
		// 1. get apiKey and secretKey from developer console
		String apiKey = Constants.PUSH_APIKEY;
		String secretKey = Constants.PUSH_SECRETKEY; 
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);
		System.out.println("AndroidPushMsgToAll");
		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
//		pushClient.setChannelLogHandler(new YunLogHandler() {
//			@Override
//			public void onHandle(YunLogEvent event) {
//				System.out.println(event.getMessage());
//			}
//		});

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json_map = mapper.writeValueAsString(map_msg);
			// 4. specify request arguments
			PushMsgToAllRequest request = new PushMsgToAllRequest()
					.addMsgExpires(new Integer(3600)).addMessageType(0)
					.addMessage(json_map) //æ·»åŠ é€�ä¼ æ¶ˆæ�¯
					.addSendTime(System.currentTimeMillis() / 1000 + 120) // è®¾ç½®å®šæ—¶æŽ¨é€�æ—¶é—´ï¼Œå¿…éœ€è¶…è¿‡å½“å‰�æ—¶é—´ä¸€åˆ†é’Ÿï¼Œå�•ä½�ç§’.å®žä¾‹2åˆ†é’Ÿå�ŽæŽ¨é€�
					.addDeviceType(3);
			// 5. http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			// Httpè¯·æ±‚ç»“æžœè§£æž�æ‰“å�°
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime() + ",timerId: "
					+ response.getTimerId());
		} catch (PushClientException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMessage: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}catch(Exception e){
			throw e;
		}
	}
}
