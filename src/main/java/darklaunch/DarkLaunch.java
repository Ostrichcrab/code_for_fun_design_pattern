package darklaunch;

import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.yaml.snakeyaml.Yaml;


public class DarkLaunch {
    private static final Logger log = LoggerFactory.getLogger(DarkLaunch.class); // why bug ?
    private static final int DEFAULT_RULE_UPDATE_TIME_INTERVAL = 60; // in seconds
    private DarkRule rule = new DarkRule();
    private ScheduledExecutorService executor;

    public DarkLaunch(int ruleUpdateTimeInterval) {
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
      // ���Ҷȹ��������ļ�dark-rule.yaml�е����ݶ�ȡDarkRuleConfig��
      InputStream in = null;
      DarkRuleConfig ruleConfig = null;
      try {
          log.debug("hihi");
        in = this.getClass().getResourceAsStream("/dark-rule.yaml");
        System.out.println("idebug===========");
        System.out.println(in.toString());
          String text = CharStreams.toString(new InputStreamReader(in, "UTF-8"));
          System.out.println(text);
        if (in != null) {
          Yaml yaml = new Yaml(); // why bug ?
          ruleConfig = yaml.loadAs(in, DarkRuleConfig.class);
          DarkRuleConfig me = yaml.loadAs(new FileInputStream(new File("dark-rule.yaml")), DarkRuleConfig.class);
          ruleConfig = me;
        }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (IOException e) {
            log.error("close file error:{}", e);
          }
          catch (Exception e){
              System.out.println(e);
          }
        }
      }

      if (ruleConfig == null) {
        throw new RuntimeException("Can not load dark rule.");
      }
      // ���¹��򲢷�ֱ����this.rule�Ͻ��У�
      // ����ͨ������һ���µ�DarkRule��Ȼ��ֵ��this.rule��
      // ��������¹���͹����ѯ�Ĳ�����ͻ����
        Map <String, IDarkFeature>darkFeatures = new HashMap<>();
        List<DarkRuleConfig.DarkFeatureConfig> darkFeatureConfigs = ruleConfig.getFeatures();
        for (DarkRuleConfig.DarkFeatureConfig darkFeatureConfig : darkFeatureConfigs) {
            darkFeatures.put(darkFeatureConfig.getKey(), new DarkFeature(darkFeatureConfig));
        }
        this.rule.setDarkFeatures(darkFeatures);
    }

    public void addProgrammedDarkFeature(String featureKey, IDarkFeature darkFeature) {
        this.rule.addProgrammedDarkFeature(featureKey, darkFeature);
    }

    public IDarkFeature getDarkFeature(String featureKey) {
        IDarkFeature darkFeature = this.rule.getDarkFeature(featureKey);
      return darkFeature;
    }
  }