package com.app.provider;

import com.app.asyntask.NetworkFacade;
import com.app.common.DataQueryType;
import com.app.entity.DataResult;

/**
 * Created by grandry.xu on 15-12-29.
 */
public class ZrodoProviderImp implements Provider {

    private NetworkFacade networkFacade;
   private String serverUrl = "http://115.29.106.147:8080/sjDetectList/";
   //private String serverUrl = "http://192.168.1.110:8080/sjDetectList/";


    public ZrodoProviderImp() {
        networkFacade = new NetworkFacade();
    }


    @Override
    public DataResult<String> login(String username, String passwd)throws Exception{
        StringBuilder loginUrl = new StringBuilder(getRequestUrl(serverUrl, "checkUser.do"));
        loginUrl.append("?").append("username=").append(username)
                .append("&password=").append(passwd);
        DataResult<String> dataResult = null;
        dataResult = networkFacade.getMethod(loginUrl.toString());
        return dataResult;


    }

    @Override
    public DataResult<String> requestForStatisticData(DataQueryType type)throws Exception{
        StringBuilder requestUrl=new StringBuilder(getRequestUrl(serverUrl, "findDetorderStatics.do"));
        requestUrl.append("?").append("userid=").append(ZrodoProviderManager.userId)
                .append("&type=").append(getType(type));
        DataResult<String> dataResult=null;
        dataResult=networkFacade.getMethod(requestUrl.toString());
        return dataResult;
    }

    @Override
    public DataResult requestForPieChartData(DataQueryType type) throws Exception {
        StringBuilder requestUrl=new StringBuilder(getRequestUrl(serverUrl, "findYXStatics.do"));
        requestUrl.append("?").append("userid=").append(ZrodoProviderManager.userId)
                .append("&type=").append(getType(type));
        DataResult<String> dataResult=null;
        dataResult=networkFacade.getMethod(requestUrl.toString());
        return dataResult;
    }

    @Override
    public DataResult requestForLineChartData(DataQueryType type) throws Exception {
        StringBuilder requestUrl=new StringBuilder(getRequestUrl(serverUrl, "getyearstaticsbyitem.do"));
        requestUrl.append("?").append("userid=").append(ZrodoProviderManager.userId)
                .append("&type=").append(getType(type));
        DataResult<String> dataResult=null;
        dataResult=networkFacade.getMethod(requestUrl.toString());
        return dataResult;
    }

    @Override
    public DataResult requestForHistoryWarningData() throws Exception {
        StringBuilder requestUrl=new StringBuilder(getRequestUrl(serverUrl, "findDetorderysyxStatics.do"));
        requestUrl.append("?").append("userid=").append(ZrodoProviderManager.userId);
        DataResult<String> dataResult=null;
        dataResult=networkFacade.getMethod(requestUrl.toString());
        return dataResult;
    }



    @Override
    public DataResult requestForInfoQueryData(String cardId) throws Exception {
        StringBuilder requestUrl=new StringBuilder(getRequestUrl(serverUrl, "selectjyzbycardid.do"));
        requestUrl.append("?").append("cardid=").append(cardId);
        DataResult<String> dataResult=null;
        dataResult=networkFacade.getMethod(requestUrl.toString());
        return dataResult;
    }

    @Override
    public DataResult requestForOnTimeWarningData() throws Exception {
        StringBuilder requestUrl=new StringBuilder(getRequestUrl(serverUrl, "getwarning.do"));
        requestUrl.append("?").append("userid=").append(ZrodoProviderManager.userId);
        DataResult<String> dataResult=null;
        dataResult=networkFacade.getMethod(requestUrl.toString());
        return dataResult;
    }


    @Override
    public DataResult postSamplePic(String postdata) throws Exception {
        StringBuilder postUrl=new StringBuilder(getRequestUrl(serverUrl,"getSamplePic.do"));
        DataResult<String> dataResult=null;
        dataResult=networkFacade.getPostMethod(postUrl.toString(),postdata,2*60*1000);
        return dataResult;
    }

    private String getRequestUrl(String serverUrl, String... appendUrls) {
        StringBuilder sb = new StringBuilder(serverUrl.trim());
        if (appendUrls.length <= 0) {
            return sb.toString();
        }
        for (String appendUrl : appendUrls) {
            sb.append(appendUrl);
        }
        return sb.toString();
    }


    private String getType(DataQueryType val){
        String type="";
        switch (val){
            case DAY:
                type="1";
                break;
            case MONTH:
                type="2";
                break;
            case ANNUAL:
                type="3";
                break;
            default:
                break;
        }
        return type;
    }
}
