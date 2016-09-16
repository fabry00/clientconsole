package com.console.service.backend;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.Metric;
import com.console.domain.NodeData;
import com.console.service.appservice.ApplicationService;
import com.console.util.MessageUtil;
import javafx.application.Platform;
import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author fabry
 */
public class ThreadBackendService implements IBackendService {

    private static final int INITIAL_SLEEP = 1; //seconds
    private static final int INITIAL_SLEEP_MAX_RAND = 5; //seconds
    private static final int SCHEDULE_EVERY = 5; //seconds

    private static final int SHUTDOWN_TIMEOUT = 3; //seconds

    private final Logger logger = Logger.getLogger(ThreadBackendService.class);

    private final ApplicationService appService;

    private ScheduledExecutorService executor = null;

    public ThreadBackendService(ApplicationService appService) {
        this.appService = appService;
    }

    @Override
    public void start() throws BackEndServiceException {
        logger.debug("Starting Thread backendService");
        List<Simulator> simulators = new ArrayList<>();
        try {
            simulators.add(new Simulator(appService, "Homer", "172.168.1.25", 0.9));
            Thread.sleep(10);
            simulators.add(new Simulator(appService, "Marge", "10.22.2.25", 1.1));
            Thread.sleep(10);
            simulators.add(new Simulator(appService, "Bart", "192.168.10.25", 1.0));
            Thread.sleep(10);
            simulators.add(new Simulator(appService, "Lisa", "82.58.14.12", 0.7));
            Thread.sleep(10);
            simulators.add(new Simulator(appService, "Meggie", "172.192.10.25", 1.8));
        } catch (InterruptedException ex) {
            logger.error(ex);
        }

        executor = Executors.newScheduledThreadPool(simulators.size());
        simulators.parallelStream().forEach((sim) -> {
            Random rand = new Random();
            int initial = INITIAL_SLEEP + ((INITIAL_SLEEP_MAX_RAND > 0) ? rand.nextInt(INITIAL_SLEEP_MAX_RAND) : 0);
            executor.scheduleAtFixedRate(sim, initial,
                    SCHEDULE_EVERY, TimeUnit.SECONDS);
        });

        MessageUtil util = new MessageUtil();
        String msg = util.getMsg("Started successfully");
        Platform.runLater(() -> appService.dispatch(new Action<>(ActionType.NEW_MESSAGE, msg)));

    }

    @Override
    public void stop() {
        logger.debug("Shutting down backend  thread");
        if (executor == null) {
            return;
        }
        executor.shutdownNow();
        try {
            logger.debug("Wait for tasks termination");
            executor.awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
    }

    private static class Simulator implements Runnable {

        private final Logger logger = Logger.getLogger(Simulator.class);

        private final ApplicationService appService;
        private final String node;
        private final String ip;
        private final Double factor;
        private final Sigar sigar = new Sigar();
        private boolean failureDetected = false;
        private final int failureLimit = 93;

        private Integer index = 0;

        private Simulator(ApplicationService appService,
                String node, String ip, Double factor) {
            this.appService = appService;
            this.node = node;
            this.factor = factor;
            this.ip = ip;
        }

        @Override
        public void run() {

            Double cpu = getCpuUsage();
            Long ram = getUsedMemory();
            Double cpu2 = Math.abs(cpu * factor); //Just to have different values among all the simulators
            if (cpu2 > 100) {
                cpu2 = 100.0;
            }

            Long ram2 = Math.abs(Double.valueOf(ram * factor).longValue());

            logger.debug("Sim:" + node + " CPU VALUE: " + cpu + " factor: "
                    + factor + " tot cpu: " + cpu2 + " ram: " + ram);

            NodeData.Builder builder = new NodeData.Builder(node)
                    .withInfo(new NodeData.NodeInfo(NodeData.NodeInfo.Type.IP, ip))
                    .withMetricValue(Metric.CPU, index, cpu2)
                    .withMetricValue(Metric.MEMORY, index++, ram2);

            if (!failureDetected) {
                Random random = new Random();
                int rand = random.nextInt(100);
                if (rand > 70 && rand < failureLimit) {
                    builder.isInAbnormalState();
                } else if (rand > failureLimit) {
                    failureDetected = true;
                    builder.isFailureDetected();
                }
            } else {
                builder.isFailureDetected();
            }

            NodeData dataReceived = builder.build();
            this.appService.dispatch(new Action<>(ActionType.DATA_RECEIVED, dataReceived));

        }

        private Double getCpuUsage() {

            try {

                CpuPerc pCpu = sigar.getCpuPerc();

                return pCpu.getCombined() * 100;
            } catch (SigarException ex) {
                logger.error(ex);
            }

            return null;
        }

        private Long getUsedMemory() {
            try {
                Mem mem = sigar.getMem();

                return mem.getActualUsed() / 1024 / 1024;
            } catch (SigarException ex) {
                logger.error(ex);
            }
            return null;
        }

    }

}
