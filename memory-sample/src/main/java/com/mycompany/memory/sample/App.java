package com.mycompany.memory.sample;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Just something to use memory and display how much is used.
 * 
 * @author fabiane
 */
public class App {

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        long prevTotal = 0;
        long prevFree = rt.freeMemory();

        Map map = new HashMap();
        for (int i = 0; i < 4_000_000; i++) {
            long total = rt.totalMemory();
            long free = rt.freeMemory();
            if (total != prevTotal || free != prevFree) {
                long used = total - free;
                System.out.println(
                        "#" + i
                        + ", Total: " + total
                        + ", Used: " + used
                        + ", Free: " + free);
                prevTotal = total;
                prevFree = free;
            }
            map.put(i, "some string, just to use memory");
        }
    }

}
