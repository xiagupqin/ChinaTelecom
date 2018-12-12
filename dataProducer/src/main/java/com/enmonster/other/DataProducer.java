package com.enmonster.other;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
1.this class is in order to get the data (analog the data source)
2.the right format is following:
    number1 number2 daytime specificTime duration flag
3.the teleNumber is static,so how to avoid using  'static' identifier?
 */
public class DataProducer {
    //teleNumber ： store telephone number;
    //numberAndName：telephone number --> name
    //init the metadata
    public static void initMetadata(ArrayList<String> teleNumber,HashMap<String,String> numberAndName){
        teleNumber.add("17802596779");
        teleNumber.add("18907263863");
        teleNumber.add("19188980695");
        teleNumber.add("13320266126");
        teleNumber.add("19048828124");
        teleNumber.add("13653454072");
        teleNumber.add("13135279938");
        teleNumber.add("18281704261");
        teleNumber.add("17035534749");
        teleNumber.add("19834669675");
        teleNumber.add("19417467461");
        teleNumber.add("19772366326");
        teleNumber.add("18283449398");
        teleNumber.add("16005439091");
        teleNumber.add("14924565399");
        teleNumber.add("14218140347");
        teleNumber.add("17782151215");
        teleNumber.add("17340248510");
        teleNumber.add("19961057493");
        teleNumber.add("19724655139");


        numberAndName.put("17802596779","李雁");
        numberAndName.put("18907263863","卫艺");
        numberAndName.put("19188980695","仰莉");
        numberAndName.put("13320266126","陶欣悦");
        numberAndName.put("19048828124","施梅梅");
        numberAndName.put("13653454072","金虹霖");
        numberAndName.put("13135279938","魏明艳");
        numberAndName.put("18281704261","华贞");
        numberAndName.put("17035534749","华啟倩");
        numberAndName.put("19834669675","仲采绿");
        numberAndName.put("19417467461","卫丹");
        numberAndName.put("19772366326","戚丽红");
        numberAndName.put("18283449398","何翠柔");
        numberAndName.put("16005439091","钱溶艳");
        numberAndName.put("14924565399","钱琳");
        numberAndName.put("14218140347","缪静欣");
        numberAndName.put("17782151215","焦秋菊");
        numberAndName.put("17340248510","吕访琴");
        numberAndName.put("19961057493","沈丹");
        numberAndName.put("19724655139","褚美丽");
    }

    //produce access log
    public static String produce(ArrayList<String> teleNum,HashMap<String,String> numberAndName,String startTime,String endTime){
        initMetadata(teleNum,numberAndName);
        int callerIndex = (int)(Math.random()*teleNum.size() );//get a random number in order to get a random number
        String callerName = teleNum.get(callerIndex);//get the caller Name
        int calleeIndex;//
        do{
            calleeIndex = (int)(Math.random()*teleNum.size());//get a random number
        }
        while(callerIndex == calleeIndex);//if get the same index ,continue
        String calleeName = teleNum.get(calleeIndex);//get the calleeName

        //for example,you could use yyyy-mm-dd,but this pattern represent year-minute-day
        //so the right pattern should be yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//should care the format,or you could meet an error
        //the startTime and endTime only a string,so you should transform them to a variables of Date
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //get a random distance to form a date.
        //the startDate and endDate are parameters that you ensure the all calls range.
        long randomTs = startDate.getTime() + (long)((endDate.getTime() - startDate.getTime()) * Math.random());
        Date resultDate = new Date(randomTs);
        String resultTimeString = sdf.format(resultDate);

        //the following is in order to get a specific time of call
        int hour = (int) (Math.random() * 24);//get hour
        int minute = (int) (Math.random() * 60);
        int second = (int) (Math.random() * 60);
        //String specificTime = Integer.toString(hour)+":"+Integer.toString(minute)+":"+Integer.toString(second);
        String specificTime = String.format("%02d", hour) +":"+ String.format("%02d", minute)
                +":"+ String.format("%02d", second);

        //The following procedure is to generate a duration of communication
        //the unit is second
        int duration = (int) (Math.random()*3600);//Units are seconds

        //the final call information
        String result = callerName+" "+calleeName+" "+resultTimeString+" "+specificTime+" "+duration ;
        return result;
    }

    //write result to text file
    public void writeLog(String filePath,String result){
        OutputStreamWriter osw = null;
        try {
            //you should use append not override,so you should use FileOutputStream(filePath,true),the true denote append
            osw = new OutputStreamWriter(new FileOutputStream(filePath,true), "utf-8");
            osw.write(result);
            osw.flush();//must
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void main(String[] args) {
        if(args[0]==null || args[0]==""){
            return;
        }
        DataProducer dataProducer = new DataProducer();
        dataProducer.initMetadata();
        for(int i = 0;i<1000000;i++){
            String result = dataProducer.produce(teleNumber,"2017-01-01","2017-12-12");
            try {
                Thread.sleep(1500);//sleep 1500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(result);
            //the result of output is in args[0]
            dataProducer.writeLog(args[0],result+"\n");
        }
    }
     */
}
