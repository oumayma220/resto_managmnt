package com.team.building.model;

import com.team.building.enums.RoleEnum;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="admin")
public class Admin extends  User{

    public Admin(String email, String password, String fullname) {
        super(email, password, fullname, RoleEnum.ADMIN);
    }

    public Admin() {
    }

    private String createdon;

    //private String updatedon;

    @PrePersist
    private void onCreate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Instant instant = date.toInstant();
        LocalDateTime ldt = instant
                .atZone(ZoneId.of("Africa/Tunis"))
                .toLocalDateTime();
        this.createdon = ldt.format(formatter);
    }


    public String getCreatedon() { return createdon; }

        public void setCreatedon(String createdon) { this.createdon = createdon; }

        //public String getUpdatedon() { return updatedon; }

        //public void setUpdatedon(String updatedon) { this.updatedon = updatedon; }
}
