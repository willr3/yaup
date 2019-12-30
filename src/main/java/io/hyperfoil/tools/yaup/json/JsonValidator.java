package io.hyperfoil.tools.yaup.json;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;

import java.util.Set;

public class JsonValidator {

   private Json schemaJson;
   private JsonSchema jsonSchema;

   public JsonValidator(Json schema) {
      this.schemaJson = schema;
      this.jsonSchema = JsonSchemaFactory.getInstance().getSchema(Json.toJsonNode(schemaJson));
   }

   public Json validate(Json input) {
      Json rtrn = new Json();
      Set<ValidationMessage> errors = jsonSchema.validate(Json.toJsonNode(input));
      errors.forEach(validationMessage -> {
         Json entry = new Json();
         entry.set("message", validationMessage.getMessage());
         entry.set("code", validationMessage.getCode());
         entry.set("path", validationMessage.getPath());
         entry.set("arguemnts", new Json(true));
         for (String arg : validationMessage.getArguments()) {
            entry.getJson("arguments").add(arg);
         }
         entry.set("details", new Json(false));
         validationMessage.getDetails().forEach((k, v) -> {
            entry.getJson("details").set(k, v);
         });
         rtrn.add(entry);
      });
      return rtrn;
   }

   public Json getSchema() {
      return schemaJson;
   }
}
