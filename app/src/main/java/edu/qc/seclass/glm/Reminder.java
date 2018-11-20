package edu.qc.seclass.glm;

public class Reminder {
  private int id;
  private String title;
  private String date;
  private String time;
  private String location;
  private String repeat;
  private String finished;

  public Reminder (int id, String title, String date, String time, String location, String repeat) {
    this.id = id;
    this.title = title;
    this.date = date;
    this.time = time;
    this.location = location;
    this.repeat = repeat;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public String getTime()
  {
    return time;
  }

  public void setTime(String time)
  {
    this.time = time;
  }
}