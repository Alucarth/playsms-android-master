package com.artivisi.android.playsms.service.impl;

import android.util.Log;

import com.artivisi.android.playsms.domain.Credit;
import com.artivisi.android.playsms.domain.User;
import com.artivisi.android.playsms.helper.ContactHelper;
import com.artivisi.android.playsms.helper.LoginHelper;
import com.artivisi.android.playsms.helper.MessageHelper;
import com.artivisi.android.playsms.helper.QueryHelper;
import com.artivisi.android.playsms.service.AndroidMasterService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by opaw on 2/5/15.
 */
public class AndroidMasterServiceImpl implements AndroidMasterService {


    private User user;
    RestTemplate restTemplate = new RestTemplate();
    private String PLAYSMS_URL;

    public AndroidMasterServiceImpl(){
        this.restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        ( (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory() ).setReadTimeout(60 * 1000);
    }

    public AndroidMasterServiceImpl(User user) {
        this.user = user;
        PLAYSMS_URL = user.getServerUrl();
        this.restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        ( (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory() ).setReadTimeout(60 * 1000);
    }


    private static final String BASE_URI = "/index.php?app=ws";

    @Override
    public LoginHelper getToken(String urlServer, String username, String password) throws Exception{

        String url = urlServer + BASE_URI + "&u=" + username + "&p=" + password + "&op=get_token&format=json";
        Log.i("David","LoginHelper:"+url);
        try {
            ResponseEntity<LoginHelper> responseEntity = restTemplate.getForEntity(url, LoginHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public MessageHelper getSentMessage() throws Exception{
        String url = PLAYSMS_URL + BASE_URI + "&u=" + user.getUsername() + "&h=" + user.getToken() + "&op=ds&format=json";
        Log.i("David","getSentMessage:"+url);
        try{
            ResponseEntity<MessageHelper> responseEntity = restTemplate.getForEntity(url, MessageHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public MessageHelper getInbox() throws Exception{
        String url = PLAYSMS_URL + BASE_URI + "&u=" + user.getUsername() + "&h=" + user.getToken() + "&op=ix&format=json";
        Log.i("David","getInbox:"+url);
        try{
            ResponseEntity<MessageHelper> responseEntity = restTemplate.getForEntity(url, MessageHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public MessageHelper sendMessage(String to, String msg) throws Exception{
        String url = PLAYSMS_URL + BASE_URI +
                "&u=" + user.getUsername() + "&h=" + user.getToken() + "&op=pv&to=" + to + "&msg=" + msg + "&format=json";
        URI uri = new URI(url);
        Log.i("David","senMessage:"+url);
        try {
            ResponseEntity<MessageHelper> responseEntity = restTemplate.getForEntity(uri, MessageHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public MessageHelper pollInbox(String id) throws Exception{
        String url;
        if(id != null){
            url = PLAYSMS_URL + BASE_URI
                    + "&u=" + user.getUsername()
                    + "&h=" + user.getToken()
                    + "&op=ix"
                    + "&last=" + id
                    + "&format=json";
        } else {
            url = PLAYSMS_URL + BASE_URI
                    + "&u=" + user.getUsername()
                    + "&h=" + user.getToken()
                    + "&op=ix"
                    + "&format=json";
        }
        try {
            ResponseEntity<MessageHelper> responseEntity = restTemplate.getForEntity(url, MessageHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public MessageHelper pollSentMessage(String smslogId) throws Exception{
        String url;
        if(smslogId != null){
            url = PLAYSMS_URL + BASE_URI
                    + "&u=" + user.getUsername()
                    + "&h=" + user.getToken()
                    + "&op=ds"
                    + "&last=" + smslogId
                    + "&format=json";
        } else {
            url = PLAYSMS_URL + BASE_URI
                    + "&u=" + user.getUsername()
                    + "&h=" + user.getToken()
                    + "&op=ds"
                    + "&format=json";
        }
        try {
            ResponseEntity<MessageHelper> responseEntity = restTemplate.getForEntity(url, MessageHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public Credit getCredit() throws Exception{
        String url = PLAYSMS_URL + BASE_URI + "&u=" + user.getUsername() + "&h=" + user.getToken() + "&op=cr&format=json";
        Log.i("David","getCredit:"+url);
        try {
            ResponseEntity<Credit> responseEntity = restTemplate.getForEntity(url, Credit.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public QueryHelper query() throws Exception{
        String url = PLAYSMS_URL + BASE_URI + "&u=" + user.getUsername() + "&h=" + user.getToken() + "&op=query&format=json";
        Log.i("David","LoginHelper:"+url);
        try {
            ResponseEntity<QueryHelper> responseEntity = restTemplate.getForEntity(url, QueryHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public ContactHelper getContact() throws Exception {
        String url = PLAYSMS_URL + BASE_URI + "&u=" + user.getUsername() + "&p=" + user.getPassword() +"&h=" + user.getToken() + "&kwd=%&op=get_contact&format=json";
        Log.i("URL : ", url);
        try {
            ResponseEntity<ContactHelper> responseEntity = restTemplate.getForEntity(url, ContactHelper.class);
            return responseEntity.getBody();
        } catch (RuntimeException e){
            throw e;
        }
    }
}
