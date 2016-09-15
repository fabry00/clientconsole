package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.AppState;
import com.console.domain.NodeData;
import com.console.domain.State;

/**
 *
 * @author fabry
 */
class ProcessDataAction implements IActionHandler {

    //private Logger logger = Logger.getLogger(ProcessDataAction.class);
    @Override
    public void execute(AppState currentState,
            Action action, ApplicationService appService) {

        NodeData node = (NodeData) action.value;
        if (node.AnomalyDetected()) {
            currentState.addAbnormalNode(node);
        } else {
            int index = currentState.getNodesInAbnormalState().lastIndexOf(node);
            if (index >= 0) {
                currentState.getNodesInAbnormalState().remove(index);
            }
        }

        currentState.addNodeData(node);
        if (!currentState.getNodesInAbnormalState().isEmpty()) {
            currentState.setState(State.ABNORMAL_NODE_STATE);
        } else {
            currentState.setState(State.NEWDATARECEIVED);
        }
    }

}
