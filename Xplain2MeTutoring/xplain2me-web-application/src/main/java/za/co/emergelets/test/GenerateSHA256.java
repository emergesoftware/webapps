/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.emergelets.test;

import za.co.emergelets.util.SHA256Encryptor;

/**
 *
 * @author user
 */
public class GenerateSHA256 {
    
    public static void main(String... args) throws Exception {
        
        String password = "washington123";
        String salt = "89732621769187643987";
        
        System.out.println("HASH: " + SHA256Encryptor.computeSHA256(password, salt));
        
        
    }
    
}
