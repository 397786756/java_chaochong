package com.teeqee.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
 //private static Random random;

 //双重校验锁获取一个Random单例
 public static ThreadLocalRandom getRandom() {
  return ThreadLocalRandom.current();
  /*if(random==null){
   synchronized (RandomUtils.class) {
    if(random==null){
     random =new Random();
    }
   }
  }
  
  return random;*/
 }

 /**
  * @param chance  几率
  * @return boolean 返回是否施放
  */
 public static boolean canChance(float chance) {
  return Math.random()>chance;
 }


 /**
  * 获得一个[0,max)之间的随机整数。
  *
  * @param max
  * @return
  */
 public static int getRandomInt(int max) {
  return getRandom().nextInt(max);
 }

 /**
  * 获得一个[min, max]之间的随机整数
  *
  * @param min
  * @param max
  * @return
  */
 public static int getRandomInt(int min, int max) {
   return getRandom().nextInt(max - min + 1) + min;
 }

 /**
  * @param min
  * @param max
  * @return 因为火和暗几率修改为百分之50
  */
 public static int getRandomIntFor(int min, int max){
  int total = getRandomInt(min , max ) ;
  //金木水火土
  int huo = 4;
  int an = 8;
  if (total==huo||total==an){
      total = getRandomInt(min , max ) ;
  }
   return total;
 }

 /**
  * 获得一个[0,max)之间的长整数。
  *
  * @param max
  * @return
  */
 public static long getRandomLong(long max) {
  return getRandom().nextLong(max);
 }

 /**
  * 从数组中随机获取一个元素
  *
  * @param array
  * @return
  */
 public static <E> E getRandomElement(E[] array) {
  return array[getRandomInt(array.length)];
 }

 /**
  * 从list中随机取得一个元素
  *
  * @param list
  * @return
  */
 public static <E> E getRandomElement(List<E> list) {
  return list.get(getRandomInt(list.size()));
 }

 /**
  * 从set中随机取得一个元素
  *
  * @param set
  * @return
  */
 public static <E> E getRandomElement(Set<E> set) {
  int rn = getRandomInt(set.size());
  int i = 0;
  for (E e : set) {
   if (i == rn) {
    return e;
   }
   i++;
  }
  return null;
 }

 /**
  * 从map中随机取得一个key
  *
  * @param map
  * @return
  */
 public static <K, V> K getRandomKeyFromMap(Map<K, V> map) {
  int rn = getRandomInt(map.size());
  int i = 0;
  for (K key : map.keySet()) {
   if (i == rn) {
    return key;
   }
   i++;
  }
  return null;
 }

 /**
  * 从map中随机取得一个value
  *
  * @param map
  * @return
  */
 public static <K, V> V getRandomValueFromMap(Map<K, V> map) {
  int rn = getRandomInt(map.size());
  int i = 0;
  for (V value : map.values()) {
   if (i == rn) {
    return value;
   }
   i++;
  }
  return null;
 }
}