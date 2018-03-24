package com.gp.utils;

import java.util.*;

public final class DateUtils {


    public static Date getCurrentTime(){
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Kuala_Lumpur"),new Locale("ms","MY")).getTime();
    }

   

}
