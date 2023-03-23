package com.revature.springpep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "message")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name = "posted_by", referencedColumnName = "account_id", nullable = false)
    @JsonProperty("posted_by")
    private Account postedBy;

    @Column(name = "message_text", nullable = false, length = 255)
    @JsonProperty("message_text")
    private String messageText;

    @Column(name = "time_posted_epoch", nullable = false)
    @JsonProperty("time_posted_epoch")
    private Instant timePostedEpoch;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return messageId != null && Objects.equals(messageId, message.messageId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
