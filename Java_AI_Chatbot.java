import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Java_AI_Chatbot {

    // --- Data store -------------------------------------------------
    private final Map<String, String> knowledge = new LinkedHashMap<>();

    // --- GUI components ---------------------------------------------
    private final JFrame frame = new JFrame("Java AI Chatbot");
    private final JTextArea chatArea = new JTextArea();
    private final JTextField inputField = new JTextField();
    private final JButton sendButton = new JButton("Send");
    private final JButton trainButton = new JButton("Teach Me");

    // --- Constructor ------------------------------------------------
    public Java_AI_Chatbot() {
        preloadKnowledge();
        buildGui();
    }

    // --- Pre-trained questions --------------------------------------
    private void preloadKnowledge() {
        knowledge.put("hi", "Hello! How can I help you?");
        knowledge.put("hello", "Hi there!");
        knowledge.put("how are you", "I'm just a program, but I'm doing great!");
        knowledge.put("what is your name", "I'm a Java-based chatbot.");
        knowledge.put("bye", "Goodbye! Have a nice day.");
        knowledge.put("thank you", "You're welcome!");
        knowledge.put("how can i reset my password",
                "Go to Settings → Account → Reset Password and follow the steps.");
    }

    // --- Build GUI --------------------------------------------------
    private void buildGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(chatArea);
        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.add(inputField, BorderLayout.CENTER);

        JPanel right = new JPanel(new GridLayout(2, 1, 5, 5));
        right.add(sendButton);
        right.add(trainButton);
        bottom.add(right, BorderLayout.EAST);

        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        frame.getContentPane().add(bottom, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> handleSend());
        inputField.addActionListener(e -> handleSend());
        trainButton.addActionListener(e -> teachNewQA());

        appendBot("Hello! I'm your AI Chatbot 🤖. Ask me anything or click 'Teach Me' to add a new Q&A.");
        frame.setVisible(true);
    }

    // --- Chat handling ----------------------------------------------
    private void handleSend() {
        String userText = inputField.getText().trim();
        if (userText.isEmpty()) return;

        appendUser(userText);
        inputField.setText("");

        String response = getResponse(userText.toLowerCase());
        appendBot(response);
    }

    private String getResponse(String msg) {
        if (knowledge.containsKey(msg)) return knowledge.get(msg);

        // small fuzzy match
        for (String key : knowledge.keySet()) {
            if (msg.contains(key)) return knowledge.get(key);
        }

        return "I'm not sure about that. Click 'Teach Me' to teach me a new answer!";
    }

    // --- Training dialog --------------------------------------------
    private void teachNewQA() {
        JTextField qField = new JTextField();
        JTextField aField = new JTextField();
        Object[] form = {"Question:", qField, "Answer:", aField};

        int ok = JOptionPane.showConfirmDialog(frame, form, "Teach me something new",
                JOptionPane.OK_CANCEL_OPTION);

        if (ok == JOptionPane.OK_OPTION) {
            String q = qField.getText().trim().toLowerCase();
            String a = aField.getText().trim();
            if (!q.isEmpty() && !a.isEmpty()) {
                knowledge.put(q, a);
                appendBot("Got it! I’ve learned a new response.");
            }
        }
    }

    // --- Helpers ----------------------------------------------------
    private void appendUser(String text) {
        chatArea.append("You: " + text + "\n");
    }

    private void appendBot(String text) {
        chatArea.append("Bot: " + text + "\n\n");
    }

    // --- Main -------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Java_AI_Chatbot::new);
    }
}