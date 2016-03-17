package com.webarch.srmobile.parsers;

import com.webarch.srmobile.utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * @author Manoj Khanna
 */

public class SignInParser extends Parser {

    private String regNo, password, cookie;

    public SignInParser(String regNo, String password) {
        this.regNo = regNo;
        this.password = password;
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("txtSN", regNo);
        map.put("txtPD", password);
        map.put("txtPA", "1");

        HttpURLConnection httpURLConnection = HttpUtils.sendPostRequest("http://evarsity.srmuniv.ac.in/srmswi/usermanager/youLogin.jsp", map);
        httpURLConnection.setInstanceFollowRedirects(false);
        cookie = httpURLConnection.getHeaderField("Set-Cookie");
        return httpURLConnection.getInputStream();
    }

    @Override
    protected Block[] getBlocks() {
        return new Block[]{
                new Block("error", new String[]{
                        "urlNew=\"youLoginResources\\.jsp\\?resource=0&loginerror=(.*)\";"
                })
        };
    }

    public String getCookie() {
        return cookie;
    }

    public boolean isError() {
        return matchListMap.containsKey("error")
                && !matchListMap.get("error").get(0).get(0).isEmpty();
    }

}
