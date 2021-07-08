package com.yang.gulimall.search;


import com.alibaba.fastjson.JSON;
import com.yang.gulimall.search.config.GulimallElasticSearchConfig;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallSearchApplicationTests {

    @Autowired
    RestHighLevelClient client;

    @Data
    @ToString
    static class Users{
        private String userName;
        private String gender;
        private Integer age;
    }


    @Test
    public void contextLoads() throws IOException {
        System.out.println(client);
        IndexRequest indexRequest = new IndexRequest ("users");
        indexRequest.id("2");

        Users user = new Users();
        user.setUserName("李四");
        user.setAge(18);
        user.setGender("男");
        String jsonString = JSON.toJSONString(user);

        //设置要保存的内容，指定数据和类型
        indexRequest.source(jsonString, XContentType.JSON);

        //执行创建索引和保存数据
        IndexResponse index = client.index(indexRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);

    }

    @Test
    public void find() throws IOException {
        // 1 创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("users");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 构造检索条件
//        sourceBuilder.query();
//        sourceBuilder.from();
//        sourceBuilder.size();
//        sourceBuilder.aggregation();
        sourceBuilder.query(QueryBuilders.matchQuery("userName","张三"));
        //AggregationBuilders工具类构建AggregationBuilder
        // 构建第一个聚合条件:按照年龄的值分布
        TermsAggregationBuilder agg1 = AggregationBuilders.terms("agg1").field("age").size(10);// 聚合名称
        // 参数为AggregationBuilder
        sourceBuilder.aggregation(agg1);
        // 构建第二个聚合条件:平均薪资
//        AvgAggregationBuilder agg2 = AggregationBuilders.avg("agg2").field("balance");
//        sourceBuilder.aggregation(agg2);

        System.out.println("检索条件"+sourceBuilder.toString());

        searchRequest.source(sourceBuilder);

        // 2 执行检索
        SearchResponse response = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
        // 3 分析响应结果
        System.out.println(response.toString());

        // 3.1 获取java bean
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            hit.getId();
            hit.getIndex();
            String sourceAsString = hit.getSourceAsString();
            Users users = JSON.parseObject(sourceAsString, Users.class);
            System.out.println(users);

        }
    }

}
