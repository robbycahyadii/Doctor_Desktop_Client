import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class DoctorClient extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private int doctorId;
    private Timer chatTimer;
    private JTextArea chatArea;
    private JTextField chatInput;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DoctorClient client = new DoctorClient();
            client.setVisible(true);
        });
    }

    public DoctorClient() {
        setTitle("Doctor Login");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan latar belakang gambar
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("C:\\Users\\ACER\\Documents\\Robby\\PAT\\Activity4_7\\baru\\images\\background-01.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 1;
        gbc.gridy = 1;
        backgroundPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(25);
        backgroundPanel.add(usernameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        backgroundPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(25);
        backgroundPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> loginDoctor());
        backgroundPanel.add(loginButton, gbc);

        add(backgroundPanel);
    }

    private void loginDoctor() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            URL url = new URL("http://localhost:3000/loginDoctor");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Chrome/51.0.2704.103");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("username", username);
            jsonInput.put("password", password);
            String jsonInputString = jsonInput.toString();

            System.out.println("Sending JSON: " + jsonInputString); // Log the JSON being sent

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // Parse response to get doctorId
                    String responseString = response.toString();
                    System.out.println("Response: " + responseString);
                    doctorId = Integer.parseInt(responseString.split("\"id\":")[1].split(",")[0]);
                    showMainPage();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login failed: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMainPage() {
        JFrame mainFrame = new JFrame("Doctor Main Page");
        mainFrame.setSize(1920, 1080);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("C:\\Users\\ACER\\Documents\\Robby\\PAT\\Activity4_7\\baru\\images\\background2-01.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension buttonSize = new Dimension(200, 50);

        JButton profileButton = new JButton("My Profile");
        profileButton.setPreferredSize(buttonSize);
        profileButton.addActionListener(e -> showProfile());
        backgroundPanel.add(profileButton, gbc);

        gbc.gridy = 2;
        JButton chatButton = new JButton("Chat");
        chatButton.setPreferredSize(buttonSize);
        chatButton.addActionListener(e -> showChatInterface());
        backgroundPanel.add(chatButton, gbc);

        gbc.gridy = 3;
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(buttonSize);
        logoutButton.addActionListener(e -> {
            mainFrame.dispose();
            DoctorClient.this.setVisible(true);
        });
        backgroundPanel.add(logoutButton, gbc);

        mainFrame.add(backgroundPanel);
        mainFrame.setVisible(true);
        this.setVisible(false);
    }

    private void sendMedicine() {
        String medicine = JOptionPane.showInputDialog(this, "Enter medicine name:");
        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:");
        int quantity = Integer.parseInt(quantityStr);
        int userId = 1;
        //int userId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter user ID:"));
    
        if (medicine.isEmpty() || quantity <= 0) {
            return;
        }
    
        try {
            URL url = new URL("http://localhost:3000/sendMedicine");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Chrome/51.0.2704.103");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
    
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("userId", userId);
            jsonInput.put("medicine", medicine);
            jsonInput.put("quantity", quantity);
            String jsonInputString = jsonInput.toString();
    
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
    
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(this, "Medicine sent successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send medicine: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            

    private void showProfile() {
        try {
            URL url = new URL("http://localhost:3000/getDoctorProfile/" + doctorId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Chrome/51.0.2704.103");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    StringBuilder formattedResponse = new StringBuilder();
                    formattedResponse.append("<html>");
                    formattedResponse.append("<b>ID:</b> ").append(jsonResponse.getInt("id")).append("<br>");
                    formattedResponse.append("<b>Name:</b> ").append(jsonResponse.getString("name")).append("<br>");
                    formattedResponse.append("<b>Specialization:</b> ").append(jsonResponse.getString("specialization")).append("<br>");
                    formattedResponse.append("<b>Contact Info:</b> ").append(jsonResponse.getString("contact_info")).append("<br>");
                    formattedResponse.append("<b>Balance:</b> ").append(jsonResponse.getString("saldo")).append("<br>");
                    formattedResponse.append("<b>Experience (years):</b> ");
                    try {
                        formattedResponse.append(jsonResponse.getInt("pengalaman(thn)"));
                    } catch (Exception e) {
                        formattedResponse.append("Not available");
                    }
                    formattedResponse.append("<br>");
                    formattedResponse.append("<b>Username:</b> ").append(jsonResponse.getString("username")).append("<br>");
                    formattedResponse.append("<b>Status:</b> ").append(jsonResponse.getInt("status") == 1 ? "Active" : "Inactive").append("<br>");
                    formattedResponse.append("</html>");
                    JOptionPane.showMessageDialog(this, formattedResponse.toString(), "Doctor Profile", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch profile: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showChatInterface() {
        JFrame chatFrame = new JFrame("Chat Interface");
        chatFrame.setSize(600, 600);
        chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chatFrame.setLocationRelativeTo(null);
    
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
    
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chatScrollPane, BorderLayout.CENTER);
    
        JPanel inputPanel = new JPanel(new BorderLayout());
        chatInput = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
    
        panel.add(inputPanel, BorderLayout.SOUTH);
    
        // Tambahkan tombol untuk mengirim medicine
        JButton sendMedicineButton = new JButton("Send Medicine");
        sendMedicineButton.addActionListener(e -> sendMedicine());
        panel.add(sendMedicineButton, BorderLayout.NORTH);
    
        chatFrame.add(panel);
        chatFrame.setVisible(true);
    
        // Start the chat update timer
        chatTimer = new Timer();
        chatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                fetchMessages();
            }
        }, 0, 1000);
    }    

    private void sendMessage() {
        String message = chatInput.getText();
        if (message.isEmpty()) {
            return;
        }

        try {
            URL url = new URL("http://localhost:3000/sendMessage");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Chrome/51.0.2704.103");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("user_id", 1);  // Assume user_id is 1 for simplicity
            jsonInput.put("doctor_id", doctorId);
            jsonInput.put("message", message);
            jsonInput.put("sender", "doctor");
            String jsonInputString = jsonInput.toString();

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                chatInput.setText(""); // Clear the input field
                fetchMessages(); // Refresh messages after sending
            }
            
            else {
                chatInput.setText("");
                //JOptionPane.showMessageDialog(this, "Failed to send message: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchMessages() {
        try {
            URL url = new URL("http://localhost:3000/getMessages/" + doctorId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Chrome/51.0.2704.103");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    JSONArray jsonResponse = new JSONArray(response.toString());
                    chatArea.setText(""); // Clear the chat area
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject message = jsonResponse.getJSONObject(i);
                        chatArea.append(message.getString("sender") + ": " + message.getString("message") + "\n");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch messages: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


