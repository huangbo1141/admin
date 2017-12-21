package com.hgc.admin.push;

import java.util.HashMap;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;

public class AndroidPushMsgToSingleDevice2 {
	public void sendPush(HashMap<String,Object> map_msg,String token) throws PushClientException,
			PushServerException,Exception {
		// 1. get apiKey and secretKey from developer console
		if(token==null || token.length() ==0){
			System.out.println("Token Empty");
			return;
		}
		System.out.println("token "+token);
		String apiKey = Constants.PUSH_APIKEY;
		String secretKey = Constants.PUSH_SECRETKEY; 
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

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
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
					.addChannelId(token)
					.addMsgExpires(new Integer(3600)). // messageæœ‰æ•ˆæ—¶é—´
					addMessageType(1).// 1ï¼šé€šçŸ¥,0:é€�ä¼ æ¶ˆæ�¯. é»˜è®¤ä¸º0 æ³¨ï¼šIOSå�ªæœ‰é€šçŸ¥.
					addMessage(json_map).
					addDeviceType(3);// deviceType => 3:android, 4:ios
			// 5. http request
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			// Httpè¯·æ±‚ç»“æžœè§£æž�æ‰“å�°
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime());
		} catch (PushClientException e) {
			/*
			 * ERROROPTTYPE ç”¨äºŽè®¾ç½®å¼‚å¸¸çš„å¤„ç�†æ–¹å¼� -- æŠ›å‡ºå¼‚å¸¸å’Œæ�•èŽ·å¼‚å¸¸,'true' è¡¨ç¤ºæŠ›å‡º, 'false' è¡¨ç¤ºæ�•èŽ·ã€‚
			 */
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
