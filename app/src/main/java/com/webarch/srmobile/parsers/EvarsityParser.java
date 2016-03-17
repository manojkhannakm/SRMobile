package com.webarch.srmobile.parsers;

import com.webarch.srmobile.utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Manoj Khanna
 */

public class EvarsityParser extends Parser {

    private String cookie;

    public EvarsityParser(String cookie) {
        this.cookie = cookie;
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return HttpUtils.sendHttpRequest("http://evarsity.srmuniv.ac.in/srmswi/usermanager/home.jsp", cookie).getInputStream();
    }

    @Override
    protected Block[] getBlocks() {
        return new Block[]{
                new Block("semester", new String[]{
                        "<li> Semester: (\\d+)</li>"
                }),
                new Block("section", new String[]{
                        "<li> Section : (\\D+)</li>"
                })
        };
    }

    public String getSemester() {
        return matchListMap.get("semester").get(0).get(0);
    }

    public String getSection() {
        return matchListMap.get("section").get(0).get(0);
    }

}
