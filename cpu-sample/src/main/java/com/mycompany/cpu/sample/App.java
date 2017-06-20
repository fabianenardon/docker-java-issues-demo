package com.mycompany.cpu.sample;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author babadopulos
 */
public class App {

    private final static BigDecimal POINTS = new BigDecimal("200000000");

    public static void main(String[] args) throws NoSuchAlgorithmException {
        int number_of_executions = 1; //from LOOP env variable
        if (args.length > 0) {
            try {
                number_of_executions = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                number_of_executions = 1;
            }
        }

        int cores = Runtime.getRuntime().availableProcessors();

        Logger.getGlobal().log(Level.INFO, "cores {0}", cores);
        BigDecimal points_per_thread = POINTS.divide(BigDecimal.valueOf(cores));

        System.out.println("points: " + POINTS);
        System.out.println("points_per_thread: " + points_per_thread);
        System.out.println("number_of_executions: " + number_of_executions);

        for (int i = 0; i < number_of_executions; i++) {
            Date now = new Date();
            
            //high CPU usage simulation
            MonteCarloPI monteCarloPI = new MonteCarloPI(cores, points_per_thread);

            BigDecimal result = monteCarloPI.calculate();

            BigDecimal pi = BigDecimal.valueOf(Math.PI);

            double error = 100 * Math.abs(result.doubleValue() - Math.PI) / Math.PI;

            System.out.printf("pi = %s\terror = %.2g%% \tin: %d ms\n", result.toPlainString(), error, (System.currentTimeMillis() - now.getTime()));
        }
    }

}
