package com.opa.poc.recomendations.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import com.opa.poc.recomendations.model.NotificationPayload;
import com.opa.poc.recomendations.model.TimePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomTree;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class RecommendationUtils {


    private static final String modelpath = "/Users/vivvverm/IdeaProjects/PrakashGit/opa-hwf-poc/vivek/recomendation-service/src/main/resources/expense_train_levelsonly_java2.model";
    private static final String timeModelpath = "/Users/vivvverm/IdeaProjects/PrakashGit/opa-hwf-poc/vivek/recomendation-service/src/main/resources/sid/expected_wait_time_java2.model";
    private static final String trainingArff = "/Users/vivvverm/IdeaProjects/PrakashGit/opa-hwf-poc/vivek/recomendation-service/src/main/resources/expense_train_levelsonly.arff";
    private static final String timeTrainingArff = "/Users/vivvverm/IdeaProjects/PrakashGit/opa-hwf-poc/vivek/recomendation-service/src/main/resources/sid/expected_wait_time.arff";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RecommendationUtils recommendationUtils;

    public static void createModel(){
        createOutcomeModel();
        createTimeModel();
    }
    public static void createTimeModel(){
        DataSource source;
        try {
            source = new DataSource(timeTrainingArff);
            Instances trainDataset = source.getDataSet();
            trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
            int numClass = trainDataset.numClasses();

            for (int i = 0; i < numClass; i++) {
                String classValue = trainDataset.classAttribute().value(i);
                System.out.println("classValue " + i + " is " + classValue);
            }

            IBk ibk = new IBk();
            ibk.buildClassifier(trainDataset);

            SerializationHelper.write(timeModelpath, ibk);
            System.out.println(">>>>>>>>>>Completed::");

        } catch (Exception e) {
            System.out.println(">>>>>>>>>>>Caught Exception ::" + e.getMessage());
            e.printStackTrace();
        }

    }
    public static void createOutcomeModel() {
        // TODO Auto-generated constructor stub
        DataSource source;
        try {
            source = new DataSource(trainingArff);
            Instances trainDataset = source.getDataSet();
            trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
            int numClass = trainDataset.numClasses();

            for (int i = 0; i < numClass; i++) {
                String classValue = trainDataset.classAttribute().value(i);
                System.out.println("classValue " + i + " is " + classValue);
            }

            RandomTree rm = new RandomTree();
            rm.buildClassifier(trainDataset);

            SerializationHelper.write(modelpath, rm);
            System.out.println(">>>>>>>>>>Completed::");

        } catch (Exception e) {
            System.out.println(">>>>>>>>>>>Caught Exception ::" + e.getMessage());
            e.printStackTrace();
        }

    }

    public void readfile()
    {
        try {
            RecommendationUtils ru = new RecommendationUtils();
            InputStream is = ru.getClass().getResourceAsStream("/application.properties");
            System.out.println("RecommendationUtils.readfile" + is);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Expense,CarRental,11061,FALSE,2,4
        //Expense,Airfare,269360,FALSE,1,9
       TimePayload payload = new TimePayload();
        payload.setProcessName("Expense");
        payload.setCategory("Airfare");
        payload.setAmount("269360");
        payload.setPriority("3");
        payload.setHasAttachment("TRUE");
        RecommendationUtils recommendationUtils = new RecommendationUtils();
        recommendationUtils.predictTime(payload);
    }

    public String predictTime(TimePayload payload){

        final List<String> processNames = new ArrayList<String>() {
            {
                add("Expense");
            }
        };

        final List<String> categories = new ArrayList<String>() {
            {
                add("Airfare");
                add("Mobile");
                add("CarRental");
                add("Food");
            }
        };

        final List<String> hasAttachments = new ArrayList<String>() {
            {
                add("TRUE");
                add("FALSE");
            }
        };

        final Attribute processName = new Attribute("processName", processNames);
        final Attribute category = new Attribute("category", categories);
        final Attribute amount = new Attribute("amount");
        final Attribute hasAttachment = new Attribute("hasAttachment", hasAttachments);
        final Attribute priority = new Attribute("priority");
        final Attribute outcome = new Attribute("outcome");

        ArrayList<Attribute> attributeList = new ArrayList<Attribute>() {
            {
                add(processName);
                add(category);
                add(amount);
                add(hasAttachment);
                add(priority);
                add(outcome);
            }
        };

        Instances unpredictedData = new Instances("TestInstance", attributeList, 1);
        unpredictedData.setClassIndex(unpredictedData.numAttributes() - 1);
        DenseInstance instanceToPredict = new DenseInstance(unpredictedData.numAttributes()) {
            {
                setValue(processName, payload.getProcessName());
                setValue(category, payload.getCategory());
                setValue(amount, Integer.valueOf(payload.getAmount()));
                setValue(hasAttachment, payload.getHasAttachment());
                setValue(priority, Integer.valueOf(payload.getPriority()));
            }
        };

        instanceToPredict.setDataset(unpredictedData);

        //Import training model
        Classifier cls = null;
        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream("/sid/expected_wait_time_java2.model");
            System.out.println("RecommendationUtils.predictTimeOutcome:: trying to read model is::"+is);
            cls = (IBk) SerializationHelper.read(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cls == null) {
            System.out.println("RecommendationUtils.predictTimeOutcome:: CLS is NUll-- returning");
            return null;
        }

        //Predict new sample data
        String predString = null;
        double result = -1;
        try {
            result = cls.classifyInstance(instanceToPredict);
            System.out.println(">>>>>>Predicted Time" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(Math.ceil(result));
    }

    public  String predictOutcome(NotificationPayload payload) {

        final List<String> processNames = new ArrayList<String>() {
            {
                add("Expense");
            }
        };
        final List<String> subTypes = new ArrayList<String>() {
            {
                add("Airfare");
                add("Mobile");
                add("CarRental");
                add("Food");
            }
        };
        final List<String> creatorLevels = new ArrayList<String>() {
            {
                add("Employee");
                add("Manager");
            }
        };
        final List<String> assigneeLevels = new ArrayList<String>() {
            {
                add("Manager");
                add("SrManager");
                add("CEO");
            }
        };
        final List<String> classes = new ArrayList<String>() {
            {
                add("APPROVE");
                add("REJECT");
            }
        };

        final Attribute processName = new Attribute("processName", processNames);
        final Attribute subtype = new Attribute("subtype", subTypes);
        final Attribute creatorLevel = new Attribute("creatorLevel", creatorLevels);
        final Attribute assigneeLevel = new Attribute("assigneeLevel", assigneeLevels);
        final Attribute amount = new Attribute("amount");


        ArrayList<Attribute> attributeList = new ArrayList<Attribute>() {
            {
                add(processName);
                add(subtype);
                add(creatorLevel);
                add(assigneeLevel);
                add(amount);
                Attribute classAttribute = new Attribute("class", classes);
                add(classAttribute);
            }
        };

        Instances unpredictedData = new Instances("TestInstance", attributeList, 1);
        unpredictedData.setClassIndex(unpredictedData.numAttributes() - 1);
        DenseInstance instanceToPredict = new DenseInstance(unpredictedData.numAttributes()) {
            {
                setValue(processName, payload.getProcessName());
                setValue(subtype, payload.getSubType());
                setValue(creatorLevel, payload.getCreatorLevel());
                setValue(assigneeLevel, payload.getAssigneeLevel());
                setValue(amount, Integer.valueOf(payload.getInvoiceAmount()));
            }
        };

        instanceToPredict.setDataset(unpredictedData);

        //Import training model
        Classifier cls = null;
        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream("/expense_train_levelsonly_java2.model");
            System.out.println("RecommendationUtils.predictOutcome:: trying to read model is is::"+is);
            cls = (RandomTree) SerializationHelper.read(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cls == null) {
            System.out.println("RecommendationUtils.predictOutcome:: CLS is NUll-- returning");
            return null;
        }

        //Predict new sample data
        String predString = null;
        try {
            double result = cls.classifyInstance(instanceToPredict);
            System.out.println("Index of predicted class label:" + result + ", which corresponds to class:" + classes.get(new Double(result).intValue()));
			predString = instanceToPredict.classAttribute().value((int) result);
            System.out.println(">>>>>>>>>predString::" + predString);
            double[] probabilityDistribution = cls.distributionForInstance(instanceToPredict);
            for (double d : probabilityDistribution) {
                System.out.println(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		return predString;
    }


    public void updateAndCompleteTask(String taskUrl, String processInstanceId, String outcome){
        //Update the variable
        try {
            System.out.println("RecommendationUtils.updateAndCompleteTask:: taskUrl:"+taskUrl);
            String baseSrvUrl = "http://camunda-hwf-engine-svc:8080/engine-rest/";
            String k8TaskBaseUrl = baseSrvUrl+"task/"+taskUrl.substring(taskUrl.lastIndexOf("/")+1);
            System.out.println("k8TaskUrl = " + k8TaskBaseUrl);
            System.out.println("restTemplate = " + restTemplate);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //String requestJson = String.format("{\"value\":\"%s\", \"type\":\"String\"}", outcome);
            String requestJson = String.format("{\n" +
                    "   \"modifications\":{\n" +
                    "      \"outcome\":{\n" +
                    "         \"type\":\"String\",\n" +
                    "         \"value\":\"%s\"\n" +
                    "      }\n" +
                    "   }\n" +
                    "}", outcome);
            String reqJson = String.format("   \"modifications\":{\n" +
                    "      \"outcome\":{\n" +
                    "      \t\"type\":\"String\",\n" +
                    "      \t\"value\":\"%s\",\n" +
                    "      \t\"valueInfo\":{}\n" +
                    "\t   }\n" +
                    "   }\n" +
                    "}\n", outcome);
            System.out.println("requestJson = " + reqJson);
            HttpEntity<String> entity = new HttpEntity<String>(reqJson, headers);
            boolean putFailed = false;
            try {
                String updateProcessVariableUrl = baseSrvUrl+"process-instance/" + processInstanceId+"/variables";
                System.out.println("updateProcessVariableUrl = " + updateProcessVariableUrl);
                ResponseEntity e = restTemplate.postForEntity(updateProcessVariableUrl, entity, String.class);
                System.out.println("e.getStatusCode() = " + e.getStatusCode());
                System.out.println("RecommendationUtils.updateAndCompleteTask:: Updated Process with outcome value");
            }catch (Exception e){
                System.out.println("Caught Exception in PUT with svc host = " + e.getMessage());
                putFailed = true;
                e.printStackTrace();
            }
            String postJson = "{}";
            if(putFailed) {
                postJson = String.format("{\n" +
                        "  \"variables\": {\n" +
                        "    \"outcome\": {\"value\":\"%s\",\"type\":\"String\"}\n" +
                        "  }\n" +
                        "}", outcome);
            }
            HttpEntity<String> entity2 = new HttpEntity<String>(postJson, headers);
            try {
                System.out.println("RecommendationUtils.updateAndCompleteTask:: Complete Task with payload:"+postJson);
                restTemplate.postForLocation(k8TaskBaseUrl + "/complete", entity2);
            }catch (Exception e){
                System.out.println("Caught Exception in POST with svc host and body = " + e.getMessage());
                e.printStackTrace();
            }
            //Complete the task
        }catch (Exception e){
            System.out.println("RecommendationUtils.updateAndCompleteTask:: Caught Exception::"+e.getMessage());
            e.printStackTrace();
        }

    }
}
