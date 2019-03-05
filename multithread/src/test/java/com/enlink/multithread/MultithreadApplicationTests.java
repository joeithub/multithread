package com.enlink.multithread;

import com.enlink.commons.Search;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.SourceSimpleFragmentsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultithreadApplicationTests {
    @Autowired
    RestHighLevelClient client;
    @Autowired
    RestClient restClient;

     Search search =new Search();
    /**
     * get
     * @throws Exception
     */
    @Test
    public void getDoc() throws Exception{
        GetRequest getRequest = new GetRequest(".monitoring-es-6-2019.02.25", "doc", "SHoPImkB9NlDwCyKgkVS");
        GetResponse getResponse = client.get(getRequest);
        boolean exists = getResponse.isExists();
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        System.out.println("是否存在"+exists);
        System.out.println("查询结果:"+sourceAsMap);
    }

    /**
     * search
     * @throws Exception
     */
    @Test
    public void searchDoc() {
        int  count=0;
        while (true){
            count ++;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()); // 添加 match_all 查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder); // 将 SearchSourceBuilder  添加到 SeachRequest 中
        //3、发送请求
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4、处理响应
        //搜索结果状态信息
        RestStatus status = searchResponse.status();
        int status1 = status.getStatus();
        TimeValue took = searchResponse.getTook();
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
        boolean timedOut = searchResponse.isTimedOut();
            System.out.println(count);
        //  System.out.println("是否请求超时"+timedOut);
        // System.out.println("是否提前结束"+terminatedEarly);
        //System.out.println("状态码"+status1+"花费时间"+took);
            if (timedOut){
                System.out.println("第"+count+"次请求异常");
                break;
            }
    } }

    /**
     * aggregation
     * @throws Exception
     */
    @Test
    public void aggregationDoc() throws Exception{
        int count = 0;
        while (true){
            //加入聚合
            count ++;
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            TermsAggregationBuilder aggregation = AggregationBuilders.terms("cars")
                    .field("price");
            aggregation.subAggregation(AggregationBuilders.avg("average_price")
                    .field("price"));
            searchSourceBuilder.aggregation(aggregation);
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.source(searchSourceBuilder); // 将 SearchSourceBuilder  添加到 SeachRequest 中
            //3、发送请求
            SearchResponse searchResponse = client.search(searchRequest);
            //4、处理响应
            //搜索结果状态信息
            RestStatus status = searchResponse.status();
            int status1 = status.getStatus();
            TimeValue took = searchResponse.getTook();
            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
            boolean timedOut = searchResponse.isTimedOut();
            System.out.println(count);
            if (timedOut){
                System.out.println("第"+count+"次请求异常");
            }
//            System.out.println("是否提前结束"+terminatedEarly);
//            System.out.println("状态码"+status1+"花费时间"+took);
        }
    }
}
