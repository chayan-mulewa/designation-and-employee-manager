package com.chayan.mulewa.hr.bl.interfaces.pojo;
import java.io.*;
public interface DesignationInterface extends Serializable,Comparable<DesignationInterface>
{
    public void setCode(int code);
    public int getCode();
    public void setTitle(String title);
    public String getTitle();
}