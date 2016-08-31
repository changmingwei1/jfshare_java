package com.jfshare.card.util;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 生成一个整型无重随机序列，无参构造使用数组序列生成，有参构造使用完全剩余系定理（效率高，包含生成质数序列）
 *
 */
public class UniqueRandom {
	int maxValue;
	ArrayList<Integer> prime_num = new ArrayList<Integer>();//质数序列用于查找\判断质数用
	Random rd = new Random();
	boolean isPrimeModel = false;
	UniqueRandom(){}
	UniqueRandom(int maxValue){
		this.maxValue=maxValue+1;
		createPrimeList();
		isPrimeModel = true;
//		for(Integer pm:prime_num)System.out.print(pm+",");
	}
	private int sqrt(int value){
		return (int)Math.ceil(Math.sqrt(new Integer(value).doubleValue()));
	}

	private void createPrimeList() {
		prime_num.add(2);
		prime_num.add(3);
		if(maxValue<5)return;
		for(int i=5;i<maxValue;i=i+2){
			int sqrt = sqrt(i);
			boolean isPrime = true;
			for(Integer pm:prime_num){
				if(!isPrime||pm>sqrt)break;
				isPrime = i%pm!=0;
			}
			if(isPrime)prime_num.add(i);
		}
	}

	private int primeNumWith(int size){
		while(true){
			int pn = prime_num.get(rd.nextInt(prime_num.size()));//随机点2
			if(pn<size && size%pn==0)continue;
			return pn;
		}
	}
	
	int[] randomList(int count,int size){
		int[] result = new int[count];
		if(isPrimeModel){
			if(size>=maxValue){
				System.out.println("size Out of range! The maxValue is:"+maxValue);
				return null;
			}
			int rdn_prm_num_for_size = primeNumWith(size);
			int initpos = (size==count)?0:rd.nextInt(size-count);//随机点1
			for(int i=0;i<count;i++)
				result[i]=(int)((initpos+i)*(long)rdn_prm_num_for_size%size);
		}else{
			return getRandomSequence2(size,count);
		}
		return result;
	}
	
	private int[] getRandomSequence2(int maxNum, int arrSize){
        int[] sequence = new int[maxNum];
        for (int i = 0; i < maxNum; i++)
            sequence[i] = i;
        int[] output = new int[arrSize];
        int end = maxNum-1;
        for (int i = 0; i < arrSize; i++){
            int num = rd.nextInt(end + 1);
            output[i] = sequence[num];
            sequence[num] = sequence[end];
            end--;
        }
        return output;
     }


	/**
	 * 生成卡密的后四位
	 * @return
     */
	public static Set<String> genCardNumLast4(int needCount){
		int MAX =  9000;
		int SIZE = 9000;// 生成数据的数值最大是多少
		UniqueRandom ur = new UniqueRandom(MAX);
		long start = System.nanoTime();
		Set<String> retSet =new HashSet<String>();
		int[] result = ur.randomList(needCount, SIZE);
//		System.out.println("result = "+result.length);
//		System.out.println(System.nanoTime()-start);
		for(int i:result){
//			System.out.println(String.format("%04d", i) );
			String item=String.format("%04d", i);
			retSet.add(item);
		}

		return retSet;
	}


}