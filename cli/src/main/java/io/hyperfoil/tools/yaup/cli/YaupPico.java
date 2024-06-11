package io.hyperfoil.tools.yaup.cli;

import io.hyperfoil.tools.yaup.json.Json;
import io.hyperfoil.tools.yaup.json.JsonValidator;
import io.hyperfoil.tools.yaup.xml.XmlOperation;
import io.hyperfoil.tools.yaup.xml.pojo.Xml;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import io.quarkus.runtime.QuarkusApplication;
import org.jboss.logging.Logger;
import picocli.CommandLine;

import java.lang.invoke.MethodHandles;
import java.util.List;

@TopCommand
@CommandLine.Command(name="", mixinStandardHelpOptions = true, subcommands={CommandLine.HelpCommand.class})
public class YaupPico implements QuarkusApplication {
    final static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public int run(String... args) throws Exception {
        System.out.println("run(...args)");
        CommandLine cmd = new CommandLine(new YaupPico());
        System.out.println("subcommand count = "+cmd.getSubcommands().size());
        cmd.getSubcommands().keySet().forEach(k->System.out.println("key="+k));
        return cmd.execute(args);
    }

    @CommandLine.Command(name="xml", description="perform the operation on an xml document")
    public int xml(String operation,String path){
        XmlOperation xmlOperation = XmlOperation.parse(operation);
        Xml doc = Xml.parseFile(path);
        String response = doc.apply(operation);
        logger.infof("response:%n%s",response);
        return 0;
    }
    @CommandLine.Command(name="json-schema", description="validate json with a schema")
    public int jsonSchema(String schemaPath,String jsonPath){
        Json schemaJson = Json.fromFile(schemaPath);
        Json dataJson = Json.fromFile(jsonPath);

        JsonValidator validator = new JsonValidator(schemaJson);
        Json errors = validator.validate(dataJson);
        logger.info(errors.toString());
        return 0;
    }
    @CommandLine.Command(name="type-structure", description="create a type structure from the provided json files",aliases = {"shape","typestructure","structure"})
    public int structure(List<String> paths){
        if(paths == null || paths.isEmpty()){
            logger.errorf("missing required paths to json files");
            return 1;
        }
        Json structure = null;
        for(String path : paths){
            Json loaded = Json.fromFile(path);
            Json loadedStructure = Json.typeStructure(loaded);
            if(structure == null){
                structure = loadedStructure;
            }else{
                structure.add(loadedStructure);
            }
        }
        if(structure == null){
            logger.errorf("failed to load structure");
            return 1;
        }else{
            System.out.printf(structure.toString(2));
        }
        return 0;
    }

}