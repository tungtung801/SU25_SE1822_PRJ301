/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tung Nguyen
 */
public class ExamDTO {
    private int exam_id;
    private String exam_tittle;
    private String subject;
    private int category_id;
    private int total_marks;
    private int duration;

    public ExamDTO() {
    }

    public ExamDTO(int exam_id, String exam_tittle, String subject, int category_id, int total_marks, int duration) {
        this.exam_id = exam_id;
        this.exam_tittle = exam_tittle;
        this.subject = subject;
        this.category_id = category_id;
        this.total_marks = total_marks;
        this.duration = duration;
    }

    public ExamDTO(String exam_tittle, String subject, int category_id, int total_marks, int duration) {
        this.exam_tittle = exam_tittle;
        this.subject = subject;
        this.category_id = category_id;
        this.total_marks = total_marks;
        this.duration = duration;
    }

    

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_tittle() {
        return exam_tittle;
    }

    public void setExam_tittle(String exam_tittle) {
        this.exam_tittle = exam_tittle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(int total_marks) {
        this.total_marks = total_marks;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    
}
