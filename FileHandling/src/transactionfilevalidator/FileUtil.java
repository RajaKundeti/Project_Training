package transactionfilevalidator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    static String date;

    public static void processFiles(String folder){
        File file = new File(folder);
        File[] files = file.listFiles((x)->x.getName().endsWith(".csv"));
        assert files != null;
        for(File f: files){
            if(checkFileName(f.getName())){
                List<Transaction> transactions = getTransactions(f.getAbsolutePath());
            }
            else{
                continue;
            }
        }
    }

    public static boolean checkFileName(String fileName){
        String pattern = "^[A-Z0-9]{3,}_TXN_(\\\\d{8})\\\\.csv$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(fileName);
        if(matcher.matches()){
            String fileDate = matcher.group(1);
            date = fileDate;
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
            return fileDate.equals(today);
        }
        return false;
    }

    public static List<Transaction> getTransactions(String filePath){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String header = br.readLine();
            if(validHeader(header)){
                String line;
                while((line= br.readLine())!= null){
                    String[] data = line.split(",");
                    if(checkFields(data) != null) {
                        Transaction transaction = new Transaction();
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Transaction checkFields(String[] data) {
        Transaction t = null;
        try{
            String txnId = data[0];
            long accountNo = Long.parseLong(data[1]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date txnDate = sdf.parse(String.valueOf(data[2]));
            double amount = Double.parseDouble(data[3]);
            String currency = data[4];
            String status = data[5];

            if(!(Pattern.matches("^[A-Za-z0-9]+$",txnId) && txnId.length()==6)){
                return t;
            }
            if(!(Pattern.matches("^\\d{10}$", Long.toString(accountNo)) && Long.toString(accountNo).length()==10)){
                return t;
            }
            if(!(LocalDate.parse(date).equals(txnDate))){
                return t;
            }
            if(amount<0){
                return t;
            }
            if(!(currency.equals("INR") || currency.equals("USD") || currency.equals("EUR"))){
                return t;
            }
            if(!(status.equals("SUCCESS") || status.equals("FAILED") || status.equals("PENDING"))){
                return t;
            }
            t = new Transaction();
            t.setTxnId(txnId);
            t.setAccountNo(accountNo);
            t.setTxnDate(txnDate);
            t.setAmount(amount);
            t.setCurrency(currency);
            t.setStatus(status);
        }catch (Exception e){
            e.getMessage();
        }
        return t;
    }

    private static boolean validHeader(String header) {
        String[] fields = header.split(",");
        for(String s: fields){
            if(s == null || s.isEmpty())
                return false;
        }
        if(fields.length==6){
            return fields[0].equals("TXN_ID") &&
                    fields[1].equals("ACCOUNT_NO") &&
                    fields[2].equals("TXN_DATE") &&
                    fields[3].equals("AMOUNT") &&
                    fields[4].equals("CURRENCY") &&
                    fields[5].equals("STATUS");
        }
        return false;
    }
}
