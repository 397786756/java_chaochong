package com.teeqee.utils;

import java.util.*;
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


 public static void main(String[] args) {
  List<Long> bandX = getBandX(6L, 6);
  System.out.println(bandX);
 }
 /**
  * @param rank 根据下标排名获取区间值
  * @param playerNum 获取的玩家人数
  * @return 返回被拉取到的玩家的集合
  */
 public static List<Long> getBandX(Long rank,int playerNum){
  if (rank==null||rank<6){
   rank=6L;
   ;      }
  if (playerNum!=6){
   playerNum=6;
  }
  List<Long> list = new ArrayList<>(playerNum);
  if (rank<=6){
   for (long l = 7L; l > 0; l--) {
    list.add(l);
   }
  }else {
   long B = getRankRandom(rank);
   Long Y = null;
   for (int i = 0; i < playerNum; i++) {
    if (i==0){
     //挑战1的排名 Y1 = X-1
     Y=rank-1;
    }else {
     int randomInt = RandomUtils.getRandomInt(1, (int) B);
     Y-=randomInt;
    }
    list.add(Y);
   }
  }
  return list;
 }
 private static final int RANK250=250;
 private static final int RANK50=50;
 private static final int RANK12=12;
 private static final int RANK6=6;
 /**获取随机的排名*/

 private static long getRankRandom(Long rank){
  long randomRank;
  if (rank>RANK250 ){
   randomRank=50;
  }else if (RANK50<rank){
   randomRank=10;
  }else if (RANK12<rank&&rank<RANK50){
   randomRank=2;
  }else if (RANK6<rank&&rank<=RANK12){
   randomRank=1;
  }else {
   randomRank=6;
  }
  return randomRank;
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