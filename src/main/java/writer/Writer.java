package writer;

import model.MongoDefinition;

import java.io.IOException;
import java.util.List;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public interface Writer {

    void generator(List<MongoDefinition> mongoDefinitions) throws IOException;
}
