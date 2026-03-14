package com.scaler.EmailService_March2025.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
        private String email;
        private String subject;
        private String body;

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public String getBody() {
                return body;
        }

        public void setBody(String body) {
                this.body = body;
        }
}
