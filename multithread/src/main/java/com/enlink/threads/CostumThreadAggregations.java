package com.enlink.threads;

import com.enlink.commons.Aggregations;

public class CostumThreadAggregations  implements  Runnable{
    Aggregations aggregations =new Aggregations();
    @Override
    public void run() {
        aggregations.aggsApi();
    }
}
