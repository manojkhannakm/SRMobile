package com.webarch.srmobile.parsers;

import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.utils.HttpUtils;
import com.webarch.srmobile.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manoj Khanna
 */

public class SubjectsParser extends Parser {

    @Override
    protected InputStream getInputStream() throws IOException {
        return HttpUtils.sendHttpRequest("http://evarsity.srmuniv.ac.in/srmswi/resource/StudentDetailsResources.jsp?resourceid=4", User.getUser().getCookie())
                .getInputStream();
    }

    @Override
    protected Block[] getBlocks() {
        return new Block[]{
                new Block("subjects", new String[]{
                        "<TD class=\"tablecontent01\">(.+)</TD>",
                        "<TD class=\"tablecontent01\">(.+)</TD>",
                        "<TD class=\"tablecontent02\">.+</TD>",
                        "<TD class=\"tablecontent02\">(.+)</TD>"
                }, -1) {

                    @Override
                    protected void modifyMatch(ArrayList<String> match) {
                        String name = StringUtils.capitalizeAllWords(match.get(1));
                        Matcher matcher = Pattern.compile(".* ([iIvVxX]+)$").matcher(name);
                        if (matcher.find()) {
                            int lastIndex = name.lastIndexOf(matcher.group(1));
                            name = name.substring(0, lastIndex) + name.substring(lastIndex).toUpperCase();
                        }
                        match.set(1, name);
                    }

                }
        };
    }

    public ArrayList<ArrayList<String>> getSubjects() {
        return matchListMap.get("subjects");
    }

}
