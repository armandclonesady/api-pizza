package fr.univlille.iut.info.r402.help;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpParser {
    private String urlToHelp = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator") + "help-descriptions.json";
    private File helpFile = new File(urlToHelp);
    private String fileText = "";

    private Map<String, JSONObject> allMans = new HashMap<>();
    public HelpParser() {
        initParser();
    }

    public void initParser() {
        try {
            extractFileText();
            parseText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void extractFileText() throws IOException {
        FileReader fr = new FileReader(helpFile);
        while (fr.ready()) {
            fileText += (char) fr.read();
        }
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    public String getFileText() {
        return fileText;
    }

    public void parseText() {
        JSONObject helpJson = new JSONObject(fileText);
        JSONArray arr = helpJson.getJSONArray("commandes");
        for (int i = 0; i < arr.length(); i++) {
            allMans.put(arr.getJSONObject(i).getString("name"), arr.getJSONObject(i));
        }
    }

    public void showCommandMan(JSONObject obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("NAME \n");
        sb.append("\t").append(obj.getString("name")).append(" - ").append(obj.getString("description")).append("\n");
        sb.append("SYNOPSIS \n");
        sb.append("\t").append(obj.getString("synopsis")).append("\n");
        sb.append("OPTIONS \n");
        JSONArray options = obj.getJSONArray("arguments");
        for (int i = 0; i < options.length(); i++) {
            sb.append("\t").append(options.getJSONObject(i).getString("name")).append("\n");
            sb.append("\t\t").append(options.getJSONObject(i).getString("type")).append("\n");
            sb.append("\t\t").append(options.getJSONObject(i).getString("description")).append("\n");
        }


        System.out.println(sb.toString());

    }

    public void showCommandMan(String command) {
        JSONObject obj = allMans.get(command);
        showCommandMan(obj);

    }
}
