package model;

import java.util.List;

public class AttachmentInfo {
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "AttachmentInfo{" +
                "attachments=" + attachments +
                '}';
    }
}
