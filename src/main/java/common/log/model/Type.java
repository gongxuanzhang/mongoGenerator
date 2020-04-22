package common.log.model;

import java.util.Objects;

public enum Type {

    /***
     * 类型 和对应mongodb api 的$type的数字
     **/
    STRING(2),
    INTEGER(16),
    LONG(18),
    OBJECT(3),
    ARRAY(4),
    DATE(9),
    BOOLEAN(8),
    DOUBLE(1);

    private final int num;
    Type(int num) {
        this.num = num;
    }

    public static String typeInfo(int num){
        Type[] values = values();
        for (Type value : values) {
            if(Objects.equals(num,value.num)){
                return value.toString();
            }
        }
        return null;
    }

}
