package com.enlink.commons;

import com.enlink.config.ElasticsarchConfig;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Search {
//    @Autowired
//    RestHighLevelClient client;
//    @Autowired
//    RestClient restClient;

    public Map<String, Boolean> searchApi() {
        Map<String, Boolean> result = new HashMap<>();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery()); // 添加 match_all 查询
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.source(searchSourceBuilder); // 将 SearchSourceBuilder  添加到 SeachRequest 中
            //3、发送请求
        SearchResponse searchResponse = null;
        try {
            ElasticsarchConfig config    = new ElasticsarchConfig();
            searchResponse =  config.restHighLevelClient().search(searchRequest);

            //4、处理响应
            //搜索结果状态信息
            RestStatus status = searchResponse.status();
            int status1 = status.getStatus();
            TimeValue took = searchResponse.getTook();
            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
            boolean timedOut = searchResponse.isTimedOut();
//            System.out.println("是否提前结束"+terminatedEarly);
            System.out.println("状态码"+status1+"花费时间"+took);
            result.put("是否超时",timedOut);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
