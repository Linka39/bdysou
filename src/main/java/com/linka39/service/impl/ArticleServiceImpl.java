package com.linka39.service.impl;

import com.linka39.entity.Article;
import com.linka39.entity.es.ArticleInfo;
import com.linka39.repository.ArticleRepository;
import com.linka39.service.ArticleService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 帖子Service实现类
 * @author linka39
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Article> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Article> articlePage = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("state"),1)); // 资源解析成功
                predicate.getExpressions().add(cb.equal(root.get("isIndex"),true)); // es索引添加成功
                return predicate;
            }
        }, pageRequest);
        return articlePage.getContent();
    }

    @Override
    public List<Article> adminList(Article s_article, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Article> articlePage = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                if(s_article!=null){
                    if(s_article.getId()!=null){
                        predicate.getExpressions().add(cb.equal(root.get("id"),s_article.getId()));
                    }
                    if(s_article.getName()!=null){
                        predicate.getExpressions().add(cb.like(root.get("name"),"%"+s_article.getName()+"%"));
                    }
                }
                return predicate;
            }
        }, pageRequest);
        return articlePage.getContent();
    }

    @Override
    public Long getAdminCount(Article s_article) {
        return articleRepository.count(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                if(s_article!=null){
                    if(s_article.getId()!=null){
                        predicate.getExpressions().add(cb.equal(root.get("id"),s_article.getId()));
                    }
                    if(s_article.getName()!=null){
                        predicate.getExpressions().add(cb.like(root.get("name"),"%"+s_article.getName()+"%"));
                    }
                }
                return predicate;
            }
        });
    }

    @Override
    public Long getCount() {
        return articleRepository.count(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("state"),1)); // 资源解析成功
                predicate.getExpressions().add(cb.equal(root.get("isIndex"),true)); // es索引添加成功
                return predicate;
            }
        });
    }

    @Override
    public Article get(Integer id) {
        return articleRepository.getOne(id);
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delete(Integer id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<ArticleInfo> search(Integer page, Integer pageSize, String searchContent) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name",searchContent))
                .should(QueryBuilders.matchQuery("content",searchContent));

        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                 .withIndices("test2")
                // 对高亮部分代码加以修饰
                .withHighlightBuilder(new HighlightBuilder().preTags("<font style='color:red'>").postTags("</font>"))
                .withHighlightFields(new HighlightBuilder.Field("content"),new HighlightBuilder.Field("name")).build();

        AggregatedPage<ArticleInfo> articleInfos = elasticsearchTemplate.queryForPage(nativeSearchQuery, ArticleInfo.class, new SearchResultMapper(){
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable){
                ArrayList<ArticleInfo> articleInfos = new ArrayList<>();
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    String name = (String) sourceAsMap.get("name");
                    String content = (String) sourceAsMap.get("content");
                    String id = (String) sourceAsMap.get("id");
                    System.out.println("name:" + name);
                    System.out.println("content:" + content);
                    ArticleInfo articleInfo = new ArticleInfo();
                    articleInfo.setId(Long.valueOf(id));
                    // 获取高亮部分
                    HighlightField contentHighlightField = hit.getHighlightFields().get("content");
                    if (contentHighlightField == null) {
                        articleInfo.setContent(content);
                    } else {
                        String highlightContent = hit.getHighlightFields().get("content").fragments()[0].toString();
                        articleInfo.setContent(highlightContent.replaceAll("br", "").replaceAll("&nbsp;", "").replaceAll("_", ""));
                    }
                    HighlightField nameHighlightField = hit.getHighlightFields().get("name");
                    if (nameHighlightField == null) {
                        articleInfo.setName(name);
                    } else {
                        articleInfo.setName(hit.getHighlightFields().get("name").fragments()[0].toString());
                    }
                    articleInfos.add(articleInfo);
                }

                if (articleInfos.size() > 0) {
                    return new AggregatedPageImpl<T>((List<T>) articleInfos);
                }
                return null;
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });
        return articleInfos==null?null:articleInfos.getContent();
    }

    @Override
    public Long searchCount(String searchContent) {
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name",searchContent))
                .should(QueryBuilders.matchQuery("content",searchContent));

        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withIndices("test2").build();

        return elasticsearchTemplate.count(nativeSearchQuery);
    }
}
