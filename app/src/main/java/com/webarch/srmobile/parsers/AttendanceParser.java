package com.webarch.srmobile.parsers;

import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Manoj Khanna
 */

public class AttendanceParser extends Parser {

    @Override
    protected InputStream getInputStream() throws IOException {
        return HttpUtils.sendHttpRequest("http://evarsity.srmuniv.ac.in/srmswi/resource/StudentDetailsResources.jsp?resourceid=7", User.getUser().getCookie())
                .getInputStream();
    }

    @Override
    protected Block[] getBlocks() {
        return new Block[0];
    }

}
