package com.chayan.mulewa.hr.dl.interfaces.dto;
import com.chayan.mulewa.hr.dl.exception.DTOException;
import java.util.Set;
import java.util.SimpleTimeZone;
    public interface DesignationDTOInterface extends Comparable<DesignationDTOInterface>, java.io.Serializable
    {
        public void setCode (int code);
        public int getCode ();
        public void setTitle (String title);
        public String getTitle ();
    }