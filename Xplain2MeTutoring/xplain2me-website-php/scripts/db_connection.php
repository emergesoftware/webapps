<?php

require_once __DIR__ . '/constants.php';

/**
 * Description of DBConnection
 *
 * @author Tsepo Maleka - Emerge Software (Pty) Ltd
 * All rights reserved. 2013.
 * 
 */
class DBConnection  {
   
    /**
     * Gets the DB connection
     * @return type 
     */
    public static function getConnection(){
        return new mysqli(Constants::HOST_IP, Constants::USERNAME, 
                        Constants::PASSWORD, Constants::DB_NAME);
    }
    
    /**
     * Gets a prepared statement from a given conneciton
     * @param type $connection
     * @return type 
     */
    public static function getPreparedStatement($connection, $query){
        return $connection->prepare($query);
    }
    
}
?>
