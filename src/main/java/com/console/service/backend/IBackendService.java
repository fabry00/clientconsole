package com.console.service.backend;

import com.console.service.IService;

/**
 *
 * @author fabry
 */
public interface IBackendService extends IService{
    
    public void start() throws BackEndServiceException;
}
