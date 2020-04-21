package pasring;

import common.util.StringUtils;
import model.Template;
import model.mongo.MongoCollection;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class DefaultTemplateParsing implements TemplateParsing {


    private final String openToken;

    private final String closeToken;

    private final TokenHandler handler;

    public DefaultTemplateParsing(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }


    /***
     *   解析替换内容  例:
     * +-+-+-+-+-+-+-+---+-+--                      +-+-+-+-+-+-+-+-+
     * |public #{database} aa|     ------->         |public test aa|
     * +-+-+-+-+-+-+-+-+-+-+--                      +-+-+-+-+-+-+-++
     **/


    @Override
    public String analyzeContent(String content, MongoCollection mongoCollection) {
        if (StringUtils.isEmpty(content.trim())) {
            return null;
        }
        int start = content.indexOf(openToken);
        StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        if (start == -1) {
            return content;
        }
        char[] src = content.toCharArray();
        int offset = 0;
        while (start > -1) {
            builder.append(src, offset, start - offset);
            if (expression == null) {
                expression = new StringBuilder();
            } else {
                expression.setLength(0);
            }
            offset = start + openToken.length();
            int end = content.indexOf(closeToken, offset);
            //如果没有找到结尾
            if (end == -1) {
                builder.append(src, start, src.length - start);
                offset = src.length;
            } else {
                expression.append(src, offset, end - offset);
                builder.append(handler.handlerToken(expression.toString(), mongoCollection));
                offset = end + closeToken.length();
            }
            start = content.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();


    }


    @Override
    public String analyzeContent(Template template, MongoCollection mongoCollection) {
        return null;
    }
}
