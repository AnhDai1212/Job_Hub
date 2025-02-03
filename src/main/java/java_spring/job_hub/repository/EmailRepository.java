package java_spring.job_hub.repository;

public interface EmailRepository {

    public void sendMessage(String from, String to, String subject, String text);
}
