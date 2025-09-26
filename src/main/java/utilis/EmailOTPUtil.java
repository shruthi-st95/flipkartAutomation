package utilis;

import java.util.Properties;
import java.util.regex.*;

import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.search.FlagTerm;

public class EmailOTPUtil {

    public static String fetchOTPFromEmail(String email, String appPassword) throws Exception {
        String host = "imap.gmail.com";

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect(host, email, appPassword);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Search unread messages
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

        for (int i = messages.length - 1; i >= 0; i--) {
            String subject = messages[i].getSubject();
            if (subject != null && subject.contains("Flipkart")) {
                String content = getTextFromMessage(messages[i]);

                Pattern p = Pattern.compile("\\b\\d{6}\\b");
                Matcher m = p.matcher(content);
                if (m.find()) {
                    inbox.close(false);
                    store.close();
                    return m.group();
                }
            }
        }

        inbox.close(false);
        store.close();
        return null;
    }

    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                result.append(multipart.getBodyPart(i).getContent().toString());
            }
            return result.toString();
        }
        return "";
    }
}
