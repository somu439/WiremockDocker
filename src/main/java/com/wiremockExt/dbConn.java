import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.sql.*;

public class PostgresResponseTransformer extends ResponseDefinitionTransformer {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public PostgresResponseTransformer(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, Parameters parameters) {
        String firstName = request.queryParameter("firstName").firstValue();
        String lastName = request.queryParameter("lastName").firstValue();

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT json_data FROM your_table WHERE firstname = ? AND lastname = ?")) {
            
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String jsonData = rs.getString("json_data");
                    return ResponseDefinition.ok()
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responseDefinition;
    }

    @Override
    public String getName() {
        return "postgres-transformer";
    }
}
