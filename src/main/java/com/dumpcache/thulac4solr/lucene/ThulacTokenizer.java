package com.dumpcache.thulac4solr.lucene;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeFactory;

import io.github.yizhiru.thulac4j.SegOnly;

/**
 * Thulac分词器 Lucene Tokenizer适配器类 兼容Lucene 4.0版本
 */
public final class ThulacTokenizer extends Tokenizer {

    //Thulac分词器实现
    private SegOnly           seg;
    private Iterator<String>  tokensIt;

    //词元文本属性
    private CharTermAttribute termAtt;
    //词元位移属性
    private OffsetAttribute   offsetAtt;
    //记录最后一个词元的结束位置
    private int               endPosition;

    private int               index = 0;

    public ThulacTokenizer() {
        super();
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException("init ThulacTokenizer failed:", e);
        }
    }

    public ThulacTokenizer(AttributeFactory factory) {
        super(factory);
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException("init ThulacTokenizer failed:", e);
        }
    }

    private void init() throws IOException {
        offsetAtt = addAttribute(OffsetAttribute.class);
        termAtt = addAttribute(CharTermAttribute.class);
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("seg_only.bin");
        seg = new SegOnly(in);
        char[] buf = new char[1024];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        while ((len = input.read(buf)) != -1) {
            sb.append(buf, 0, len);
        }
        tokensIt = seg.segment(sb.toString()).iterator();
    }

    /*
     * (non-Javadoc)
     * @see org.apache.lucene.analysis.TokenStream#incrementToken()
     */
    @Override
    public boolean incrementToken() throws IOException {
        //清除所有的词元属性
        clearAttributes();
        String nextLexeme = tokensIt.next();
        if (nextLexeme != null) {
            String token = tokensIt.next();
            //将Lexeme转成Attributes
            //设置词元文本
            termAtt.append(token);
            //设置词元长度
            termAtt.setLength(token.length());
            //设置词元位移
            offsetAtt.setOffset(index, index + token.length() - 1);
            //记录分词的最后位置
            endPosition = index + token.length() - 1;
            //返会true告知还有下个词元
            index += token.length();
            return true;
        }
        //返会false告知词元输出完毕
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.lucene.analysis.Tokenizer#reset(java.io.Reader)
     */
    @Override
    public void reset() throws IOException {
        super.reset();
        char[] buf = new char[1024];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        while ((len = input.read(buf)) != -1) {
            sb.append(buf, 0, len);
        }
        tokensIt = seg.segment(sb.toString()).iterator();
    }

    @Override
    public final void end() {
        // set final offset
        int finalOffset = correctOffset(this.endPosition);
        offsetAtt.setOffset(finalOffset, finalOffset);
    }
}
