package com.example.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isNumeric;

//TODO change money to big decimal or maybe convert to cents
//todo test to make sure builder can handle no client code or no client ID

public class ClientSession {

    /* to create */
    private String location;    // branch location
    private int sessionId;      // ID number of session
    private String sessionType; // string value of type of session
    private Calendar dateTime;  // date of the session
    private String serviceName; // description of service
    private String serviceType; // service type category
    private String clientCode;  // unique client code
    private int clientId;       // unique client ID
    private String contractorName; //name of the contractor
    private String supervisorName;  // contractors supervisor if required
    private int duration;       // duration of session in minutes
    private String attendance;  // type of attendance
    private int fee;            // fee for service in dollars
    private int charged;        // fee that was charged to customer
    private int taxCharged;     // tax that was charged
    private int paid;           // amount that customer paid
    private int taxPaid;        // tax that was paid by customer
    private int invoiceId;      // unique invoice ID for charged session
    private String paymentMethod;   // payment methods //todo can be split up into a list
    private String noteStatus;  // status of note
    private String comment;     // session comments
    private String clientTags;  // client tags //todo can be split up into a list
    private Boolean videoSession; // if was tagged as a video session
    private String DATE_PATTERN = "M/dd/yyyy  h:mm:SS aa"; // todo can probably delete


    private ClientSession(ClientSessionBuilder builder) {
        this.location = builder.location;
        this.sessionId = builder.sessionId;
        this.sessionType = builder.sessionType;
        this.dateTime = builder.dateTime;
        this.serviceName = builder.serviceName;
        this.serviceType = builder.serviceType;
        this.clientCode = builder.clientCode;
        this.clientId = builder.clientId;
        this.contractorName = builder.contractorName;
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

    public String getContractorName() {
        return contractorName;
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


    @Override
    public boolean equals(Object object) {
        // If the object is compared with itself then return true
        if (object == this) {
            return true;
        }
        /* Check if o is an instance of ClientSession or not
          "null instanceof [type]" also returns false */
        if (!(object instanceof ClientSession)) {
            return false;
        }

        // typecast o to ClientSession so that we can compare data members
        ClientSession castClientSessionObject = (ClientSession) object;

        return location.equals(castClientSessionObject.location)
                && sessionId == (castClientSessionObject.sessionId)
                && sessionType.equals(castClientSessionObject.sessionType)
                && dateTime.equals(castClientSessionObject.dateTime)
                && serviceName.equals(castClientSessionObject.serviceName)
                && serviceType.equals(castClientSessionObject.serviceType)
                && clientCode.equals(castClientSessionObject.clientCode)
                && clientId == (castClientSessionObject.clientId)
                && contractorName.equals(castClientSessionObject.contractorName)
                && supervisorName.equals(castClientSessionObject.supervisorName)
                && duration == (castClientSessionObject.duration)
                && attendance.equals(castClientSessionObject.attendance)
                && fee == (castClientSessionObject.fee)
                && charged == (castClientSessionObject.charged)
                && taxCharged == (castClientSessionObject.taxCharged)
                && paid == (castClientSessionObject.paid)
                && taxPaid == (castClientSessionObject.taxPaid)
                && invoiceId == (castClientSessionObject.invoiceId)
                && paymentMethod.equals(castClientSessionObject.paymentMethod)
                && noteStatus.equals(castClientSessionObject.noteStatus)
                && comment.equals(castClientSessionObject.comment)
                && clientTags.equals(castClientSessionObject.clientTags)
                && videoSession.equals(castClientSessionObject.videoSession);

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
        private String contractorName = null;
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
        private String DATE_PATTERN; // todo can probably delete

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

        public ClientSessionBuilder contractorName(String contractorName) {
            this.contractorName = contractorName;
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
