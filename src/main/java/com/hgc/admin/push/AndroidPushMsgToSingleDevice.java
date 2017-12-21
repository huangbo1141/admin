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

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;

public class AndroidPushMsgToSingleDevice {
	public void sendPush(HashMap<String,Object> map_msg,String token) throws PushClientException,
			PushServerException,Exception {
		// 1. get apiKey and secretKey from developer console
		if(token==null || token.length() ==0){
			System.out.println("Token Empty");
			return;
		}
		
		String masterSecret = Constants.PUSH_MASTERSECRET;
		String appKey = Constants.PUSH_APPKEY; 
		JPushClient jc = new JPushClient(masterSecret,appKey);
		
		try{
			String title = map_msg.get("title").toString();
			String alert = map_msg.get("alert").toString();
			//PushResult pr = jc.sendAndroidMessageWithRegistrationID(title, msgContent, token);
			PushResult pr = jc.sendAndroidNotificationWithRegistrationID(title, alert, null, token);
			System.out.println("statusCode " +  pr.statusCode);
			System.out.println("msg_id " +  pr.msg_id);
			System.out.println("ResponseCode " +  pr.getResponseCode());
			System.out.println("sendno " +  pr.sendno);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}
}
