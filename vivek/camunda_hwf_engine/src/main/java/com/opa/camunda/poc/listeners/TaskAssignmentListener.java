package com.opa.camunda.poc.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opa.camunda.poc.model.NotificationPayload;
import com.opa.camunda.poc.model.User;
import com.opa.camunda.poc.service.Producer;
import com.opa.camunda.poc.util.HwfUtil;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class TaskAssignmentListener implements TaskListener {
    @Autowired
    Producer producer1;

    @Autowired
    private KafkaTemplate<String, String> kakfkaTemplate;

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> processVariables = delegateTask.getVariables();
        for(String key : processVariables.keySet()){
            System.out.println(">>>>>>>>>>>key = " + key + ":: Value ="+((String)processVariables.get(key)));
        }
        String taskId = delegateTask.getId();
        String taskUrl = "http://localhost:8080/engine-rest/task/" + taskId;
        processVariables.put("taskUrl", taskUrl);

        //Form the Java Model using the processVariable and push the message to Kafka
        NotificationPayload payload = new NotificationPayload();
        payload.setTaskUrl(taskUrl);
        payload.setProcessDefinitionName(delegateTask.getProcessDefinitionId());
        payload.setInvoiceAmount((String) processVariables.get("invoiceAmount"));
        payload.setInvoiceTitle((String) processVariables.get("invoiceTitle"));
        payload.setInvoiceDate((String) processVariables.get("invoiceDate"));
        payload.setCurrency((String) processVariables.get("currency"));
        payload.setInvoiceDescription((String) processVariables.get("invoiceDescription"));
        User creator = new User();
        creator.setName((String) processVariables.get("creatorId"));
        creator.setEmail("vivek.ka.verma@oracle.com");
        User assignee = new User();
        assignee.setName(HwfUtil.getManager(creator.getName()));
        assignee.setEmail("vivek.ka.verma@oracle.com");
        payload.setAssignee(assignee);
        payload.setInvoiceCreator(creator);
        processVariables.put("assigneeId", assignee.getName());
        //New additions for weka processing
        payload.setCreatorLevel("Employee");
        payload.setAssigneeLevel(HwfUtil.getUserLevel(payload.getAssignee().getName()));
        payload.setSubType((String) processVariables.get("subType"));
        payload.setProcessName("Expense");
        payload.setOutcome("UNPREDICTED");
        payload.setProcessInstanceId(delegateTask.getProcessInstanceId());
        payload.setHasAttachment((String) processVariables.get("hasAttachment"));
        String recieptValid = "TRUE";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date recieptDate = sdf.parse(payload.getInvoiceDate());
            Date today = new Date();
            long difference_In_Time = today.getTime() - recieptDate.getTime();
            long difference_In_Days = (difference_In_Time/ (1000 * 60 * 60 * 24)) % 365;
            System.out.println(difference_In_Days);
            recieptValid = difference_In_Days > 180? "FALSE":"TRUE";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        payload.setIsValidReceipt(recieptValid);
        payload.toString();
        processVariables.put("outcome", payload.getOutcome());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(payload);
        } catch (Exception e) {
            System.out.println(">>>>>>>UserTaskAIListener.notify Caught exception while converting object to json::" + e.getMessage());
        }
        System.out.println(">>>>>>>UserTaskAIListener.notify Sending JSON String: " + jsonString);
        producer1.publishToTopic(jsonString);
        //Send Message to Kafka
    }
}
