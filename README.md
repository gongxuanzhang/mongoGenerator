# mongoGenerator
唯一支持mongodb的逆向工程




## mongoGenerator能做什么?
```
市面上现有各式各样的Generator逆向工程，但都是针对关系型数据库
而在使用MongoDB的时候，我们经常会遇到对复杂数据结构建立复杂实体的情况
mongoGenerator可以帮你解决此类问题
```

## 功能说明 

* 本项目可以帮你自动生成任何复杂实体
* 本项目可以自由配置模板


## 快速启动 你需要做什么?
1. 下载代码
2. 配置config.xml 执行即可 


## 其他注意事项(你需要拥有的内容)
* 本项目仅支持<b>JDK8</b>及以上版本
* 你的表中至少需要一条数据（因为mongo的表没有结构约束，项目原理是扫描数据，所以你至少需要一条数据）


## 配置文件文档（config.xml）

<b>&lt;Mongo&gt;:</b>
 
属性|类型|例|功能详解
---|---|---|---
id|string|mongo1|每个mongo标签代表一个mongo连接实例,id是唯一标识
host|string|localhost|连接地址
port|int|27017|连接端口
auth|boolean|true|此mongo连接是否需要口令验证
source|string|mongotest|对应mongo连接的source 详情关注mongodbAPI(当auth为false时忽略此属性)
username|string|admin|用户名(当auth为false时忽略此属性)
password|string|123456|密码(当auth为false时忽略此属性)
\<database\>|标签||Mongo标签中至少有一个database标签

<b>&lt;database&gt;:</b>
 
属性|类型|例|功能详解
---|---|---|---
name|string|test1|mongo连接的数据库名称
beanClose|string|DTO|此数据库下的所有<b>子类</b>实体命名的结尾,默认为"BO"
\<collection\>|标签||database标签中至少有一个collection标签


<b>&lt;collection&gt;:</b>
 
属性|类型|例|功能详解
---|---|---|---
name|string|age|数据库中文档名称(表名)
scannerCount|int|5000|程序扫描时扫描此表的数据数量,此属性越高扫描准确性越高，同时性能越低
primaryPackage|string|com.model|实体类写入的包名
innerPackage|string|com.model.bo|如果有属性是对象类型 那么需要继续创建子类实体，此属性是子类实体写入包名
template|string|t1 t2|配置的模板id 用空格隔开
beanClose|string|POJO|<b>子类</b>实体的后缀 如果没配置此属性 默认获取外层database的此属性


<b>&lt;template&gt;:</b>
 
属性|类型|例|功能详解
---|---|---|---
id|string|t1|模板的唯一表示 对应collection中的template属性
name|string|TestController|输出的文件名
package|string|com.controller|此模板写入的包名
"value"|string|public class TestController(){}|模板内容
表达式|#{}|#{dbname}|模板中的#{dbname} #{databasename} #{db} #{database}#{bean}会被解析替换成对应的collection的类名



