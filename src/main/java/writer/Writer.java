package writer;

import model.GeneratorModel;

import java.io.IOException;
import java.util.List;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public interface Writer {

    void generator(List<GeneratorModel> generatorModels) throws IOException;
}
