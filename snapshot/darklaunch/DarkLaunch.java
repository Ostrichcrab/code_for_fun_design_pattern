import java.util.*;
public class DarkLaunch{
    private static final Loagger log = LoggerFactory.getLogger(DarkLaunch.class);
    private static final int DEFAULT_RULE_UPDATE_TIME_INTERVAL = 60;
    private DarkRule rule;
    private ScheduleExecutorService executor;

    public DarkLaunch(int ruleUpdateTimeInterval){
        loadRule();
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                loadRule();
            }
        }, ruleUpdateTimeInterval, ruleUpdateTimeInterval, TimeUnit.SECONDS);
    }

    public DarkLaunch() {
        this(DEFAULT_RULE_UPDATE_TIME_INTERVAL);
    }

    private void loadRule() {
        InputStream in = null;
        DarkRuleConfig ruleConfig = null;
        try { 
            in = this.getClass().getResourceAsStream("/dark-rule.yaml"); 
            if (in != null) {
                 Yaml yaml = new Yaml(); ruleConfig = yaml.loadAs(in, DarkRuleConfig.class); 
                } 
            } finally { 
                if (in != null) { 
                    try { 
                        in.close();
                 } catch (IOException e) { log.error("close file error:{}", e); } }
    }
}