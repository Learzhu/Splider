package com.learzhu.splider.test;

import com.learzhu.splider.bean.LinkTypeData;
import com.learzhu.splider.core.ExtractService;
import com.learzhu.splider.rule.Rule;

import java.util.List;

/**
 * Created by Learzhu on 2016/10/17.
 */

public class TestSplider {
    private String url = "http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery";
    private String url1 = "http://www.11315.com/search";
    private String spliderUrl = "https://www.e-shenhua.com/ec/myaccount/login.jsp?DPSLogout=true/com/shenhua/userprofiling/ShenHuaProfileFormHandler.login";
//    private String spliderUrl = "https://www.e-shenhua.com/ec/myaccount/login.jsp";


    public StringBuilder getDataSpliterByClass() {
        Rule rule = new Rule(spliderUrl, new String[]{"query.atg_store_registerLoginEmailAddress", "atg_store_registerLoginPassword"}, new String[]{"wchgsl_1b", "Slb$88278572"}, "cont_right", Rule.CLASS, Rule.POST);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        return printf(extracts);
    }

    public StringBuilder getDatasByClass() {
        Rule rule = new Rule(
                url,
                new String[]{"query.enterprisename", "query.registationnumber"}, new String[]{"兴网", ""},
                "cont_right", Rule.CLASS, Rule.POST);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        return printf(extracts);
    }

    public StringBuilder getDatasByCssQuery() {
        Rule rule = new Rule(url1,
                new String[]{"name"}, new String[]{"兴网"},
                "div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        return printf(extracts);
    }

    public StringBuilder printf(List<LinkTypeData> datas) {
        StringBuilder mStringBuilder = new StringBuilder();
        for (LinkTypeData data : datas) {
            System.out.println(data.getLinkText());
            System.out.println(data.getLinkHref());
            System.out.println("***********************************");
            mStringBuilder.append(data.getLinkText() + " \n").append(data.getLinkHref() + " \n");
        }
        return mStringBuilder;
    }

}
