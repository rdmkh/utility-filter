package edu.common.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.common.exception.ProcessingException;
import edu.common.handler.Handler;
import edu.common.handler.HandlerFile;
import edu.common.loader.Loader;
import edu.common.loader.ResultFileLoader;
import edu.common.parser.Parser;
import edu.common.parser.ParserCli;
import edu.common.statistics.StatisticsType;
import edu.common.validator.DataTypeValidator;
import edu.common.view.ViewWriter;

import java.util.Optional;

public class AppRunner {
    public static void main(String... args) {
        try {
            Parser parser = new ParserCli();
            runParserArgs(parser, args);

            DataTypeValidator validator = new DataTypeValidator();
            ViewWriter view = new ViewWriter();

            Loader loader =
                    new ResultFileLoader(
                            view,
                            parser.getFilePrefix(),
                            parser.getOutputDirectory(),
                            parser.getRecordModeToFile());

            Handler handler = new HandlerFile(parser.getFilePaths(), view, validator);

            Optional<StatisticsType> statsType = parser.getStatisticsType();
            loader.load(handler.processFiles(statsType.orElse(null)));

        } catch (ProcessingException e) {
            System.err.println("Ошибка обработки: " + e.getMessage());
        } catch (ParameterException e) {
            System.err.println("Ошибка в параметрах командной строки: " + e.getMessage());

            JCommander jCommander = e.getJCommander();
            jCommander.setProgramName("java -jar util.jar");
            jCommander.usage();

        } catch (Exception e) {
            System.err.println("Непредвиденная ошибка: ");
        }
    }

    private static void runParserArgs(Parser parser, String... args) {
        JCommander jc = JCommander.newBuilder().addObject(parser).build();
        jc.parse(args);
    }
}
