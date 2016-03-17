package com.webarch.srmobile.parsers;

import com.webarch.srmobile.utils.HttpUtils;
import com.webarch.srmobile.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class ProfileParser extends Parser {

    private String cookie;

    public ProfileParser(String cookie) {
        this.cookie = cookie;
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return HttpUtils.sendHttpRequest("http://evarsity.srmuniv.ac.in/srmswi/resource/StudentDetailsResources.jsp?resourceid=1", cookie).getInputStream();
    }

    @Override
    protected Block[] getBlocks() {
        return new Block[]{
                new Block("name", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Name</td>",
                        "<td height=\"25px\" colspan=\"3\" class=\"tablecontent01\">(.+)</td>"
                }) {

                    @Override
                    protected void modifyMatch(ArrayList<String> match) {
                        match.set(0, StringUtils.capitalizeAllWords(match.get(0)));
                    }

                },
                new Block("pictureUrlString", new String[]{
                        "<img height=\"100px\" width=\"100px\" src=\"..(.+)\" > </img>"
                }) {

                    @Override
                    protected void modifyMatch(ArrayList<String> match) {
                        match.set(0, "http://evarsity.srmuniv.ac.in/srmswi" + match.get(0));
                    }

                },
                new Block("regNo", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Register No.</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }),
                new Block("office", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Office Name</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }),
                new Block("course", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Course Name</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }),
                new Block("fathersName", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Father Name</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }) {

                    @Override
                    protected void modifyMatch(ArrayList<String> match) {
                        match.set(0, StringUtils.capitalizeAllWords(match.get(0)));
                    }

                },
                new Block("dateOfBirth", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Date of Birth</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }) {

                    @Override
                    protected void modifyMatch(ArrayList<String> match) {
                        match.set(0, match.get(0).replace('-', ' '));
                    }

                },
                new Block("sex", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Sex</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }),
                new Block("bloodGroup", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Blood Group</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }),
                new Block("address", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Address</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }) {

                    @Override
                    protected void modifyMatch(ArrayList<String> match) {
                        match.set(0, StringUtils.capitalizeAllWords(match.get(0)));
                    }

                },
                new Block("email", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Email</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }),
                new Block("pincode", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\">Pincode</td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                }),
                new Block("validity", new String[]{
                        "<td width=\"28%\" class=\"tablecontent01\"> Validity </td>",
                        "<td class=\"tablecontent01\" colspan=\"4\">(.+)</td>"
                })
        };
    }

    public String getName() {
        return matchListMap.get("name").get(0).get(0);
    }

    public String getPictureUrlString() {
        return matchListMap.get("pictureUrlString").get(0).get(0);
    }

    public String getRegNo() {
        return matchListMap.get("regNo").get(0).get(0);
    }

    public String getOffice() {
        return matchListMap.get("office").get(0).get(0);
    }

    public String getCourse() {
        return matchListMap.get("course").get(0).get(0);
    }

    public String getFathersName() {
        return matchListMap.get("fathersName").get(0).get(0);
    }

    public String getDateOfBirth() {
        return matchListMap.get("dateOfBirth").get(0).get(0);
    }

    public String getSex() {
        return matchListMap.get("sex").get(0).get(0);
    }

    public String getBloodGroup() {
        return matchListMap.get("bloodGroup").get(0).get(0);
    }

    public String getAddress() {
        return matchListMap.get("address").get(0).get(0);
    }

    public String getEmail() {
        return matchListMap.get("email").get(0).get(0);
    }

    public String getPincode() {
        return matchListMap.get("pincode").get(0).get(0);
    }

    public String getValidity() {
        return matchListMap.get("validity").get(0).get(0);
    }

}
