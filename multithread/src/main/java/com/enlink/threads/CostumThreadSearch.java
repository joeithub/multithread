package com.enlink.threads;

import com.enlink.commons.Search;

import java.util.Map;

public class CostumThreadSearch implements Runnable {

    @Override
    public void run() {
        int count = 0;
        Search search = new Search();

        while (true){
            count ++;
            Map<String, Boolean> map = search.searchApi();
            Boolean flag = map.get("是否超时");
//            System.out.print(count);
//            System.out.println(flag);
            System.out.println(flag);
            if (flag != false){
                System.out.println(count);
                System.out.println("第"+count+"请求异常");
            }else {
                System.out.println(count);
            }

        }
    }
}
