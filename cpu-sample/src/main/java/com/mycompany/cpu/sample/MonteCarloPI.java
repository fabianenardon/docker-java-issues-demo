package com.mycompany.cpu.sample;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author babadopulos
 */
public class MonteCarloPI {

    private final int number_of_threads;
    private final ExecutorService executor;
    private final BigDecimal pointsPerThread;

    public MonteCarloPI(int number_of_threads, BigDecimal pointsPerThread) {
        this.number_of_threads = number_of_threads;
        this.pointsPerThread = pointsPerThread;
        this.executor = Executors.newFixedThreadPool(number_of_threads);

    }

    public BigDecimal calculate() {
        ArrayList<Future<BigDecimal>> threads = new ArrayList<>();

        for (int i = 0; i < this.number_of_threads; i++) {
            Future<BigDecimal> future = executor.submit(() -> {
                return new Calculate(pointsPerThread).calculate();
            });
            threads.add(future);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MonteCarloPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        BigDecimal totalCount = BigDecimal.ZERO;

        for (Future<BigDecimal> thread : threads) {
            try {
                totalCount = totalCount.add(thread.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(MonteCarloPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        BigDecimal result = totalCount.multiply(BigDecimal.valueOf(4l)).divide(pointsPerThread.multiply(BigDecimal.valueOf(number_of_threads)));

        return result;
    }

    private class Calculate extends Thread {

        private BigDecimal points;

        public Calculate(BigDecimal points) {
            this.points = points;
        }

        public BigDecimal calculate() {

            ThreadLocalRandom rnd = java.util.concurrent.ThreadLocalRandom.current();

            BigDecimal count = BigDecimal.ZERO;

            while (points.compareTo(BigDecimal.ZERO) > 0) {
                double x = rnd.nextDouble();
                double y = rnd.nextDouble();

                if (((Math.pow(x, 2) + Math.pow(y, 2)) <= 1)) {
                    count = count.add(BigDecimal.ONE);
                }
                points = points.add(BigDecimal.valueOf(-1l));
            }

            return count;
        }

    }

}
