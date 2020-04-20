package model.mongo;

/**
 *
 * mongo创建实体时的重复策略
 * @author gxz
 * @date 2020/4/20
 */
public enum RepetitionStrategy {
    /****忽略，写成已经存在的实体**/
    IGNORE,
    /***覆盖**/
    COVER,
    /***转成map**/
    MAP,
    /***转成Object**/
    Object,
    /***转成json**/
    JSON,
    /***创建另外的实体 实体名称将在控制台打印**/
    OTHER;

}
