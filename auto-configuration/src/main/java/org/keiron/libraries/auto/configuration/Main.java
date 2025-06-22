package org.keiron.libraries.auto.configuration;

public class Main {

  public static void main(String[] args) {

    System.out.print("Hello and welcome!");
  }

  public static <T> T load(String env, Class<T> configClass) {
    var baseDir = System.getProperty("user.dir");
  }

}