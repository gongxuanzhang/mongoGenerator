package model.mongo;

/**
 *
 * mongo复杂实体的命名策略
 * @author gxz
 * @date 2020/4/20
 */
public enum NameStrategy {
    /****忽略，写成已经存在的实体**/
    IGNORE,
    /***覆盖**/
    COVER,
    /***转成map**/
    MAP,
    /***转成Object**/
    Object,
    /***创建另外的实体 实体名称将在控制台打印**/
    OTHER,
    /***默认添加BO 也支持自定义**/
    AUTO;

}
