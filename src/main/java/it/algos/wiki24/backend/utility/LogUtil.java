package it.algos.wiki24.backend.utility;

import org.apache.commons.lang3.*;

public class LogUtil {


    public static String monLog(String type, String detail){

        StringBuffer sb=new StringBuffer();

        String sType="";
        if(!StringUtils.isEmpty(type)){
            sType=type;
        }
        sb.append(rPad(sType,10));
        sb.append(" | ");

        sb.append(detail);

        return sb.toString();
    }


    /**
     * Fills with spaces to the desired length
     * The output is a fixed-lenght string
     */
    public static String rPad(String source, int len){
        String out=StringUtils.rightPad(source,len);
        return out.substring(0,len);
    }




}
