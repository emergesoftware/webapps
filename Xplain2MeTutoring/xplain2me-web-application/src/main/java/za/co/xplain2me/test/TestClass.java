package za.co.xplain2me.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import za.co.xplain2me.dao.ProfileDAO;
import za.co.xplain2me.dao.ProfileDAOImpl;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.model.SearchUserProfileType;


public class TestClass {
    
    public static void main(String... args) throws Exception {
        
        File originalFile = new File("C:\\Users\\user\\Downloads\\block_russian_ip_addresses.txt");
        BufferedReader reader = new BufferedReader(new FileReader(originalFile));
        
        File outputFile = new File("C:\\Users\\user\\Downloads\\block_russian_ip_addresses_output.txt");
        if (outputFile.exists())
            outputFile.delete();
        
        outputFile.createNewFile();
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        StringBuilder contents = new StringBuilder();
        
        while (reader.ready()) {
            contents.append(reader.readLine());
        }
        
        reader.close();
        
        String changedContents = contents.toString()
                .replaceAll("iptables", "\r\niptables");
        writer.write(changedContents + 
                "\r\niptables -L -n" + 
                "\r\nservice iptables restart");
        writer.close();
        
    }
    
}