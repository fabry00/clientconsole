package com.console.service.backend;

import com.console.domain.ActionType;
import com.console.domain.AppState;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
@Named("CommandLine")
public class CommandLineAppService implements IBackendService {

    private final Logger logger = Logger.getLogger(CommandLineAppService.class);

    public CommandLineAppService(){
    }
    
    @PostConstruct
    public void init() {
        logger.debug("initialized");
    }

    @Override
    public void start() {
    }
    
}
