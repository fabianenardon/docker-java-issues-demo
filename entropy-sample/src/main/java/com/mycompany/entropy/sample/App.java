package com.mycompany.entropy.sample;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author babadopulos
 */
public class App {

    private final static int BYTES = 2;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Date now = new Date();

        /*
        use a strong random implementations, defined by:
        $JAVA_HOME/jre/lib/security/java.security :
        securerandom.strongAlgorithms=
         */
        SecureRandom sr = SecureRandom.getInstanceStrong();

        Logger.getGlobal().log(Level.INFO, "\nRequesting {0} random bytes", BYTES);

        byte[] b = new byte[BYTES];

        /*
        request N random bytes.
        depending on the SecureRandom implementation, this can potentialy be a blocking request.
        this method only returns when the system has enough entropy.
         */
        sr.nextBytes(b);

        System.out.println("\n" + Arrays.toString(b) + "\n");

        Logger.getGlobal().log(Level.INFO, "Took {0} ms\n", (System.currentTimeMillis() - now.getTime()));
    }

}
