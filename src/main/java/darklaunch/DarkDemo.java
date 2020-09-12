package darklaunch;

import darklaunch.IDarkFeature;
import darklaunch.DarkLaunch;

import java.util.*;
public class DarkDemo{
    public static void main(String[] args){
        System.out.println("Hi");
        DarkLaunch darkLaunch = new DarkLaunch();
        darkLaunch.addProgrammedDarkFeature("user_promotion", new UserPromotionDarkRule());
         IDarkFeature darkFeature = darkLaunch.getDarkFeature("user_promotion");
          System.out.println(darkFeature.enabled());
         System.out.println(darkFeature.dark(1100));

    }
}