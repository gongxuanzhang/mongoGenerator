package pasring;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoCommandException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import common.log.model.Type;
import common.util.CollectionUtils;
import model.GeneratorFileInfo;
import model.GeneratorModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class MongoParsing {
    private static Logger logger = LogManager.getLogger(MongoParsing.class);

    final private MongoCollection<Document> collection;

    final private int scanCount;

    private List<String> colNames;

    private final static int[] TYPE = {3, 16, 18, 8, 9, 2, 1};

    private final static int ARRAY_TYPE = 4;


    public MongoParsing(MongoCollection<Document> collection, int scanCount) {
        this.collection = collection;
        this.scanCount = scanCount > 400000 ? 400000 : scanCount;
    }

    public GeneratorModel process() {
        //初始化
        initColNames();
        //解析属性值
        return processType();
    }

    /**
     * 功能描述:分组发送聚合函数(获得一级属性名)
     *
     * @author : gxz
     * @date : 2019/7/4 16:24
     */
    public List<String> groupAggregation(Integer skip, Integer limit) throws MongoCommandException {
        if (skip == null) skip = 0;
        if (limit == null) limit = 400000;
        MongoCollection<Document> collection = this.collection;
        BasicDBObject $project = new BasicDBObject("$project", new BasicDBObject("arrayofkeyvalue", new BasicDBObject("$objectToArray", "$$ROOT")));
        BasicDBObject $unwind = new BasicDBObject("$unwind", "$arrayofkeyvalue");
        BasicDBObject $skip = new BasicDBObject("$skip", skip);
        BasicDBObject $limit = new BasicDBObject("$limit", limit);
        BasicDBObject basicDBObject = new BasicDBObject("_id", "null");
        basicDBObject.append("allkeys", new BasicDBObject("$addToSet", "$arrayofkeyvalue.k"));
        BasicDBObject $group = new BasicDBObject("$group", basicDBObject);
        List<BasicDBObject> dbStages = Arrays.asList($project, $skip, $limit, $unwind, $group);
        //System.out.println(dbStages);  发送的聚合函数   获得所有参数名称
        AggregateIterable<Document> aggregate = collection.aggregate(dbStages);
        Document document = aggregate.first();
        if (document == null) {
            BasicDBObject existsQuery = new BasicDBObject("$ROOT", new BasicDBObject("$exists", true));
            MongoCursor<Document> existsList = collection.find(existsQuery).limit(100).iterator();
            Set<String> keySet = new HashSet<>();
            while (existsList.hasNext()) {
                Document next = existsList.next();
                Map<String, Object> keyMap = (Document) next.get("$ROOT");
                keySet.addAll(keyMap.keySet());
            }
            return new ArrayList<>(keySet);
        } else {
            return (List<String>) document.get("allkeys");
        }

    }

    public GeneratorModel processParameter() {
        GeneratorModel result = new GeneratorModel();
        result.setPropertyName(this.collection.getNamespace().getCollectionName());
        result.setType(3).setArray(false);
        List<String> colNames = this.colNames;
        List<GeneratorModel> children = new ArrayList<>();
        for (String colName : colNames) {
            GeneratorModel generatorModel = this.processNameType(colName);
            children.add(generatorModel);
        }
        return result.setChild(children);
    }

    /**
     * 如果一个文档是对象类型  获得这个属性的下一级的属性名的集合
     * 例子: user:{name:"张三",age:12}  传入user  返回[name,age]
     *
     * @param parameterName 上层参数名  这个参数名可以包含一个或多个.
     *                      注: 参数传递之前需确认:  1.上层属性一定是对象类型
     * @return 返回这个属性内的所有属性名
     */
    public Set<String> getNextParameterNames(String parameterName) {
        Document condition = new Document(parameterName, new Document("$exists", true));
        Document match = new Document("$match", condition);
        String unwindName = parameterName;
        if (parameterName.contains(".")) {
            unwindName = parameterName.split("\\.")[0];
        }
        Document unwind = new Document("$unwind", "$" + unwindName);
        Document limit = new Document("$limit", 1);
        Document project = new Document("$project", new Document("list", "$" + parameterName).append("_id", false));
        Document unwind2 = new Document("$unwind", "$list");
        AggregateIterable<Document> aggregate = this.collection.aggregate(Arrays.asList(match, unwind, limit, project, unwind2));
        Set<String> names = new HashSet<>();
        for (Document document : aggregate) {
            Object list = document.get("list");
            if (list instanceof Map) {
                Set<String> documentNames = ((Document) list).keySet();
                names.addAll(documentNames);
            }
        }
        logger.info("解析"+parameterName+"有"+names.size()+"个子属性");
        return names;
    }



    /**
     * 功能描述:提供属性名 解析属性类型
     * 获取相应的属性信息  封装成generator对象
     *
     * @author: gxz
     * @param: propertyName属性名 可以是层级名  比如 name 也可以是info.name
     * @return: 解析之后的Model {@see #GeneratorModel}
     * @see GeneratorModel
     */

    public GeneratorModel processNameType(String propertyName) {
        MongoCollection<Document> collection = this.collection;
        GeneratorModel result = new GeneratorModel();
        if ("_id".equals(propertyName)) {
            result.setType(2);
            result.setPropertyName("_id");
            return result;
        }
        result.setPropertyName(propertyName);
        MongoCursor<Document> isArray = collection.find(new Document(propertyName, new Document("$type", ARRAY_TYPE))).limit(1).iterator();
        if (isArray.hasNext()) {
            result.setArray(true);
            for (int i : TYPE) {
                MongoCursor<Document> iterator = collection.find(new Document(propertyName, new Document("$type", i))).limit(1).iterator();
                if (iterator.hasNext()) {
                    if (i == 3) {
                        result.setChild(this.produceChildList(propertyName));
                    }
                    //1是double 2是string 3是对象 4是数组 16是int 18 是long
                    result.setType(i);
                    logger.info("解析["+propertyName+"]是[List]["+Type.typeInfo(result.getType())+"]");
                    return result;
                }
            }
        } else {
            for (int i : TYPE) {
                MongoCursor<Document> iterator = collection.find(new Document(propertyName, new Document("$type", i))).limit(1).iterator();
                if (iterator.hasNext()) {
                    if (i == 3) {
                        result.setChild(this.produceChildList(propertyName));
                    }
                    //1是double 2是string 3是对象 4是数组 16是int 18 是long
                    //到这里就是数组了
                    result.setType(i);
                    logger.info("解析["+propertyName+"]是["+Type.typeInfo(result.getType())+"]");
                    return result;
                }
            }
            result.setType(2);
        }
        logger.info("解析["+propertyName+"]是["+Type.typeInfo(result.getType())+"]");
        return result;
    }


    private List<GeneratorModel> produceChildList(String parentName) {
        Set<String> nextParameterNames = this.getNextParameterNames(parentName);
        List<String> strings = new ArrayList<>(nextParameterNames);
        List<String> collect = strings.stream().map(name -> parentName + "." + name).collect(Collectors.toList());
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<List<GeneratorModel>> task = new ForkJoinProcessType(collect);
        return pool.invoke(task);
    }

    private List<String> distinctAndJoin(List<String> a, List<String> b) {
        a.removeAll(b);
        a.addAll(b);
        return a;
    }


    /**
     * 功能描述:解析这个集合的列名  用ForkJoin框架实现
     *
     * @author: gxz
     * @date: 2019/7/5 14:48
     */
    public void initColNames() {
        long start = System.currentTimeMillis();
        int scan = this.scanCount;
        if (scan < 400000) scan = 400000;
        long count = this.collection.countDocuments();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<List<String>> task;
        if (count > (long) scan) {
            task = new ForkJoinGetProcessName(0, scan);
        } else {
            task = new ForkJoinGetProcessName(0, (int) count);
        }
        this.colNames = pool.invoke(task);
        logger.info("collection[" + this.collection.getNamespace().getCollectionName() +
                "]初始化列名成功.....     用时: " + (System.currentTimeMillis() - start) + "毫秒");
    }

    public GeneratorModel processType() {
        GeneratorModel result = new GeneratorModel();
        List<String> colNames = this.colNames;
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<List<GeneratorModel>> task = new ForkJoinProcessType(colNames);
        List<GeneratorModel> invoke = pool.invoke(task);
        return result.setChild(invoke).setType(3).setPropertyName(this.collection.getNamespace().getCollectionName());
    }

    /**
     * 功能描述:forkJoin多线程框架的实现  通过业务拆分解析类型
     *
     * @author: gxz
     */
    class ForkJoinProcessType extends RecursiveTask<List<GeneratorModel>> {
        List<String> names;
        private final int THRESHOLD = 4;

        ForkJoinProcessType(List<String> names) {
            this.names = names;
        }

        @Override
        protected List<GeneratorModel> compute() {
            if (names.size() <= THRESHOLD) {
                List<GeneratorModel> result = new ArrayList<>();
                for (String name : names) {
                    result.add(processNameType(name));
                }
                return result;
            } else {
                int size = names.size();
                int middle = size / 2;
                List<String> leftList = names.subList(0, middle);
                List<String> rightList = names.subList(middle, size);
                ForkJoinProcessType pre = new ForkJoinProcessType(leftList);
                pre.fork();
                ForkJoinProcessType next = new ForkJoinProcessType(rightList);
                next.fork();
                return CollectionUtils.mergeList(pre.join(), next.join());
            }
        }
    }

    /**
     * 功能描述:forkJoin多线程框架的实现  通过业务拆分获得属性名
     *
     * @author: gxz
     */
    class ForkJoinGetProcessName extends RecursiveTask<List<String>> {
        private int begin; //查询开始位置
        private int end;
        private final int THRESHOLD = 130000;

        ForkJoinGetProcessName(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected List<String> compute() {
            int count = end - begin;
            if (THRESHOLD >= count) {
                return groupAggregation(begin, count);
            } else {
                int middle = (begin + end) / 2;
                ForkJoinGetProcessName pre = new ForkJoinGetProcessName(begin, middle);
                pre.fork();
                ForkJoinGetProcessName next = new ForkJoinGetProcessName(middle + 1, end);
                next.fork();
                return distinctAndJoin(pre.join(), next.join()); //去重合并
            }
        }
    }
}
