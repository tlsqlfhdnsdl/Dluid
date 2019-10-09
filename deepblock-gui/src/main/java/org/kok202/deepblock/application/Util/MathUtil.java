package org.kok202.deepblock.application.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MathUtil {
    public static int[] getRecommendedDivisors(int size){
        if(size < 0)
            throw new RuntimeException("Size must be over than 0");
        if(size == 1)
            return new int[]{1, 1};

        List<Integer> divisors = new ArrayList<>();
        int sqrtSize = (int) Math.sqrt(size);
        for(int i = 1; i < sqrtSize; i++){
            if(size % i == 0){
                divisors.add(i);
                divisors.add(size/i);
            }
        }

        Collections.sort(divisors);
        int middle = divisors.size() / 2;
        if (divisors.size() % 2 == 0)
            return new int[]{divisors.get(middle-1), divisors.get(middle)};
        else
            return new int[]{divisors.get(middle-1), divisors.get(middle-1)};
    }
}
