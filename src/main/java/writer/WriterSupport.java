package writer;

import model.GeneratorFileInfo;
import model.MongoDefinition;

import java.util.List;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public interface WriterSupport {

    List<GeneratorFileInfo> conformity(MongoDefinition mongoDefinition);
}
