package com.opa.camunda.poc.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opa.camunda.poc.model.AdditionalInvoiceDetails;
import com.opa.camunda.poc.model.NotificationPayload;
import com.opa.camunda.poc.model.User;
import com.opa.camunda.poc.service.Producer;
import com.opa.camunda.poc.util.HwfUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserTaskAIListener implements ExecutionListener {

    @Autowired
    Producer producer1;

    @Autowired
    private KafkaTemplate<String, String> kakfkaTemplate;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        Map<String, Object> processVaribles = delegateExecution.getVariables();
        Task task = delegateExecution.getProcessEngineServices().getTaskService().createTaskQuery()
                .processInstanceId(delegateExecution.getProcessInstanceId()).executionId(delegateExecution.getId()).singleResult();
        // just make sure that task really exists
        String taskUrl = "www.google.com";
        if(task != null) {
            taskUrl = "http://localhost:8080/engine-rest/task/"+task.getId();
        }
        //Form the Java Model using the processVariable and push the message to Kafka
        NotificationPayload payload = new NotificationPayload();
        payload.setTaskUrl(taskUrl);
        payload.setProcessDefinitionName(delegateExecution.getProcessDefinitionId());
        payload.setInvoiceAmount((String)processVaribles.get("invoiceAmount"));
        payload.setInvoiceTitle((String)processVaribles.get("invoiceTitle"));
        payload.setInvoiceDate((String)processVaribles.get("invoiceDate"));
        payload.setCurrency((String)processVaribles.get("currency"));
        payload.setInvoiceDescription((String)processVaribles.get("invoiceDescription"));
        User creator = new User();
        creator.setName((String)processVaribles.get("creatorId"));
        creator.setEmail((String)processVaribles.get("creatorEmail"));
        User assignee = new User();
        assignee.setName((String)processVaribles.get("assigneeId"));
        assignee.setEmail((String)processVaribles.get("assigneeEmail"));
        payload.setAssignee(assignee);
        payload.setInvoiceCreator(creator);

        //New additions for weka processing
        payload.setCreatorLevel(HwfUtil.getUserLevel(payload.getInvoiceCreator().getName()));
        payload.setAssigneeLevel(HwfUtil.getUserLevel(payload.getAssignee().getName()));
        payload.setSubType((String)processVaribles.get("subType"));
        payload.setProcessName("Expense");
        payload.setOutcome("UNPREDICTED");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(payload);
        } catch (Exception e){
            System.out.println(">>>>>>>UserTaskAIListener.notify Caught exception while converting object to json::"+e.getMessage());
        }
        System.out.println(">>>>>>>UserTaskAIListener.notify Sending JSON String: "+jsonString);
        producer1.publishToTopic(jsonString);
        //Send Message to Kafka
    }

}
