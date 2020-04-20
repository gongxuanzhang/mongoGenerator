import model.mongo.MongoConnection;
import resolver.DataBaseAnalyze;

import java.util.List;

/**
 * @author gxz
 * @date 2020/4/20 21:47
 */
public class App {
    public static void main(String[] args) {
        List<MongoConnection> analyze = DataBaseAnalyze.resolver().analyze();
        System.out.println(1);

    }
}
