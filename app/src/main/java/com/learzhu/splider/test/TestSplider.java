package com.learzhu.splider.test;

import com.learzhu.splider.bean.LinkTypeData;
import com.learzhu.splider.core.ExtractService;
import com.learzhu.splider.rule.Rule;

import java.util.List;

/**
 * Created by Learzhu on 2016/10/17.
 */

public class TestSplider {
    public StringBuilder getDatasByClass() {
        Rule rule = new Rule(
                "http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",
                new String[]{"query.enterprisename", "query.registationnumber"}, new String[]{"兴网", ""},
                "cont_right", Rule.CLASS, Rule.POST);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        return printf(extracts);
    }

    public StringBuilder getDatasByCssQuery() {
        Rule rule = new Rule("http://www.11315.com/search",
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
            mStringBuilder.append(data.getLinkText() + " /").append(data.getLinkHref() + " /").append("*******");
        }
        return mStringBuilder;
    }

//    public static void main(String args[]) {
//        Test test = new Test();
//        test.getDatasByClass();
//        test.getDatasByCssQuery();
//    }
}
