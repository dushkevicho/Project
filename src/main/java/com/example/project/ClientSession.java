package com.example.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isNumeric;


public class ClientSession {
    private String location;
    private int sessionId;
    private String sessionType;
    private Calendar dateTime;
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
    private String DATE_PATTERN = "dd/MM/yyyy hh:mm a";


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

    public Calendar getDateTime() {
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
        private Calendar dateTime = null;
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
        private Boolean videoSession = false;
        private String DATE_PATTERN;

        // each session will always have a client code and client ID
        public ClientSessionBuilder(String clientCode, String clientId) {
            this.clientCode = clientCode;

            //clientId must be integer
            if (isNumeric(clientId)) {
                this.clientId = Integer.valueOf(clientId);
            }
        }

        public ClientSessionBuilder location(String location) {
            this.location = location;
            return this;
        }
        public ClientSessionBuilder sessionType(String sessionType) {
            this.sessionType = sessionType;
            return this;
        }

        public ClientSessionBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public ClientSessionBuilder serviceType(String serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public ClientSessionBuilder therapistName(String therapistName) {
            this.therapistName = therapistName;
            return this;
        }

        public ClientSessionBuilder supervisorName(String supervisorName) {
            this.supervisorName = supervisorName;
            return this;
        }

        public ClientSessionBuilder attendance(String attendance) {
            this.attendance = attendance;
            return this;
        }

        public ClientSessionBuilder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public ClientSessionBuilder noteStatus(String noteStatus) {
            this.noteStatus = noteStatus;
            return this;
        }

        public ClientSessionBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ClientSessionBuilder clientTags(String clientTags) {
            this.clientTags = clientTags;
            return this;
        }

        public ClientSessionBuilder videoSession(String videoSession) {
            if (videoSession.equalsIgnoreCase("yes")) {
                this.videoSession = true;
            }
            return this;
        }

        public ClientSessionBuilder duration(String duration) {
            if (isNumeric(duration)) {
                this.duration = Integer.valueOf(duration);
            }
            return this;
        }

        public ClientSessionBuilder fee(String fee) {
            if (isNumeric(fee)) {
                this.fee = Integer.valueOf(fee);
            }
            return this;
        }

        public ClientSessionBuilder charged(String charged) {
            if (isNumeric(charged)) {
                this.charged = Integer.valueOf(charged);
            }
            return this;
        }

        public ClientSessionBuilder taxCharged(String taxCharged) {
            if (isNumeric(taxCharged)) {
                this.taxCharged = Integer.valueOf(taxCharged);
            }
            return this;
        }

        public ClientSessionBuilder paid(String paid) {
            if (isNumeric(paid)) {
                this.paid = Integer.valueOf(paid);
            }
            return this;
        }

        public ClientSessionBuilder taxPaid(String taxPaid) {
            if (isNumeric(taxPaid)) {
                this.taxPaid = Integer.valueOf(taxPaid);
            }
            return this;
        }

        public ClientSessionBuilder invoiceId(String invoiceId) {
            if (isNumeric(invoiceId)) {
                this.invoiceId = Integer.valueOf(invoiceId);
            }
            return this;
        }

        public ClientSessionBuilder sessionId(String sessionId) {
            if (isNumeric(sessionId)) {
                this.sessionId = Integer.valueOf(sessionId);
            }
            return this;
        }

        public ClientSessionBuilder dateTime(String dateTime) throws ParseException {
            this.dateTime = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy  h:mm:SS aa", Locale.ENGLISH);
            Date date = dateFormat.parse(dateTime);
            this.dateTime.setTime(dateFormat.parse(dateTime));
            return this;
        }


        public ClientSession build() {
            ClientSession clientSession = new ClientSession(this);
            return clientSession;
        }





    }

}
