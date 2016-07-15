import com.github.sarxos.webcam.Webcam;

import com.github.sarxos.webcam.WebcamUtils;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;

import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main
{
    public static void main(String[] args) {


        boolean net = true;
        do
        {
            if (netIsAvailable()&&mouse()) {
                webCapture();
                mailSend();
                net = false;
            }
        }
        while (net);
    }

    public static void webCapture()
    {
        Webcam webcam = Webcam.getDefault();

        webcam.setViewSize(new Dimension(640, 480));

        WebcamUtils.capture(webcam, "C:\\Users\\alin\\workspace\\spy\\test2", "jpg");
    }

    private static boolean netIsAvailable()
    {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e); }
        catch (IOException e) {
        }
        return false;
    }

    public static void mailSend()
    {


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("javaMailTest002@gmail.com", "anaaremere");
            }

        });
        try
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("javaMailTest002@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("alindradici@gmail.com"));

            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MMMM dd,yyyy,hh:mm");
            message.setSubject("Your computer has been accessed at "+LocalDateTime.now().format(dateTime));
            message.setText("Congratulation!");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            String file = "C:\\Users\\alin\\workspace\\spy\\test2.jpg";
            String fileName = "Check This Out";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("done, email sent ok");
        }
        catch (Exception e) {
            System.out.println("Email sending problems");
            e.printStackTrace();
        }
    }
    public static boolean mouse() {

        boolean sense=true;
        boolean moved = false;
        do {
            double a = MouseInfo.getPointerInfo().getLocation().getX();
            double b = MouseInfo.getPointerInfo().getLocation().getX();
            if(a!=b){
                System.out.println("mouse moved");
                sense=false;
                moved=true;
            }
        }while(sense);
        return moved;
    }

}