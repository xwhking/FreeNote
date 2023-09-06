package com.xwhking.freenotebackend;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class TestChat {
    public static String accessKey = "cylj0c4jwgmkrn0zhau7ntok83xirvkx";
    public static String secretKey = "7zdon0oakrt80pf3ajyynsy3t2wkfl9z";
    public static YuCongMingClient yuCongMingClient = new YuCongMingClient(accessKey,secretKey);
    public static void main(String[] args) {
        ContinueChatWithYuCongMing();
    }
    public static void ContinueChatWithYuCongMing(){
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1695068568497954817L);
        Scanner scanner = new Scanner(System.in);
        while(true){
            String message = scanner.nextLine();
            devChatRequest.setMessage(message);
            BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
            System.out.println(response.getData().getContent());
        }
    }
}
