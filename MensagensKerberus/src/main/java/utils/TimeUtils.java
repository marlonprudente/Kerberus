/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Date;

/**
 *
 * @author Vitor Tozzi
 */
public class TimeUtils {
    
    public static final long HOUR = 3600*1000; // in milli-seconds
    
    public static Date addHours(Date oldDate, int hours){
        Date newDate = new Date(oldDate.getTime() + hours * HOUR);
        return newDate;
    }
    
    public static Date getDate(){
        return new Date();
    }
    
    public static boolean compare (Date data1, Date data2){
        if(data1.before(data2)){
            return true;
        }
        return false;
    }
    
    public static boolean checkValidTimestamp(Date data){
        
        Date dataAtual = addHours(getDate(), 0);
        return data.after(dataAtual);
        
    }
    
    public static boolean checkFakeValidTimestamp(Date data){
        
        Date dataAtual = getDate();
        return data.after(dataAtual);
        
    }
}
