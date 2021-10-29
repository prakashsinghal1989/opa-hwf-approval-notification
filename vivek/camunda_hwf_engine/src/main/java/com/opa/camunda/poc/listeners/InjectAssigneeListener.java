package com.opa.camunda.poc.listeners;

import com.opa.camunda.poc.util.HwfUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InjectAssigneeListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("InjectAssigneeListener.notify --- Start Event Listner invoked!!!!");
        Map<String, Object> processVariables = delegateExecution.getVariables();
        String creator  = (String)processVariables.get("creatorId");
        creator = creator != null?creator:"Atul";
        String assignee1 = HwfUtil.getManager(creator);
        String assignee2 = HwfUtil.getManager(assignee1);
        delegateExecution.setVariable("assignee1", assignee1);
        delegateExecution.setVariable("assignee2", assignee2);
        System.out.println("InjectAssigneeListener.notify::assignees added to process variables!!!!");
    }

}
