package com.enlink.threads;

import com.enlink.commons.Search;

import java.util.Map;

public class CostumThreadSearch implements Runnable {
   Search search = new Search();
    @Override
    public void run() {
        int count = 0;
        while (true){
            count ++;
            Map<String, Boolean> map = search.searchApi();
            Boolean flag = map.get("是否超时");
            if (flag){
                System.out.println("第"+count+"请求异常");
                break;
            }
        }
    }
}
