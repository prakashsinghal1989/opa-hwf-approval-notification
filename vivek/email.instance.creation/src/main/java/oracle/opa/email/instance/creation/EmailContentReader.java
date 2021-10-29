package oracle.opa.email.instance.creation;

import java.util.LinkedList;
import java.util.*;

/**
 * This class is used for processing subject.
 */
public class EmailContentReader {


    List<String> variableTokenNames = new LinkedList<String>();

    public EmailContentReader () {

        variableTokenNames.add("Invoice Amount");
        variableTokenNames.add("Invoice Title");
        variableTokenNames.add("Invoice Date");
        variableTokenNames.add("Currency");
        variableTokenNames.add("Invoice Description");
        variableTokenNames.add("CreatorId");
        variableTokenNames.add("ExpenseType");

    }

    public String getExpenseReportPayload (String emailContent) {
        String payload = "{\n" +
                "  \"variables\": {\n" +
                "    \"invoiceAmount\": {\n" +
                "      \"value\": \"Invoice Amount\",\n" +
                "      \"type\": \"String\"\n" +
                "    },\n" +
                "    \"invoiceTitle\": {\n" +
                "      \"value\": \"Invoice Title\",\n" +
                "      \"type\": \"String\"\n" +
                "    },\n" +
                "    \"invoiceDate\": {\n" +
                "      \"value\": \"Invoice Date\",\n" +
                "      \"type\": \"String\"\n" +
                "    },\n" +
                "    \"currency\": {\n" +
                "      \"value\": \"Currency\",\n" +
                "      \"type\": \"String\"\n" +
                "    },\n" +
                "    \"invoiceDescription\": {\n" +
                "      \"value\": \"Invoice Description\",\n" +
                "      \"type\": \"String\"\n" +
                "    },\n" +
                "    \"creatorId\": {\n" +
                "      \"value\": \"CreatorId\",\n" +
                "      \"type\": \"String\"\n" +
                "    },\n" +
                "    \"subType\": {\n" +
                "      \"value\": \"ExpenseType\",\n" +
                "      \"type\": \"String\"\n" +
                "    },\n" +
                "    \"hasAttachment\": {\n" +
                "      \"value\": \"TRUE\",\n" +
                "      \"type\": \"String\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        for (String variable: variableTokenNames) {
            String value =check( emailContent, variable );
           payload = payload.replace(variable, value );
        }
        return payload;
    }


    private String check(String emailContent, String token) {
        String[] breakup = emailContent.split("\n");
        for(String var:breakup) {
            if (var.contains(token)) {
                String returnValue = var.replace(token, "");
                returnValue = returnValue.replace(",", "");
                returnValue = returnValue.replace(":", "");

                returnValue = returnValue.replace("\n", "");
//System.out.println(returnValue);
                return returnValue.trim();
            }
        }
        return null;

}

}
