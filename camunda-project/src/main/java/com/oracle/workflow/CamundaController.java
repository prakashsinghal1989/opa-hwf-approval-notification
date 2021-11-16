package com.oracle.workflow;

import com.oracle.workflow.model.SamplePayload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamundaController {

    @RequestMapping(value = "/postconnector", method = RequestMethod.POST)
    public SamplePayload msgs2(@RequestBody SamplePayload samplePayload) {
        return samplePayload;
    }
}
