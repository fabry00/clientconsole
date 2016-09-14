package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.AppState;
import com.console.domain.NodeData;
import com.console.domain.NodesData;
import com.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class ProcessDataAction implements IActionHandler {

    private Logger logger = Logger.getLogger(ProcessDataAction.class);

    @Override
    public void execute(AppState currentState,
            Action action, ApplicationService appService) {

        NodeData data = (NodeData) action.value;
        currentState.setState(State.NEWDATARECEIVED);
        currentState.addNodeData(data);
    }

}
