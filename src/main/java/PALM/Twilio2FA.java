package PALM;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.entity.Challenge;
import com.twilio.rest.verify.v2.service.entity.Factor;
import com.twilio.rest.verify.v2.service.entity.NewFactor;

import java.util.Scanner;
import java.util.UUID;

public class Twilio2FA {
    public static final String VERIFICATION_SERVICE_SID = System.getenv("VERIFICATION_SERVICE_SID");
    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    /**
     * Creates instance of verification service
     * This is not normally done within the application and can be done in the TWILIO dashboard
     * and then set as an environment variable
     *
     * @return SID of created service
     */
    private static String createVerificationService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Service service = Service.creator("PALM").create();
        return service.getSid();
    }

    /**
     * Creates new TOTP factor based on user's UUID
     *
     * @param uuid         Unique user ID
     * @param friendlyName Normal username
     * @return Response from API
     */
    protected static NewFactor createNewTOTPFactor(String uuid, String friendlyName) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        return NewFactor.creator(VERIFICATION_SERVICE_SID, uuid, friendlyName, NewFactor.FactorTypes.TOTP).create();
    }

    /**
     * Verifies TOTP code based on given parameters (ONLY FOR WHEN CREATING ACCOUNT)
     *
     * @param uuid        Unique user ID
     * @param responseSid SID from response when TOTP factor was created
     * @param code        verification code being verified
     * @return True if successful
     */
    protected static boolean verifyTOTPFactor(String uuid, String responseSid, int code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Factor factor = Factor.updater(VERIFICATION_SERVICE_SID, uuid, responseSid)
                .setAuthPayload(Integer.toString(code)).update();
        return factor.getStatus() == Factor.FactorStatuses.VERIFIED;
    }

    /**
     * Validates existing token
     *
     * @param uuid        Unique user ID
     * @param responseSid SID from response when TOTP factor was created
     * @param code        verification code being verified
     * @return True if valid
     */
    protected static boolean validateToken(String uuid, String responseSid, int code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Challenge challenge = Challenge.creator(VERIFICATION_SERVICE_SID, uuid, responseSid)
                .setAuthPayload(Integer.toString(code)).create();
        return challenge.getStatus() == Challenge.ChallengeStatuses.APPROVED;
    }

    public static void main(String[] args) {
        // Need to save UUID, responseSID, serviceSID
        String user = "testusername";
        String uuid = UUID.randomUUID().toString();
        uuid = "testuser";
        //String serviceSid = createVerificationService();

        //NewFactor response = createNewTOTPFactor(serviceSid, uuid, user);
        //String secret = response.getBinding().get("secret").toString();
        //String responseSid = response.getSid();
        //System.out.println("Secret: " + secret);
        String responseSid = "YF02656f8c8557f3d28e24f769a7938c46";
        System.out.println("Response SID: " + responseSid); // YF02656f8c8557f3d28e24f769a7938c46
        System.out.println("Service SID: " + VERIFICATION_SERVICE_SID); // VA5b855115913c55cde36d93fabd82f62d

        System.out.println(validateToken(uuid, responseSid, new Scanner(System.in).nextInt()));
    }
}
