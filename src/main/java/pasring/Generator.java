package pasring;

import config.XMLConfigLoader;
import model.MongoDefinition;
import writer.BeanWriterSupport;
import writer.TemplateWriterSupport;
import writer.Writer;
import writer.WriterSupport;

import java.io.IOException;
import java.util.List;

public class Generator{

    public static void generator() throws IOException {
        List<MongoDefinition> read = XMLConfigLoader.read();

        GenericAbstractTokenParser genericAbstractTokenParser = new GenericAbstractTokenParser();
        TemplateParsing templateParsing = new DefaultTemplateParsing("#{", "}", genericAbstractTokenParser);
        templateParsing.fillTemplate(read);

        for (MongoDefinition mongoDefinition : read) {
            WriterSupport beanWriterSupport = new BeanWriterSupport();
            Writer beanWriter = new Writer(beanWriterSupport);
            beanWriter.generator(mongoDefinition);
            WriterSupport templateWriterSupport = new TemplateWriterSupport();
            Writer template = new Writer(templateWriterSupport);
            template.generator(mongoDefinition);
        }

    }

}