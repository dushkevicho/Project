package com.example.project;

import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class ClientSession {
    private String location;
    private int sessionId;
    private String sessionType;
    private LocalDateTime dateTime;
    private String serviceName;
    private String serviceType;
    private String clientCode;
    private int clientId;
    private String therapistName;
    private String supervisorName;
    private int duration;
    private String attendance;
    private int fee;
    private int charged;
    private int taxCharged;
    private int paid;
    private int taxPaid;
    private int invoiceId;
    private String paymentMethod;
    private String noteStatus;
    private String comment;
    private String clientTags;
    private Boolean videoSession;

    private ClientSession(ClientSessionBuilder builder) {
        this.location = builder.location;
        this.sessionId = builder.sessionId;
        this.sessionType = builder.sessionType;
        this.dateTime = builder.dateTime;
        this.serviceName = builder.serviceName;
        this.serviceType = builder.serviceType;
        this.clientCode = builder.clientCode;
        this.clientId = builder.clientId;
        this.therapistName = builder.therapistName;
        this.supervisorName = builder.supervisorName;
        this.duration = builder.duration;
        this.attendance = builder.attendance;
        this.fee = builder.fee;
        this.charged = builder.charged;
        this.taxCharged = builder.taxCharged;
        this.paid = builder.paid;
        this.taxPaid = builder.taxPaid;
        this.invoiceId = builder.invoiceId;
        this.paymentMethod = builder.paymentMethod;
        this.noteStatus = builder.noteStatus;
        this.comment = builder.comment;
        this.clientTags = builder.clientTags;
        this.videoSession = builder.videoSession;


    }

    public String getLocation() {
        return location;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getClientCode() {
        return clientCode;
    }

    public int getClientId() {
        return clientId;
    }

    public String getTherapistName() {
        return therapistName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public int getDuration() {
        return duration;
    }

    public String getAttendance() {
        return attendance;
    }

    public int getFee() {
        return fee;
    }

    public int getCharged() {
        return charged;
    }

    public int getTaxCharged() {
        return taxCharged;
    }

    public int getPaid() {
        return paid;
    }

    public int getTaxPaid() {
        return taxPaid;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getNoteStatus() {
        return noteStatus;
    }

    public String getComment() {
        return comment;
    }

    public String getClientTags() {
        return clientTags;
    }

    public Boolean getVideoSession() {
        return videoSession;
    }

    public static class ClientSessionBuilder
    {
        private String location = null;
        private int sessionId = 0;
        private String sessionType = null;
        private LocalDateTime dateTime = null;
        private String serviceName = null;
        private String serviceType = null;
        private String clientCode = null;
        private int clientId = 0;
        private String therapistName = null;
        private String supervisorName = null;
        private int duration = 0;
        private String attendance = null;
        private int fee = 0;
        private int charged = 0;
        private int taxCharged = 0;
        private int paid = 0;
        private int taxPaid = 0;
        private int invoiceId = 0;
        private String paymentMethod = null;
        private String noteStatus = null;
        private String comment = null;
        private String clientTags = null;
        private Boolean videoSession = null;

        public ClientSessionBuilder(String clientCode, String clientId) {
            this.clientCode = clientCode;
            if (isNumeric(clientId)) {
                this.clientId = Integer.valueOf(clientId);
            }
            else {
                this.clientId = 0;
            }
        }

        public ClientSessionBuilder location(String location) {
            this.location = location;
            return this;
        }

        public ClientSession build() {
            ClientSession clientSession = new ClientSession(this);
            return clientSession;
        }



    }

}
