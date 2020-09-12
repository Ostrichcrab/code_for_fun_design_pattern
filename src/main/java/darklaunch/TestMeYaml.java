package darklaunch;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestMeYaml {
    public static void main(String[] args) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        DarkRuleConfig me = yaml.loadAs(new FileInputStream(new File("dark-rule.yaml")), DarkRuleConfig.class);
//        Me me = yaml.loadAs(new FileInputStream(new File("me.yaml")), Me.class);
        System.out.println(me);
    }
}