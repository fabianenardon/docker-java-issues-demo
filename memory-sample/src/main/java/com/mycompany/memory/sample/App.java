package com.mycompany.memory.sample;

import java.util.HashMap;
import java.util.Map;

/**
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
                long prevUsed = (prevTotal - prevFree);
                System.out.println(
                        "#" + i
                        + ", Total: " + total
                        + ", Used: " + used
                     //   + ", ∆Used: " + (used - prevUsed)
                        + ", Free: " + free);
                     //   + ", ∆Free: " + (free - prevFree));
                prevTotal = total;
                prevFree = free;
            }
            map.put(i, "abcdefghijklmnopqrstuvwz");
        }
    }

}
