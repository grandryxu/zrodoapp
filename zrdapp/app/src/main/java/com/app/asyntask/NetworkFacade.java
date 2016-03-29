package com.app.asyntask;


import com.app.entity.DataResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by grandry.xu on 15-9-18.
 */
public class NetworkFacade {

    private HttpClient httpClient;

    private DataResult<String> executeRequest(HttpUriRequest request) throws Exception{
        request.addHeader("Content-type", "application/json");
        DataResult<String> result = null;
            HttpResponse response = getHttpClient().execute(request);
            result = handResponse(response);
        return result;

    }

    private DataResult<String> handResponse(HttpResponse response) {
        DataResult<String> result = null;
        boolean isSuccess = false;
        if (response == null) {
            return result;
        }
        int respCode = response.getStatusLine().getStatusCode();
        result = new DataResult<String>();
        if (respCode >= 200 && respCode < 300) {
            result.status = DataResult.Status.OK;
            isSuccess = true;
        } else {
            result.status = DataResult.Status.ERROR;
        }
        if (isSuccess) {
            HttpEntity entity = response.getEntity();
            try {
                String data = EntityUtils.toString(entity);
                result.setData(data);
            } catch (Exception e) {
                result.status = DataResult.Status.ERROR;
                result.setE(e);

            }

        }
        return result;
    }


    private HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        return httpClient;
    }

    public DataResult<String> getMethod(String url) throws Exception{
        HttpGet get = new HttpGet(url);
        return executeRequest(get);
    }

    /**
     * httppost请求，android2.3之后，httpclient过期，改用httpURLConnection
     * @param url
     * @return
     * @throws Exception
     */
    public DataResult<String> getPostMethod(String posturl,String postData,int timeout)throws Exception{
        URL connectURL=new URL(posturl);
        HttpURLConnection connection= (HttpURLConnection) connectURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(timeout);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length",String.valueOf(postData.getBytes().length));
      return  executePost(connection, postData);

    }

    private DataResult<String> executePost(HttpURLConnection connection,String postData)throws Exception{
        DataResult<String> result=new DataResult<String>();
        OutputStream os=connection.getOutputStream();
        os.write(postData.getBytes());
      /*  PrintWriter writer=new PrintWriter(connection.getOutputStream());
        writer.write(postData);
        writer.flush();
        writer.close();*/
        os.flush();
        os.close();
        try{
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is=connection.getInputStream();
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                byte[] bytes=new byte[1024];
                while((is.read(bytes))!=-1){
                    bos.write(bytes);
                }
                is.close();
                bos.close();
                result.setData(bos.toString());
                result.status= DataResult.Status.OK;

            }
        }catch (Exception e){
            result.status= DataResult.Status.ERROR;
            result.setE(e);
            throw e;

        }finally {
           connection.disconnect();
        }


        return result;
    }
}
