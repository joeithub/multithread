package com.enlink.multithread;

import com.enlink.threads.CostumThreadAggregations;
import com.enlink.threads.CostumThreadSearch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@ComponentScan(basePackages = {"com.enlink"})
public class MultithreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultithreadApplication.class, args);
        CostumThreadSearch costumThreadSearch = new CostumThreadSearch();
        CostumThreadAggregations costumThreadAggregations = new CostumThreadAggregations();
//        Thread thread = new Thread(costumThreadSearch, "搜索线程1");
       // Thread thread2 = new Thread(costumThreadAggregations, "聚合线程1");
//        thread.start();
//        new Thread(costumThreadSearch, "搜索线程"+1).start();
//        for (int i = 0; i <50 ; i++) {
          new Thread(costumThreadSearch).start();


//            System.out.println("搜索线程"+i);
//        }
    }

}
