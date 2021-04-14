package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.postRequestHandler;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.JournalTexterDB;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.encryption.Encryptor;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.security.auth.login.FailedLoginException;
import java.util.Map;

/**
 * Handles requests from the frontend to the user login server.  Takes in a request
 * with a username and a password and checks to see if a user exists.  If they do, it returns
 * the token, if not it returns an error.
 */
public class SignUpHandler implements Route {
  private static final Gson GSON = new Gson();

  /**
   * Handles post requests from the server from the signup page.
   *
   * @param request The request to the server.
   * @param response The response from the server.
   * @return The body of the response.
   * @throws Exception If there is a server error.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    JSONObject data = new JSONObject(request.body());
    String username = data.getString("username");
    String password = data.getString("password");

    byte[] encryptedPassword = Encryptor.encryptMessage(password);
    JournalTexterDB database = JournalTexterDB.getInstance();

    try {
      database.registerUser(username, encryptedPassword);

      Map<String, Object> variables = ImmutableMap.of("token", username);
      return GSON.toJson(variables);
    } catch (FailedLoginException flEx) {
      response.status(401);
      return flEx.getMessage();
    } catch (Exception e) {
      e.printStackTrace();
      response.status(500);
      return e.getMessage();
    }
  }
}