package it.algos.wiki24.backend.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import jakarta.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;

@Service
@Slf4j
public class JvmMonitor2 {


//    @Autowired
    private ObjectWriter objectWriter;
//    @Autowired
//ApplicationContext appContext;
    private Logger monitorLog;

    private long lastGcCount;

    private long lastGcTime;    // millis


    @PostConstruct
    private void init() {
//        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
         objectWriter = mapper.writer(new DefaultPrettyPrinter());
         monitorLog = LoggerFactory.getLogger("monitoring");
//        monitorLog.info(LogUtil.monLog("JVM START", "**** JVM monitoring session started ****"));
        log.info("JVM START, **** JVM monitoring session started ****");
        monitorGC();
        monitorMemory();
    }


    /**
     * Monitor the Garbage Collector activity
     */
//    @Scheduled(cron = "0 0 * * * ?")    // every hour at minute 0
    public void monitorGC() {
        String type = "GC STAT";
        GcStat stat = takeGcStat();
        String jsonStat = "";
        try {
            jsonStat = objectWriter.writeValueAsString(stat);
        } catch (JsonProcessingException e) {
//            monitorLog.error(LogUtil.monLog(type, e.getMessage()));
            log.info(e.getMessage());
        }
        log.info(jsonStat);
    }

    /**
     * Monitor the memory status
     */
//    @Scheduled(cron = "0 1 * * * ?")    // every hour at minute 1
    public void monitorMemory() {
        String type = "MEM STAT";
        MemoryStat stat = takeMemoryStat();
        String jsonStat = "";
        try {
            jsonStat = objectWriter.writeValueAsString(stat);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        log.info(jsonStat);
    }

    private GcStat takeGcStat(){
        long totGcCount = 0;
        long totGcTime = 0;

        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {

            long count = gc.getCollectionCount();
            if (count >= 0) {
                totGcCount += count;
            }

            long timeMillis = gc.getCollectionTime();
            if (timeMillis >= 0) {
                totGcTime += timeMillis;
            }
        }

        long currGcCount = totGcCount - lastGcCount;
        long currGcTime = totGcTime - lastGcTime;

        float currGcTimeSec = (float) currGcTime / 1000;
        float totGcTimeSec = (float) totGcTime / 1000;

        float currCgAvgTime = 0;
        if (currGcCount > 0) {
            currCgAvgTime = currGcTimeSec / currGcCount;
        }
        float totCgAvgTime = 0;
        if (totGcCount > 0) {
            totCgAvgTime = totGcTimeSec / totGcCount;
        }

        DecimalFormat df = new DecimalFormat("#.####");
        String sCurrGcTimeSec = df.format(currGcTimeSec)+" s";
        String sCurrCgAvgTime = df.format(currCgAvgTime)+" s";
        String sTotGcTimeSec = df.format(totGcTimeSec)+" s";
        String sTotCgAvgTime = df.format(totCgAvgTime)+" s";

        lastGcCount = totGcCount;
        lastGcTime = totGcTime;

        return new GcStat(currGcCount, sCurrGcTimeSec, sCurrCgAvgTime, totGcCount, sTotGcTimeSec, sTotCgAvgTime);

    }


    public MemoryStat takeMemoryStat(){
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap=memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeap=memoryBean.getNonHeapMemoryUsage();

        String hrHeapMax=humanByte(heap.getMax());
        String hrHeapUsed=humanByte(heap.getUsed());
        String hrHeapCommitted=humanByte(heap.getCommitted());
        String hrNonHeapUsed=humanByte(nonHeap.getUsed());
        String hrNonHeapCommitted=humanByte(nonHeap.getCommitted());

        return new MemoryStat(hrHeapMax, hrHeapUsed, hrHeapCommitted, hrNonHeapUsed, hrNonHeapCommitted);
    }


    @AllArgsConstructor
    @Data
    class GcStat {
        long gcCount;
        String gcTime;
        String gcAvgTime;
        long gcTotCount;
        String gcTotTime;
        String gcTotAvgTime;
    }

    @AllArgsConstructor
    @Data
    class MemoryStat {
        String heapMax;
        String heapUsed;
        String heapCommitted;
        String nonHeapUsed;
        String nonHeapCommitted;
    }


    /**
     * Human readable byte count
     */
    public static String humanByte(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %cB", value / 1024.0, ci.current());
    }



}
