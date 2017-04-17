# thulac4solr
* 这是用来让thulac分词库来支持solr的
* 感谢清华大学实验室开发出这么牛逼的库
* 感谢@yizhiru 我在thulac4j基础上从java8工程移植为支持java7，并且支持solr.

#集成方法
`
<fieldType name="thulac_ik" class="solr.TextField">
    <analyzer type="index" isMaxWordLength="false" class="com.dumpcache.thulac4solr.lucene.ThulacAnalyzer"/>
    <analyzer type="query" isMaxWordLength="true" class="com.dumpcache.thulac4solr.lucene.ThulacAnalyzer"/>
</fieldType>
`