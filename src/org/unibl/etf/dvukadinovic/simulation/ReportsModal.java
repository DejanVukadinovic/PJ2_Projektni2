package org.unibl.etf.dvukadinovic.simulation;

import org.unibl.etf.dvukadinovic.report.Report;
import org.unibl.etf.dvukadinovic.report.ReportRecord;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReportsModal extends JFrame {
    private static Logger logger = Logger.getLogger(ReportsModal.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/reportsModal"+System.nanoTime()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ReportsModal(){
        this.setLayout(new FlowLayout());
        this.add(new Label("Reports"));
        //this.add(new Label("second"));
        ArrayList<Report> reports = new ArrayList<>();
        System.out.println(ReportRecord.getRecord());
        ArrayList<String> customsReports = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(ReportRecord.customsRecordString))){
            String line = reader.readLine();
            System.out.println("Customs reports:");
            while (line!=null){
                customsReports.add(line);
                System.out.println(line);
                line = reader.readLine();
            }
        }catch (Exception e){
            logger.info("No customs reports");
        }

        System.out.println(ReportRecord.recordString);
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ReportRecord.recordString))){
            ArrayList<Report> obj = (ArrayList<Report>) ois.readObject();
            System.out.println(obj);
            reports = obj;
        }catch(Exception e){
            logger.warning(e.getMessage());
        }

        JLabel customsLabel = new JLabel("Customs reports:");
        this.add(customsLabel);
        customsReports = new ArrayList<>(new HashSet<>(customsReports));
        for(String report: customsReports){
            JLabel label = new JLabel(report);
            this.add(label);
        }
        JLabel reportsLabel = new JLabel("Border reports:");
        this.add(reportsLabel);
        for (Report rep: reports){
            JLabel button = new JLabel(rep.toString());
            this.add(button);
        }
    }
}
