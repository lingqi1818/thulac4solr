package com.dumpcache.thulac4solr.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * Thulac分词器，Lucene Analyzer接口实现 兼容Lucene 4.0版本
 */
public final class ThulacAnalyzer extends Analyzer {

    /**
     * IK分词器Lucene Analyzer接口实现类 默认细粒度切分算法
     */
    public ThulacAnalyzer() {
        this(false);
    }

    /**
     * Thulac分词器Lucene Analyzer接口实现类
     * 
     * @param useSmart 当为true时，分词器进行智能切分
     */
    public ThulacAnalyzer(boolean useSmart) {
        super();
    }

    /**
     * 重载Analyzer接口，构造分词组件
     */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer _IKTokenizer = new ThulacTokenizer();
        return new TokenStreamComponents(_IKTokenizer);
    }

}
