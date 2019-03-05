package com.enlink.commons;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Aggregations {
    @Autowired
    RestHighLevelClient client;
    @Autowired
    RestClient restClient;

    public Map<String, Boolean> aggsApi(){
        //加入聚合
        Map<String, Boolean> result = new HashMap<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("cars")
                .field("price");
        aggregation.subAggregation(AggregationBuilders.avg("average_price")
                .field("price"));
        searchSourceBuilder.aggregation(aggregation);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder); // 将 SearchSourceBuilder  添加到 SeachRequest 中
        //3、发送请求
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            //4、处理响应
            //搜索结果状态信息
            RestStatus status = searchResponse.status();
            int status1 = status.getStatus();
            TimeValue took = searchResponse.getTook();
            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
            boolean timedOut = searchResponse.isTimedOut();
            System.out.println("是否提前结束"+terminatedEarly);
            System.out.println("状态码"+status1+"花费时间"+took);
            result.put("是否超时",timedOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return result;
    }

}
