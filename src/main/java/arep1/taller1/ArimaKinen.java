package arep1.taller1;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
//libreria agregada para el manejo de objetos json
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArimaKinen {

    private static final Map<String, String> MIME_TYPES = new HashMap<>();
    static {
        MIME_TYPES.put("html", "text/html");
        MIME_TYPES.put("css", "text/css");
        MIME_TYPES.put("js", "application/javascript");
        MIME_TYPES.put("pdf", "application/pdf");
        MIME_TYPES.put("txt", "text/plain");
        MIME_TYPES.put("json", "application/json");
        MIME_TYPES.put("xml", "application/xml");
        MIME_TYPES.put("png", "image/png");
        MIME_TYPES.put("jpg", "image/jpeg");
        MIME_TYPES.put("jpeg", "image/jpeg");
        MIME_TYPES.put("gif", "image/gif");
        MIME_TYPES.put("mp4", "video/mp4");
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        boolean running = true;
        ArrayList<Horse> racers = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5000");
            System.exit(1);
        }
        while (running) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
                String inputLine;
                URI requestUri = null;
                String path = "/";
                boolean firstLine = true;
                int contentLength = 0;
                /**
                 * se opto por cambiar el printwritter por outputstream
                 * ya que el printwriter devuelve solamente texto, mientras
                 * que outputstream devuelve bytes y por ende, se maneja
                 * informacion mas facil, que en este caso, es mas optimo
                 * para mostrar la imagen.
                 */
                OutputStream out = clientSocket.getOutputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while ((inputLine = in.readLine()) != null) {
                    if (firstLine) {
                        requestUri = new URI(inputLine.split(" ")[1]);
                        path = inputLine.split(" ")[1];
                        System.out.println("Path: " + path);
                        firstLine = false;
                    }
                    if (inputLine.toLowerCase().startsWith("content-length:")) {
                        contentLength = Integer.parseInt(inputLine.split(":")[1].trim());
                        System.out.println("recibido: " + contentLength);
                    }
                    System.out.println("Received: " + inputLine);
                    if (inputLine.trim().isEmpty()) {
                        break;
                    }
                }

                if (path.equals("/api/register")) {
                    char[] body = new char[contentLength];
                    in.read(body, 0, contentLength);
                    String json = new String(body);
                    Horse horse = mapHorse(json);
                    racers.add(horse);
                    String registrationMessage = "{\"result\": \"" + horse.getHorse()
                            + " ha sido registrado exitosamente\"}";
                    sendResponse(out, 200, "OK", "application/json", registrationMessage.getBytes());

                } else if (path.equals("/api/table")) {
                    String json = horsesToJson(racers);
                    sendResponse(out, 200, "OK", "application/json", json.getBytes());
                } else {

                    Map<String, String> parameters = getParameters(requestUri);
                    String filePath = "src/resources/";
                    String mimeType = null;
                    if (parameters.isEmpty()) {
                        String[] pathParts = requestUri.getPath().split("/");
                        if (pathParts.length > 1 && !pathParts[1].isEmpty()) {
                            filePath += pathParts[1];
                            String[] parts = filePath.split("\\.");
                            if (parts.length > 1) {
                                mimeType = MIME_TYPES.get(parts[parts.length - 1]);
                            }
                        }
                        System.out.println("file request: " + filePath + " | " + mimeType);
                    } else {
                        filePath += getFileName(parameters);
                        mimeType = MIME_TYPES.get(parameters.get("type"));
                        System.out.println("path request: " + filePath + " | " + mimeType);
                    }
                    sendFile(out, filePath, mimeType);
                }
                out.close();
                in.close();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            } catch (Exception e) {
                System.err.println("ERROR: " + e.getMessage());
                System.exit(1);
            } finally {
                clientSocket.close();
            }

        }
        serverSocket.close();
    }

    private static void sendResponse(OutputStream out, int status, String message, String contentType, byte[] content)
            throws IOException {

        String header = "HTTP/1.1 " + status + " " + message + "\r\n" +
                "Content-Type: " + contentType + "; charset=UTF-8\r\n" +
                "\r\n";
        out.write(header.getBytes());
        out.write(content);
        out.flush();
    }

    private static void sendFile(OutputStream out, String path, String contentType) throws IOException {
        try {
            byte[] content = Files.readAllBytes(Path.of(path));
            sendResponse(out, 200, "OK", contentType, content);
        } catch (Exception e) {
            send404(out);
        }
    }

    private static void send404(OutputStream out) throws IOException {
        String htmlContent = "<html><body><h1>404 - NOT FOUND</h1></body></html>";
        byte[] content = htmlContent.getBytes();
        sendResponse(out, 404, "Not Found", "text/html", content);
    }

    private static Map<String, String> getParameters(URI uri) {
        Map<String, String> parameters = new HashMap<>();
        String query = uri.getQuery();
        if (query != null && !query.isEmpty()) {

            String[] parts = query.split("&");
            for (String part : parts) {
                String[] value = part.split("=");
                if (value.length == 2) {
                    parameters.put(value[0], value[1]);
                }

            }

        }

        return parameters;
    }

    private static String getFileName(Map<String, String> parameters) {

        String fileName = parameters.get("name") + "." + parameters.get("type");
        return fileName;
    }

    private static Horse mapHorse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Horse.class);
        } catch (Exception e) {
            System.err.println("Error parseando JSON: " + e.getMessage());
            return null;
        }
    }

    private static String horsesToJson(List<Horse> horses) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(horses);
        } catch (Exception e) {
            System.err.println("Error al convertir los datos: " + e.getMessage());
            return "[]";
        }
    }
}
