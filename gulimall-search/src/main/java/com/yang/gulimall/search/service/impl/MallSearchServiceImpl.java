package com.yang.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yang.common.to.es.SkuEsModel;
import com.yang.common.utils.R;
import com.yang.gulimall.search.config.GulimallElasticSearchConfig;
import com.yang.gulimall.search.constant.EsConstant;
import com.yang.gulimall.search.feign.ProductFeignService;
import com.yang.gulimall.search.service.MallSearchService;
import com.yang.gulimall.search.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TangYuan
 * @create 2021--06--28--01:35
 * @description
 */
@Service
@Slf4j
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public SearchReult search(SearchParam param) {
        //??????????????????????????????dsl???dsk??????doc/json/gulimall_product_dsl.json???

        SearchReult result=null;
        //1. ??????????????????
        SearchRequest searchRequest =buildSearchRequest(param);
        try {
            //2.??????????????????
            SearchResponse response = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
            //3.???????????????????????????????????????????????????
            result = buildSearchResult(param,response);
        } catch (IOException e) {

        }


        return result;
    }

    /**
     * ??????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam param) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        /**
         * ?????????????????????????????????????????????????????????????????????????????????
         */
        //1. ??????bool-query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //1.1 bool-must
        if(!StringUtils.isEmpty(param.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle",param.getKeyword()));
        }

        //1.2 bool-fiter
        //1.2.1 catelogId
        if(null != param.getCatalog3Id()){
            boolQueryBuilder.filter(QueryBuilders.termQuery("catelogId",param.getCatalog3Id()));
        }

        //1.2.2 brandId
        if(null != param.getBrandId() && param.getBrandId().size() >0){
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId",param.getBrandId()));
        }

        //1.2.3 attrs
        if(param.getAttrs() != null && param.getAttrs().size() > 0){
            param.getAttrs().forEach(item -> {
                //attrs=1_5???:8???&2_16G:8G
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

                //attrs=1_5???:8???
                String[] s = item.split("_");
                String attrId=s[0];
                String[] attrValues = s[1].split(":");//???????????????????????????
                boolQuery.must(QueryBuilders.termQuery("attrs.attrId",attrId));
                boolQuery.must(QueryBuilders.termsQuery("attrs.attrValue",attrValues));

                NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("attrs",boolQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQueryBuilder);
            });

        }

        //1.2.4 hasStock
        if(null != param.getHasStock()){
            boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock",param.getHasStock() == 1));
        }


        //1.2.5 skuPrice
        if(!StringUtils.isEmpty(param.getSkuPrice())){
            //skuPrice????????????1_500???_500???500_
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("skuPrice");
            String[] price = param.getSkuPrice().split("_");
            if(price.length==2){
                rangeQueryBuilder.gte(price[0]).lte(price[1]);
            }else if(price.length == 1){
                if(param.getSkuPrice().startsWith("_")){
                    rangeQueryBuilder.lte(price[1]);
                }
                if(param.getSkuPrice().endsWith("_")){
                    rangeQueryBuilder.gte(price[0]);
                }
            }
            boolQueryBuilder.filter(rangeQueryBuilder);
        }

        //???????????????????????????
        searchSourceBuilder.query(boolQueryBuilder);


        /**
         * ????????????????????????
         */

        //??????
        //?????????sort=hotScore_asc/desc
        if(!StringUtils.isEmpty(param.getSort())){
            String sort = param.getSort();
            String[] sortFileds = sort.split("_");

            SortOrder sortOrder="asc".equalsIgnoreCase(sortFileds[1])?SortOrder.ASC:SortOrder.DESC;

            searchSourceBuilder.sort(sortFileds[0],sortOrder);
        }

        //??????
        searchSourceBuilder.from((param.getPageNum()-1)* EsConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        //??????
        if(!StringUtils.isEmpty(param.getKeyword())){

            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");

            searchSourceBuilder.highlighter(highlightBuilder);
        }

        /**
         * ????????????
         */
        //1. ????????????????????????
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);


        //1.1 ??????????????????-???????????????
        brand_agg.subAggregation(AggregationBuilders.terms("brand_Name_agg").field("brandName").size(1));
        //1.2 ??????????????????-??????????????????
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));

        searchSourceBuilder.aggregation(brand_agg);

        //2. ??????????????????????????????
        TermsAggregationBuilder catelog_agg = AggregationBuilders.terms("catelog_agg");
        catelog_agg.field("catelogId").size(20);

        catelog_agg.subAggregation(AggregationBuilders.terms("catelog_name_agg").field("catelogName").size(1));

        searchSourceBuilder.aggregation(catelog_agg);

        //2. ??????????????????????????????
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        //2.1 ????????????ID????????????
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        attr_agg.subAggregation(attr_id_agg);
        //2.1.1 ???????????????ID?????????????????????????????????
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        //2.1.1 ???????????????ID?????????????????????????????????
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        searchSourceBuilder.aggregation(attr_agg);

        System.out.println(searchSourceBuilder.toString());
        log.info("?????????DSL?????? {}",searchSourceBuilder.toString());

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX},searchSourceBuilder);


        return searchRequest;
    }

    private SearchReult buildSearchResult(SearchParam param,SearchResponse response) {
        SearchReult result = new SearchReult();
        SearchHits hits = response.getHits();

        SearchHit[] subHits = hits.getHits();
        List<SkuEsModel> skuEsModels=null;
        if(subHits != null && subHits.length > 0){
            skuEsModels = Arrays.asList(subHits).stream().map(subHit -> {
                String sourceAsString = subHit.getSourceAsString();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                if (!StringUtils.isEmpty(param.getKeyword())) {
                    HighlightField skuTitle = subHit.getHighlightFields().get("skuTitle");
                    String skuTitleHighLight = skuTitle.getFragments()[0].string();
                    skuEsModel.setSkuTitle(skuTitleHighLight);
                }
                return skuEsModel;
            }).collect(Collectors.toList());

        }

        //1.?????????????????????????????????
        result.setProducts(skuEsModels);

        //2.???????????????????????????????????????????????????
        ParsedNested attr_agg = response.getAggregations().get("attr_agg");
        ParsedLongTerms attr_id_agg = attr_agg.getAggregations().get("attr_id_agg");
        List<AttrVo> attrVos = attr_id_agg.getBuckets().stream().map(item -> {
            AttrVo attrVo = new AttrVo();
            //1.???????????????id
            long attrId = item.getKeyAsNumber().longValue();

            //2.???????????????
            String attrName = ((ParsedStringTerms) item.getAggregations().get("attr_name_agg")).getBuckets().get(0).getKeyAsString();

            //3.????????????????????????
            List<String> attrValues = ((ParsedStringTerms) item.getAggregations().get("attr_value_agg")).getBuckets().stream().map(bucket -> {
                return bucket.getKeyAsString();
            }).collect(Collectors.toList());

            attrVo.setAttrId(attrId);
            attrVo.setAttrName(attrName);
            attrVo.setAttrValue(attrValues);


            return attrVo;
        }).collect(Collectors.toList());

        result.setAttrs(attrVos);

        //3.???????????????????????????????????????????????????
        ParsedLongTerms brand_agg = response.getAggregations().get("brand_agg");
        List<BrandVo> brandVos = brand_agg.getBuckets().stream().map(item -> {
            BrandVo brandVo = new BrandVo();
            //1.??????id
            long brandId = item.getKeyAsNumber().longValue();
            //2.???????????????
            String brandName = ((ParsedStringTerms) item.getAggregations().get("brand_Name_agg")).getBuckets().get(0).getKeyAsString();
            //3.??????????????????
            String brandImag = ((ParsedStringTerms) item.getAggregations().get("brand_img_agg")).getBuckets().get(0).getKeyAsString();

            brandVo.setBrandId(brandId);
            brandVo.setBrandName(brandName);
            brandVo.setBrandImg(brandImag);
            return brandVo;
        }).collect(Collectors.toList());

        result.setBrands(brandVos);

        //4.???????????????????????????????????????????????????
        ParsedLongTerms catelog_agg = response.getAggregations().get("catelog_agg");
        List<CatelogVo> catelogVos = catelog_agg.getBuckets().stream().map(item -> {
            CatelogVo catelogVo = new CatelogVo();
            //????????????ID
            String catelogId = item.getKeyAsString();
            catelogVo.setCatelogId(Long.parseLong(catelogId));

            //???????????????
            ParsedStringTerms catelog_name_agg = item.getAggregations().get("catelog_name_agg");
            String catelogName = catelog_name_agg.getBuckets().get(0).getKeyAsString();
            catelogVo.setCatelogName(catelogName);
            return catelogVo;
        }).collect(Collectors.toList());

        result.setCatelogs(catelogVos);

        //=========??????????????????????????????===========
        //5.????????????-??????

        result.setPageNum(param.getPageNum());
        //5.????????????-????????????
        long total = hits.getTotalHits().value;
        result.setTotal(total);
        //5.????????????-?????????
        boolean flag=total%EsConstant.PRODUCT_PAGESIZE == 0;
        int totalPage=flag?(int)total/EsConstant.PRODUCT_PAGESIZE:((int)total/EsConstant.PRODUCT_PAGESIZE)+1;
        result.setTotalPages(totalPage);

        ArrayList<Integer> page = new ArrayList<>();
        for (int i=1;i<=totalPage;i++){
            page.add(i);
        }

        result.setPageNavs(page);


        //???????????????????????????
        List<String> attrs = param.getAttrs();
        if (attrs != null && attrs.size() > 0) {
            List<SearchReult.NavVo> navVos = param.getAttrs().stream().map(attr -> {
                SearchReult.NavVo navVo = new SearchReult.NavVo();
                String[] s = attr.split("_");
                navVo.setNavValue(s[1]);

                R info = productFeignService.info(Long.parseLong(s[0]));
                if(info.getCode() == 0){
                    AttrResponseVo data = info.getData("attr", new TypeReference<AttrResponseVo>() {
                    });
                    navVo.setNavName(data.getAttrName());
                }else {
                    navVo.setNavName(s[0]);
                }

                //2.?????????????????????????????????????????????????????????????????????URL??????
                //???????????????????????????
                String queryString = param.get_queryString();
                String replace = queryString.replace("&attrs=" + attr, "").replace("attrs=" + attr+"&", "").replace("attrs=" + attr, "");
                navVo.setLink("http://search.gulimall.com/list.html" + (replace.isEmpty()?"":"?"+replace));
                return navVo;
            }).collect(Collectors.toList());

            result.setNavs(navVos);
        }


        return result;
    }
}

