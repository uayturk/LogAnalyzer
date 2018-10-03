import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class libreofficeyaratdene {

    private static String[] columns = {"Response Time (in ms)", "Id", "Request Type",""};
    private static String[] columns2 = {"getVersion mean(in ms)", "searchObjects mean(in ms)", "checkBookability mean(in ms)","bookAccommodation mean(in ms)"};
    private static List<CevapZamani> cevapzamani =  new ArrayList<CevapZamani>();

    double harmonikOrt_getVersion_SON_public=0;
    double harmonikOrt_searchObjects_SON_public=0;
    double harmonikOrt_checkBookability_SON_public=0;
    double harmonikOrt_bookAccommodation_SON_public=0;


    public static void main(String[] args) throws IOException, InvalidFormatException {
        //---------------------------------------------------------------------------------
        double harmonikOrt_getVersion_SON=0;
        double harmonikOrt_searchObjects_SON=0;
        double harmonikOrt_checkBookability_SON=0;
        double harmonikOrt_bookAccommodation_SON=0;

        try{
            FileInputStream fstream = new FileInputStream("mcc2.log2018-02-21");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine,ikinci_request_type="",birinci_request_type="",en_son_id="";
            int startTime=0,endTime=0,inteCevrilenSaat,inteCevrilenDakika,inteCevrilenSaniye,inteCevrilenMilisaniye ;
            int milisaniyeCinsiSaat,milisaniyeCinsiDakika,milisaniyeCinsiSaniye,cevapZamani;
            int sonucRequestTime=0,sonucResponseTime=0,ilk_atama=0,getVersion_SAY=0,searchObjects_SAY=0,checkBookability_SAY=0,bookAccommodation_SAY=0;
            double harmonikOrt_getVersion=0,harmonikOrt_searchObjects=0,harmonikOrt_checkBookability=0,harmonikOrt_bookAccommodation=0;


            List<Double> getVersion_CEVAPZAMAN = new ArrayList<>();
            List<Double> searchObjects_CEVAPZAMAN = new ArrayList<>();
            List<Double> checkBookability_CEVAPZAMAN = new ArrayList<>();
            List<Double> bookAccommodation_CEVAPZAMAN = new ArrayList<>();

            ArrayList idDiziRequest = new ArrayList();
            ArrayList requestHesaplananZaman = new ArrayList();
            HashMap<String, Integer>idDiziRequestHashMap=new HashMap<String, Integer>();

            ArrayList idDiziResponse = new ArrayList();
            ArrayList responseHesaplananZaman = new ArrayList();

            Map<String, List<String>> id_RequestTypeHash = new HashMap<String, List<String>>();

            while ((strLine = br.readLine()) != null)   {


                String[] idCekme=strLine.split(" ");

                if(strLine.contains("<S:Envelope") && strLine.contains("ns2") && strLine.contains("eurotours"))
                {

                    String idAlmaTypeIcin=idCekme[5];

                    int requestTypeStartIndex = strLine.indexOf("<ns2")+"<ns2:".length();
                    String requestType = " ";
                    if(strLine.contains("getVersion"))
                    {
                        requestType = strLine.substring(requestTypeStartIndex,requestTypeStartIndex+10);
                        getVersion_SAY++;
                    }
                    if(strLine.contains("searchObjects"))
                    {
                        requestType = strLine.substring(requestTypeStartIndex,requestTypeStartIndex+13);
                        searchObjects_SAY++;
                    }
                    if(strLine.contains("checkBookability"))
                    {
                        requestType = strLine.substring(requestTypeStartIndex,requestTypeStartIndex+17);
                        checkBookability_SAY++;

                    }
                    if(strLine.contains("bookAccommodation"))
                    {
                        requestType = strLine.substring(requestTypeStartIndex,requestTypeStartIndex+17);
                        bookAccommodation_SAY++;
                    }


                    if (!id_RequestTypeHash.containsKey(idAlmaTypeIcin))
                        id_RequestTypeHash.put(idAlmaTypeIcin,new ArrayList<>());

                    id_RequestTypeHash.get(idAlmaTypeIcin).add(requestType);


                }


                if (strLine.contains("SOAP Request") && strLine.contains("eurotours")) {

                    ilk_atama++;

                    String[] dosyadanCekilenStringSaat = strLine.split(" ");
                    String[] alinanSaatDakikaSaniye = dosyadanCekilenStringSaat[0].split(":");

                    inteCevrilenSaat = Integer.parseInt(alinanSaatDakikaSaniye[0]);
                    inteCevrilenDakika = Integer.parseInt(alinanSaatDakikaSaniye[1]);


                    String[] SaniyeVeMilisaniyeAyrim = alinanSaatDakikaSaniye[2].split(",");

                    inteCevrilenSaniye = Integer.parseInt(SaniyeVeMilisaniyeAyrim[0]);
                    inteCevrilenMilisaniye = Integer.parseInt(SaniyeVeMilisaniyeAyrim[1]);

                    milisaniyeCinsiSaat = (inteCevrilenSaat * 3600000);
                    milisaniyeCinsiDakika = (inteCevrilenDakika * 60000);
                    milisaniyeCinsiSaniye = (inteCevrilenSaniye * 1000);

                    startTime = (milisaniyeCinsiSaat + milisaniyeCinsiDakika + milisaniyeCinsiSaniye + inteCevrilenMilisaniye);
                    requestHesaplananZaman.add(startTime);
                    idDiziRequest.add(idCekme[5]);

                    idDiziRequestHashMap.put(idCekme[5],startTime);
                    startTime = 0;

                }


                if (strLine.contains("SOAP Response") && strLine.contains("eurotours")) {

                    String[] dosyadanCekilenStringSaat=strLine.split(" ");
                    String[] alinanSaatDakikaSaniye= dosyadanCekilenStringSaat[0].split(":");

                    inteCevrilenSaat=Integer.parseInt(alinanSaatDakikaSaniye[0]);
                    inteCevrilenDakika=Integer.parseInt(alinanSaatDakikaSaniye[1]);


                    String[] SaniyeVeMilisaniyeAyrim=alinanSaatDakikaSaniye[2].split(",");


                    inteCevrilenSaniye=Integer.parseInt(SaniyeVeMilisaniyeAyrim[0]);
                    inteCevrilenMilisaniye=Integer.parseInt(SaniyeVeMilisaniyeAyrim[1]);

                    milisaniyeCinsiSaat=(inteCevrilenSaat*3600000);
                    milisaniyeCinsiDakika=(inteCevrilenDakika*60000);
                    milisaniyeCinsiSaniye=(inteCevrilenSaniye*1000);

                    endTime=(milisaniyeCinsiSaat+milisaniyeCinsiDakika+milisaniyeCinsiSaniye+inteCevrilenMilisaniye);
                    responseHesaplananZaman.add(endTime);
                    idDiziResponse.add(idCekme[5]);

                    endTime=0;


                }


            }

            int say=0,if_ice_girdi=0,for_içi_say=0;

            do {


                say = 0;
                if_ice_girdi=0;

                for (int j = 0; j < idDiziResponse.size(); j++) {

                    if (idDiziRequest.get(0).equals(idDiziResponse.get(j))) {

                        say = 1;

                        sonucResponseTime = Integer.parseInt(responseHesaplananZaman.get(j).toString());
                        sonucRequestTime = Integer.parseInt(requestHesaplananZaman.get(0).toString());
                        cevapZamani = (sonucResponseTime - sonucRequestTime);


                        if(id_RequestTypeHash.get((String)idDiziRequest.get(0)).get(0).equals("getVersion"))
                        {
                            getVersion_CEVAPZAMAN.add((double) cevapZamani);

                        }

                        if(id_RequestTypeHash.get((String)idDiziRequest.get(0)).get(0).equals("searchObjects"))
                        {
                            searchObjects_CEVAPZAMAN.add((double)cevapZamani);
                        }

                        if(id_RequestTypeHash.get((String)idDiziRequest.get(0)).get(0).trim().equals("checkBookability"))
                        {
                            checkBookability_CEVAPZAMAN.add((double)cevapZamani);
                        }

                        if(id_RequestTypeHash.get((String)idDiziRequest.get(0)).get(0).equals("bookAccommodation"))
                        {
                            bookAccommodation_CEVAPZAMAN.add((double)cevapZamani);
                        }

                        cevapzamani.add(new CevapZamani(String.valueOf(cevapZamani),(String)idDiziRequest.get(0),id_RequestTypeHash.get((String)idDiziRequest.get(0)).get(0)));
                        idDiziResponse.remove(j);
                        responseHesaplananZaman.remove(j);
                        id_RequestTypeHash.get((String)idDiziRequest.get(0)).remove(0);
                        idDiziRequest.remove(0);
                        requestHesaplananZaman.remove(0);

                        sonucRequestTime = 0;
                        sonucResponseTime = 0;
                        if_ice_girdi=1;
                        break;
                    }

                    for_içi_say++;


                }


                if(if_ice_girdi==0) {
                    idDiziRequest.remove(0);
                    requestHesaplananZaman.remove(0);
                    say=1;
                }

                if(idDiziResponse.size()==0)
                    break;

            }while(say==1);


            for(int i=0 ; i<getVersion_CEVAPZAMAN.size() ; i++)
            {
                harmonikOrt_getVersion+=(1/(getVersion_CEVAPZAMAN.get(i)));
            }

            for(int i=0 ; i<searchObjects_CEVAPZAMAN.size() ; i++)
            {
                harmonikOrt_searchObjects+=(1/(searchObjects_CEVAPZAMAN.get(i)));
            }

            for(int i=0 ; i<checkBookability_CEVAPZAMAN.size() ; i++)
            {
                harmonikOrt_checkBookability+=(1/(checkBookability_CEVAPZAMAN.get(i)));
            }

            for(int i=0 ; i<bookAccommodation_CEVAPZAMAN.size() ; i++)
            {
                harmonikOrt_bookAccommodation+=(1/(bookAccommodation_CEVAPZAMAN.get(i)));
            }


            harmonikOrt_getVersion_SON=(getVersion_SAY/harmonikOrt_getVersion);
            harmonikOrt_searchObjects_SON=(searchObjects_SAY/harmonikOrt_searchObjects);
            harmonikOrt_checkBookability_SON=(checkBookability_SAY/harmonikOrt_checkBookability);
            harmonikOrt_bookAccommodation_SON=(bookAccommodation_SAY/harmonikOrt_bookAccommodation);
            System.out.println ("GetVer: " + harmonikOrt_getVersion_SON);
            System.out.println ("searcOb: " +harmonikOrt_searchObjects_SON);
            System.out.println ("checkBook: " +harmonikOrt_checkBookability_SON);
            System.out.println ("bookAcc: " +harmonikOrt_bookAccommodation_SON);

            fstream.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        //----------------------------------------------------------------------------------
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("CevapZamani");

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(CevapZamani cevapzaman: cevapzamani) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(cevapzaman.getCevapZaman());

            row.createCell(1)
                    .setCellValue(cevapzaman.getId());

            row.createCell(2)
                    .setCellValue(cevapzaman.getRequest_type());

        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        //----------------------------------------------------------------

        System.out.println ("columns.length+1 : " + cevapzamani.size());
        Row headerRow1 = sheet.createRow(cevapzamani.size()+2);

        for(int i = 0; i < columns2.length; i++) {
            Cell cell = headerRow1.createCell(i);
            cell.setCellValue(columns2[i]);

        }

        Row row1 = sheet.createRow(cevapzamani.size()+3);

        row1.createCell(0)
                .setCellValue(harmonikOrt_getVersion_SON);
        row1.createCell(1)
                .setCellValue(harmonikOrt_searchObjects_SON);
        row1.createCell(2)
                .setCellValue(harmonikOrt_checkBookability_SON);
        row1.createCell(3)
                .setCellValue(harmonikOrt_bookAccommodation_SON);

        for(int i = 0; i < columns2.length; i++) {
            sheet.autoSizeColumn(i);
        }
        //----------------------------------------------------------------

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("responseZamanlari.xlsx");
        workbook.write(fileOut);
        fileOut.close();
    }
}