package com.console.service;

import com.console.domain.Status;
import javax.annotation.PostConstruct;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class StatusService {
    
    private Status status;
    
    @PostConstruct
    public void init() {
        this.status = Status.UNKWOWN;
    }
    
    public Status getCurrentStatus() {
        return status;
    }
}
